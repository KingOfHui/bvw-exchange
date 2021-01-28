package com.darknet.bvw.qvkuaibao;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.activity.YuBiBaoDetailActivity;
import com.darknet.bvw.databinding.ActivityQvkuaibaoCoinListBinding;
import com.darknet.bvw.qvkuaibao.adapter.CoinListAdapter;
import com.darknet.bvw.qvkuaibao.bean.PosWallet;
import com.darknet.bvw.qvkuaibao.vm.QvKuaiBaoViewModel;

public class QvKuaiBaoCoinListActivity extends BaseBindingActivity<ActivityQvkuaibaoCoinListBinding> {
    public static void startSelf(Context requireContext) {
        requireContext.startActivity(new Intent(requireContext, QvKuaiBaoCoinListActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_qvkuaibao_coin_list;
    }

    @Override
    public void initView() {
        mBinding.titleLayout.layBack.setOnClickListener(view -> finish());
        mBinding.titleLayout.title.setText("余币宝");
        mBinding.titleLayout.titleRight.setText("规则");
        mBinding.titleLayout.titleRight.setVisibility(View.VISIBLE);
        mBinding.titleLayout.titleRight.setOnClickListener(view -> {
            Intent yubiIntent = new Intent(this, YuBiBaoDetailActivity.class);
            startActivity(yubiIntent);
        });
        QvKuaiBaoViewModel viewModel = getViewModel(QvKuaiBaoViewModel.class);
        mBinding.setVm(viewModel);
        CoinListAdapter adapter = new CoinListAdapter();
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            PosWallet item = adapter.getItem(position);
            if (item!=null) {
                CoinDetailActivity.startSelf(this, item.getSymbol(), item.getUsd_rate());
            }
        });
        mBinding.setAdapter(adapter);
        viewModel.refresh();

    }

    @Override
    public void initDatas() {

    }
}
