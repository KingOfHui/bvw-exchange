package com.darknet.bvw.fund.ui.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.darknet.bvw.R;
import com.darknet.bvw.base.PayTransferHelper;
import com.darknet.bvw.common.BaseBindingFragment;
import com.darknet.bvw.databinding.FragmentFundPledgeBinding;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.fund.bean.DefiProduct;
import com.darknet.bvw.fund.ui.activity.AboutBTDActivity;
import com.darknet.bvw.fund.ui.adapter.FundPledgeAdapter;
import com.darknet.bvw.fund.ui.dialog.PledgeDialog;
import com.darknet.bvw.fund.vm.FundViewModel;
import com.darknet.bvw.order.vm.PayViewModel;
import com.darknet.bvw.util.ToastUtils;
import com.jingui.lib.utils.wrap.SmallToBigComparator;

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
        mDataBinding.tvBTD.setOnClickListener(view -> AboutBTDActivity.start(requireContext()));
        FundPledgeAdapter adapter = new FundPledgeAdapter();
        mDataBinding.setAdapter(adapter);
        mDataBinding.tvPledge.setOnClickListener(view -> {
            DefiProduct selected = adapter.getSelected();
            if (selected == null) {
                ToastUtils.showToast(getString(R.string.tip_select_pledge_num));
                return;
            }
            PledgeDialog pledgeDialog = new PledgeDialog(requireContext(), selected.getAmount(), selected.getSymbol());
            pledgeDialog.setListener(pwd -> {
                if (!WalletDaoUtils.checkPassword(pwd)) {
                    ToastUtils.showToast(R.string.wrong_pwd);
                    return;
                }
                tradePledge(selected.getAmount(), selected.getSymbol(), selected.getId(),() -> {
                    pledgeDialog.dismiss();
                    ToastUtils.showToast(getString(R.string.pledge_success));
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
    }

    private void tradePledge(String amount, String symbol, int id, Runnable runnable) {
        mViewModel.getPayAddress("DEFI_INVEST_ADDRESS");
        mViewModel.tradeSuccessLive.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                runnable.run();
            }
        });
        mViewModel.couponAddress.observe(getViewLifecycleOwner(), s -> {
            mViewModel.createTrade(amount, s, symbol);
        });
        mViewModel.mSendTxMutableLiveData.observe(getViewLifecycleOwner(), sendTx -> {
            mPayTransferHelper.callH5CanNull(sendTx, input -> {
                if (input == null) return null;
                mViewModel.sendPledgeTrade(input, amount, mViewModel.couponAddress.getValue(), symbol, id);
                return null;
            });
        });
    }
}
