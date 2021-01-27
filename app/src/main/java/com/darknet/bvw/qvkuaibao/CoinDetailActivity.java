package com.darknet.bvw.qvkuaibao;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.base.BasePayActivity;
import com.darknet.bvw.databinding.ActivityCoinDetailBinding;
import com.darknet.bvw.qvkuaibao.adapter.BonusListAdapter;
import com.darknet.bvw.qvkuaibao.dialog.PoszhuanZhangDialog;
import com.darknet.bvw.qvkuaibao.vm.PosCoinDetailViewModel;

import java.math.BigDecimal;

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

    public static void startSelf(Context context, String symbol) {
        Intent intent = new Intent(context, CoinDetailActivity.class);
        intent.putExtra("symbol", symbol);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_coin_detail;
    }

    @Override
    public void initView() {
        mBinding.titleLayout.layBack.setOnClickListener(view -> finish());
        mBinding.titleLayout.title.setText("余币宝");
        mBinding.titleLayout.ivRight.setImageResource(R.mipmap.ic_qvkuaibao_record);
        mBinding.titleLayout.ivRight.setOnClickListener(v -> {
            YubibaoHistoryListActivity.start(v.getContext(), mViewModel.getSymbol());
        });
        mBinding.setAdapter(new BonusListAdapter());
        mBinding.tvIn.setOnClickListener(view -> {
            PoszhuanZhangDialog zhangDialog = new PoszhuanZhangDialog(this, null);
            zhangDialog.setOnPayClickListener((amount, pwd) -> {
                mViewModel.in(amount, pwd, zhangDialog::dismiss);
            });
            zhangDialog.show();
        });
        mBinding.tvOut.setOnClickListener(view -> {
            PoszhuanZhangDialog zhangDialog = new PoszhuanZhangDialog(this, null);
            zhangDialog.setOnPayClickListener((amount, pwd) -> {
                mViewModel.out(amount, pwd, zhangDialog::dismiss);
            });
            zhangDialog.show();
        });
        mBinding.tvAll.setOnClickListener(view -> BonusActivity.start(this, getIntent().getStringExtra("symbol")));
        mBinding.ivVisible.setOnClickListener(v -> {
            v.setActivated(!v.isActivated());
            mViewModel.toggleVisible();
        });
    }

    @Override
    public void initDatas() {
        String symbol = getIntent().getStringExtra("symbol");
        mBinding.tvCoinName.setText(String.format("折合总资产（%s）", symbol));
        mViewModel = getViewModel(PosCoinDetailViewModel.class);
        mViewModel.getWalletData(symbol);
        mViewModel.mPosWalletDataMutableLiveData.observe(this, posWalletData -> {
            if(mBinding.ivVisible.isActivated()) {
                mBinding.tvCoinCount.setText(String.format("%s", posWalletData.getPosInvestAmount()));
//                mBinding.tvCoinCny.setText(posWalletData.getPosInvestAmount().multiply(posWalletData.getUsdRate()).setScale(2, BigDecimal.ROUND_DOWN).toEngineeringString());
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

    }

    @Override
    protected void sendTrade(String afterSignVal) {

    }
}
