package com.darknet.bvw.qvkuaibao;

import android.content.Context;
import android.content.Intent;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.databinding.ActivityBonusListBinding;
import com.darknet.bvw.qvkuaibao.bean.PosWalletData;
import com.darknet.bvw.qvkuaibao.vm.PosCoinDetailViewModel;

public class BonusActivity extends BaseBindingActivity<ActivityBonusListBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_bonus_list;
    }

    @Override
    public void initView() {
        mBinding.titleLayout.layBack.setOnClickListener(view -> finish());
        mBinding.titleLayout.title.setText("收益记录");
        PosCoinDetailViewModel viewModel = getViewModel(PosCoinDetailViewModel.class);
        String symbol = getIntent().getStringExtra("symbol");
        mBinding.tvSymbol.setText(symbol);
        viewModel.getWalletData(symbol);
        viewModel.mPosWalletDataMutableLiveData.observe(this, new Observer<PosWalletData>() {
            @Override
            public void onChanged(PosWalletData posWalletData) {
//                mBinding.tvBonusToday.setText(posWalletData.getYesterdayPosBonusAmount());
                mBinding.tvAllBonus.setText(posWalletData.getPosBonusAmount());
            }
        });
    }

    @Override
    public void initDatas() {

    }

    public static void start(Context context, String symbol) {
        Intent intent = new Intent(context, BonusActivity.class);
        intent.putExtra("symbol", symbol);
        context.startActivity(intent);
    }
}
