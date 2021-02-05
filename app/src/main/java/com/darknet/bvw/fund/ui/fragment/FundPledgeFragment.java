package com.darknet.bvw.fund.ui.fragment;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.darknet.bvw.R;
import com.darknet.bvw.base.PayTransferHelper;
import com.darknet.bvw.common.BaseBindingFragment;
import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.config.UrlPath;
import com.darknet.bvw.databinding.FragmentFundPledgeBinding;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.fund.bean.DefiProduct;
import com.darknet.bvw.fund.bean.ClearSelectedStatusEvent;
import com.darknet.bvw.fund.ui.activity.AboutBTDActivity;
import com.darknet.bvw.fund.ui.adapter.FundPledgeAdapter;
import com.darknet.bvw.fund.ui.dialog.PledgeDialog;
import com.darknet.bvw.model.response.LeftMoneyResponse;
import com.darknet.bvw.order.vm.PayViewModel;
import com.darknet.bvw.util.ToastUtils;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.google.gson.Gson;
import com.jingui.lib.utils.wrap.SmallToBigComparator;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <pre>
 *     author : dinghui
 *     e-mail : dinghui@bcbook.com
 *     time   : 2021/02/03
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class FundPledgeFragment extends BaseBindingFragment<PayViewModel, FragmentFundPledgeBinding> {

    private PayTransferHelper mPayTransferHelper;
    private DefiProduct mSelected;
    private FundPledgeAdapter mAdapter;

    public static Fragment newInstance(ArrayList<DefiProduct> value) {
        FundPledgeFragment fragment = new FundPledgeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("list", value);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int setLayoutResId() {
        return R.layout.fragment_fund_pledge;
    }

    @Override
    protected PayViewModel initViewModel() {
        return getFragmentViewModel(PayViewModel.class);
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        mDataBinding.tvBTD.setOnClickListener(view -> AboutBTDActivity.start(requireContext()));
        mAdapter = new FundPledgeAdapter();
        mDataBinding.setAdapter(mAdapter);
        mDataBinding.tvPledge.setOnClickListener(view -> {
            mSelected = mAdapter.getSelected();
            if (mSelected == null) {
                ToastUtils.showToast(getString(R.string.tip_select_pledge_num));
                return;
            }
            PledgeDialog pledgeDialog = new PledgeDialog(requireContext(), mSelected.getAmount(), mSelected.getSymbol());
            pledgeDialog.setListener(pwd -> {
                if (!WalletDaoUtils.checkPassword(pwd)) {
                    ToastUtils.showToast(R.string.wrong_pwd);
                    return;
                }
                checkWallet(mSelected.getAmount(), () -> {
                    mViewModel.getPayAddress("DEFI_INVEST_ADDRESS");
                });

            });
            pledgeDialog.show();
        });
    }

    @Override
    protected void initData() {
        mPayTransferHelper = PayTransferHelper.attach(requireContext());
        Bundle bundle = getArguments();
        if (bundle != null) {
            ArrayList<DefiProduct> list = bundle.getParcelableArrayList("list");
            Collections.sort(list, new SmallToBigComparator<DefiProduct>() {
                @NonNull
                @Override
                public Number provideCompareField(@NonNull DefiProduct target) {
                    return target.getOrders();
                }
            });
            mDataBinding.setList(list);
        }
        mViewModel.tradeSuccessLive.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                EventBus.getDefault().post(new ClearSelectedStatusEvent());
                ToastUtils.showToast(getString(R.string.pledge_success));
            }
        });
        mViewModel.couponAddress.observe(getViewLifecycleOwner(), s -> {
            mViewModel.createTrade(mSelected.getAmount(), s, mSelected.getSymbol());
        });
        mViewModel.mSendTxMutableLiveData.observe(getViewLifecycleOwner(), sendTx -> {
            mPayTransferHelper.callH5CanNull(sendTx, input -> {
                if (input == null) return null;
                mViewModel.sendPledgeTrade(input, mSelected.getAmount(), mViewModel.couponAddress.getValue(), mSelected.getSymbol(), mSelected.getId());
                return null;
            });
        });

    }

    @Subscribe
    public void onSuccessEvent(ClearSelectedStatusEvent event) {
        mAdapter.setOriginStatusList();
    }
    private void checkWallet(String amount, Runnable after) {
        showLoading();
        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();
        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);
        String symbol = mSelected.getSymbol();

        OkGo.<String>get(ConfigNetWork.JAVA_API_URL + UrlPath.CHECK_MONEY_URL)
                .tag(requireActivity())
                .headers("Chain-Authentication", addressVals + "#" + msg + "#" + signVal)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> backresponse) {
                        if(backresponse == null) return;
                        String json = backresponse.body();
                        if(json == null) return;
                        try {
                            Gson gson = new Gson();
                            LeftMoneyResponse response = gson.fromJson(json, LeftMoneyResponse.class);
                            if (response != null && response.getCode() == 0 && response.getData() != null
                                    && response.getData().size() > 0) {
                                List<LeftMoneyResponse.LeftMoneyModel> list = response.getData();
                                for (LeftMoneyResponse.LeftMoneyModel model : list) {
                                    if(symbol.equals(model.getName())){
                                        BigDecimal money = new BigDecimal(model.getValue_qty());
                                        BigDecimal targetMoney = new BigDecimal(amount);
                                        if(targetMoney.compareTo(money) > 0) {
                                            Toast.makeText(requireContext(),requireActivity().getString(R.string.bid_yue_not_encough),Toast.LENGTH_SHORT).show();
                                        }else {
                                            after.run();
                                        }
                                        return;
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastUtils.showToast(response.message());
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissDialog();
                    }
                });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
