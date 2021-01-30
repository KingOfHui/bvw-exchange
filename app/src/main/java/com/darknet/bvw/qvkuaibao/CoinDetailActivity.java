package com.darknet.bvw.qvkuaibao;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.base.BasePayActivity;
import com.darknet.bvw.databinding.ActivityCoinDetailBinding;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.model.response.CreateTradeResponse.SendTx;
import com.darknet.bvw.order.vm.PayViewModel;
import com.darknet.bvw.qvkuaibao.adapter.BonusListAdapter;
import com.darknet.bvw.qvkuaibao.dialog.PoszhuanZhangDialog;
import com.darknet.bvw.qvkuaibao.vm.PosCoinDetailViewModel;
import com.darknet.bvw.util.SharedPreferencesUtil;
import com.darknet.bvw.util.ToastUtils;

import java.math.BigDecimal;

import androidx.arch.core.util.Function;
import androidx.lifecycle.Observer;

/**
 * <pre>
 *     author : dinghui
 *     e-mail : dinghui@bcbook.com
 *     time   : 2021/01/21
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class CoinDetailActivity extends BasePayActivity<ActivityCoinDetailBinding> {

    private PosCoinDetailViewModel mViewModel;
    private BigDecimal usdRange;

    public static void startSelf(Context context, String symbol, BigDecimal usdRange) {
        Intent intent = new Intent(context, CoinDetailActivity.class);
        intent.putExtra("symbol", symbol);
        intent.putExtra("usdRange", usdRange);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_coin_detail;
    }

    @Override
    public void initView() {
        usdRange = (BigDecimal) getIntent().getSerializableExtra("usdRange");
        mBinding.titleLayout.layBack.setOnClickListener(view -> finish());
        mBinding.titleLayout.title.setText(R.string.yb_title_val);
        mBinding.titleLayout.ivRight.setImageResource(R.mipmap.ic_qvkuaibao_record);
        mBinding.titleLayout.ivRight.setOnClickListener(v -> {
            YubibaoHistoryListActivity.start(v.getContext(), mViewModel.getSymbol());
        });
        mBinding.setAdapter(new BonusListAdapter());
        mBinding.tvIn.setOnClickListener(view -> {
            PoszhuanZhangDialog zhangDialog = new PoszhuanZhangDialog(this, null);
            zhangDialog.setOnPayClickListener((amount, pwd) -> {

                if (!WalletDaoUtils.checkPassword(pwd)) {
                    ToastUtils.showToast(R.string.wrong_pwd);
                    return;
                }
                zhangDialog.dismiss();
                in(mPayViewModel, amount, pwd, () -> {
                    ToastUtils.showToast(getString(R.string.tran_in_success));
                    mViewModel.getWalletData(mViewModel.getSymbol());
                });
            });
            zhangDialog.show();
        });
        mBinding.tvOut.setOnClickListener(view -> {
            PoszhuanZhangDialog zhangDialog = new PoszhuanZhangDialog(this, null);
            zhangDialog.setOnPayClickListener((amount, pwd) -> {
                if (!WalletDaoUtils.checkPassword(pwd)) {
                    ToastUtils.showToast(R.string.wrong_pwd);
                    return;
                }
                zhangDialog.dismiss();
                mViewModel.out(amount, pwd, ()->{/*zhangDialog.dismiss()*/});
            });
            zhangDialog.show();
        });
        mBinding.tvAll.setOnClickListener(view -> BonusActivity.start(this, getIntent().getStringExtra("symbol")));
        boolean ybbEyes = (boolean) SharedPreferencesUtil.getData("ybbEyes", true);
        mBinding.ivVisible.setActivated(ybbEyes);
        mBinding.ivVisible.setOnClickListener(v -> {
            v.setActivated(!v.isActivated());
            SharedPreferencesUtil.putData("ybbEyes", v.isActivated());
            mViewModel.toggleVisible();
        });
    }

    public void in(PayViewModel payVM, String amount, String password, Runnable successCallback) {
        payVM.tradeSuccessLive.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean success) {
                payVM.tradeSuccessLive.removeObserver(this);
                if(success) {
                    successCallback.run();
                }
            }
        });
        String symbol = mViewModel.getSymbol();
        payVM.mSendTxMutableLiveData.observe(this, new Observer<SendTx>() {
            @Override
            public void onChanged(SendTx sendTx) {
                payVM.mSendTxMutableLiveData.removeObserver(this);
                callH5CanNull(sendTx, afterSignVal -> {
                    if(afterSignVal == null) return null;
                    mPayViewModel.sendTransferInTrade(afterSignVal, amount, payVM.couponAddress.getValue(), symbol);
                    return null;
                });
            }
        });
        payVM.couponAddress.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String address) {
                payVM.couponAddress.removeObserver(this);
                //创建交易
                payVM.createTrade(amount, address, symbol);
            }
        });
        //获取地址
        payVM.getPayAddress("CHAIN_POS_INVEST");
    }

    @Override
    public void initDatas() {
        String symbol = getIntent().getStringExtra("symbol");
        mBinding.tvCoinName.setText(String.format(getString(R.string.total_assets_equivalent) + "（%s）", symbol));
        mViewModel = getViewModel(PosCoinDetailViewModel.class);
        mViewModel.getWalletData(symbol);
        mViewModel.mPosWalletDataMutableLiveData.observe(this, posWalletData -> {
            if(mBinding.ivVisible.isActivated()) {
                if(posWalletData == null) {
                    mBinding.tvCoinCount.setText("0");
                    mBinding.tvCoinCny.setText("≈$0");
                    mBinding.tvDailyBonus.setText("0");
                    mBinding.tvYesterdayBonus.setText("0");
                    mBinding.tvTotalBonus.setText("0");
                    return;
                }
                mBinding.tvCoinCount.setText(String.format("%s", posWalletData.getPosInvestAmount()));
                mBinding.tvCoinCny.setText("≈$"+posWalletData.getPosInvestAmount().multiply(usdRange).setScale(2, BigDecimal.ROUND_DOWN).toEngineeringString());
                mBinding.tvDailyBonus.setText(posWalletData.getDayRate());
                mBinding.tvYesterdayBonus.setText(posWalletData.getYesterdayPosBonusAmount());
                mBinding.tvTotalBonus.setText(posWalletData.getPosBonusAmount());
            }else {
                mBinding.tvCoinCount.setText("****");
                mBinding.tvCoinCny.setText("****");
                mBinding.tvDailyBonus.setText("****");
                mBinding.tvYesterdayBonus.setText("****");
                mBinding.tvTotalBonus.setText("****");
            }
        });
    }

    @Override
    protected void createTrade(String address) {
        //do nothing
        //啥也不做, 创建交易不在这里
    }

    @Override
    protected void sendTrade(String afterSignVal) {
        //do nothing
    }
}
