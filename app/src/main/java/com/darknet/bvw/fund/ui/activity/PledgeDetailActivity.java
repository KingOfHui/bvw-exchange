package com.darknet.bvw.fund.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.databinding.ActivityPledgeDetailBinding;
import com.darknet.bvw.fund.bean.DefiTotalDataBySymbol;
import com.darknet.bvw.fund.ui.adapter.WithdrawRecordAdapter;
import com.darknet.bvw.fund.vm.PledgeDetailViewModel;
import com.darknet.bvw.net.retrofit.MvvmNetworkObserver;
import com.darknet.bvw.util.StatusBarUtil;

import cn.hutool.core.collection.CollectionUtil;

/**
 * <pre>
 *     author : dinghui
 *     e-mail : dinghui@bcbook.com
 *     time   : 2021/02/03
 *     desc   : 质押详情页面
 *     version: 1.0
 * </pre>
 */
public class PledgeDetailActivity extends BaseBindingActivity<ActivityPledgeDetailBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_pledge_detail;
    }

    @Override
    public void initView() {
        String symbol = getIntent().getStringExtra("symbol");
        StatusBarUtil.setStatusBarColor(this, R.color.bg_141624);
        mBinding.titleLayout.layBack.setOnClickListener(v -> finish());
        mBinding.titleLayout.title.setText("BTD");
        mBinding.titleLayout.titleRight.setText(R.string.pledge_record);
        mBinding.titleLayout.titleRight.setVisibility(View.VISIBLE);
        mBinding.titleLayout.titleRight.setTextColor(Color.parseColor("#15D496"));
        mBinding.titleLayout.titleRight.setOnClickListener(view -> PledgeRecordActivity.start(this, symbol));
        mBinding.tvAll.setOnClickListener(view -> WithdrawRecordActivity.start(this));
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        decoration.setDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.line_4E4A5E)));
        mBinding.rv.addItemDecoration(decoration);
        mBinding.setAdapter(new WithdrawRecordAdapter());
        PledgeDetailViewModel viewModel = getViewModel(PledgeDetailViewModel.class);
        mBinding.setVm(viewModel);
        viewModel.mDefiTotalDataBySymbolMutableLiveData.observe(this, defiTotalDataBySymbol -> {
            StringBuilder holder = new StringBuilder();
            if (CollectionUtil.isNotEmpty(defiTotalDataBySymbol.getInvestVOList())) {
                for (DefiTotalDataBySymbol.DeFiTotalInvestVO deFiTotalInvestVO : defiTotalDataBySymbol.getInvestVOList()) {
                    holder.append(deFiTotalInvestVO.getInvestAmount()).append(deFiTotalInvestVO.getSymbol()).append(" ");
                }
            }
            mBinding.tvHolderDetail.setText(TextUtils.isEmpty(holder.toString()) ? "0" : holder.toString());
            mBinding.cvvNetAmount.setTopStr(defiTotalDataBySymbol.getInvestAmount());
            mBinding.cvvNetAmount.setBottomText(String.format(getString(R.string.net_amount), defiTotalDataBySymbol.getSymbol()));
            mBinding.cvvHistoryBonus.setTopStr(defiTotalDataBySymbol.getHistoryBonus());
            mBinding.cvvWithdraw.setTopStr(defiTotalDataBySymbol.getBalance());
        });
        viewModel.getTotalDataBySymbol();
    }

    @Override
    public void initDatas() {

    }

    public static void start(Context context, String symbol) {
        Intent intent = new Intent(context, PledgeDetailActivity.class);
        intent.putExtra("symbol", symbol);
        context.startActivity(intent);
    }
}
