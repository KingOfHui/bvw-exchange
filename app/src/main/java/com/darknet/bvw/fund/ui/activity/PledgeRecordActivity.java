package com.darknet.bvw.fund.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.databinding.ActivityPledgeRecordBinding;
import com.darknet.bvw.fund.ui.adapter.PledgeRecordAdapter;
import com.darknet.bvw.fund.ui.dialog.CancelTipDialog;
import com.darknet.bvw.fund.vm.PledgeRecordViewModel;
import com.darknet.bvw.util.StatusBarUtil;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

public class PledgeRecordActivity extends BaseBindingActivity<ActivityPledgeRecordBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_pledge_record;
    }

    @Override
    public void initView() {
        StatusBarUtil.setStatusBarColor(this, R.color.bg_141624);
        mBinding.titleLayout.layBack.setOnClickListener(v -> finish());
        mBinding.titleLayout.title.setText(R.string.pledge_record);
        mBinding.setAdapter(new PledgeRecordAdapter());
        PledgeRecordViewModel viewModel = getViewModel(PledgeRecordViewModel.class);
        mBinding.setVm(viewModel);
        mBinding.titleLayout.title.setOnClickListener(view -> {
            new CancelTipDialog(this).show();
        });
    }

    @Override
    public void initDatas() {

    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, PledgeRecordActivity.class));
    }
}
