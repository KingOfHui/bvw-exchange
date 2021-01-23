package com.darknet.bvw.qvkuaibao;

import android.content.Context;
import android.content.Intent;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.base.BasePayActivity;
import com.darknet.bvw.databinding.ActivityCoinDetailBinding;
import com.darknet.bvw.qvkuaibao.adapter.BonusListAdapter;
import com.darknet.bvw.qvkuaibao.dialog.PoszhuanZhangDialog;
import com.darknet.bvw.qvkuaibao.vm.PosCoinDetailViewModel;

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
        mBinding.setAdapter(new BonusListAdapter());
        mBinding.tvIn.setOnClickListener(view -> {
            PoszhuanZhangDialog zhangDialog = new PoszhuanZhangDialog(this, null);
            zhangDialog.setOnPayClickListener(new PoszhuanZhangDialog.OnPayClickListener() {
                @Override
                public void payClick(String amount, String pwd) {

                }

            });
            zhangDialog.show();
        });
        mBinding.tvOut.setOnClickListener(view -> {
            PoszhuanZhangDialog zhangDialog = new PoszhuanZhangDialog(this, null);
            zhangDialog.show();
        });
        mBinding.tvAll.setOnClickListener(view -> BonusActivity.start(this, getIntent().getStringExtra("symbol")));
    }

    @Override
    public void initDatas() {
        String symbol = getIntent().getStringExtra("symbol");
        mBinding.tvCoinName.setText(String.format("折合总资产（%s）", symbol));
        PosCoinDetailViewModel viewModel = getViewModel(PosCoinDetailViewModel.class);
        viewModel.getWalletData(symbol);
        viewModel.mPosWalletDataMutableLiveData.observe(this, posWalletData -> {
            mBinding.tvCoinCount.setText(posWalletData.getPosInvestAmount());
            mBinding.tvDailyBonus.setText(posWalletData.getDayRate());
            mBinding.tvYesterdayBonus.setText(posWalletData.getYesterdayPosBonusAmount());
            mBinding.tvTotalBonus.setText(posWalletData.getPosBonusAmount());
        });
    }

    @Override
    protected void createTrade(String address) {

    }

    @Override
    protected void sendTrade(String afterSignVal) {

    }
}
