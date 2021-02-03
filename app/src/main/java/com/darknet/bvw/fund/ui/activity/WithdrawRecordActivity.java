package com.darknet.bvw.fund.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.databinding.ActivityPledgeRecordBinding;
import com.darknet.bvw.databinding.ActivityWithdrawRecordBinding;
import com.darknet.bvw.fund.ui.adapter.WithdrawRecordAdapter;
import com.darknet.bvw.fund.vm.PledgeDetailViewModel;
import com.darknet.bvw.util.StatusBarUtil;
import com.darknet.bvw.util.binding.DividerLine;

public class WithdrawRecordActivity extends BaseBindingActivity<ActivityWithdrawRecordBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_withdraw_record;
    }

    @Override
    public void initView() {
        StatusBarUtil.setStatusBarColor(this, R.color.bg_141624);
        mBinding.titleLayout.layBack.setOnClickListener(v -> finish());
        mBinding.titleLayout.title.setText(R.string.withdraw_record);
        mBinding.setAdapter(new WithdrawRecordAdapter());
        mBinding.rvIncomeFund.addItemDecoration(new DividerLine(this, DividerLine.LineDrawMode.VERTICAL,1));
        PledgeDetailViewModel viewModel = getViewModel(PledgeDetailViewModel.class);
        mBinding.setVm(viewModel);
    }

    @Override
    public void initDatas() {

    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, WithdrawRecordActivity.class));
    }
}
