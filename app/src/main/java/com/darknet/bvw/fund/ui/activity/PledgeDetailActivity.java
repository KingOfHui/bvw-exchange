package com.darknet.bvw.fund.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.databinding.ActivityPledgeDetailBinding;
import com.darknet.bvw.fund.ui.adapter.WithdrawRecordAdapter;
import com.darknet.bvw.fund.vm.PledgeDetailViewModel;
import com.darknet.bvw.net.retrofit.MvvmNetworkObserver;
import com.darknet.bvw.util.StatusBarUtil;

/**
 * <pre>
 *     author : dinghui
 *     e-mail : dinghui@bcbook.com
 *     time   : 2021/02/03
 *     desc   :
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
        StatusBarUtil.setStatusBarColor(this, R.color.bg_141624);
        mBinding.titleLayout.layBack.setOnClickListener(v -> finish());
        mBinding.titleLayout.title.setText("BTD");
        mBinding.titleLayout.titleRight.setText(R.string.pledge_record);
        mBinding.titleLayout.titleRight.setVisibility(View.VISIBLE);
        mBinding.titleLayout.titleRight.setTextColor(Color.parseColor("#15D496"));
        mBinding.titleLayout.titleRight.setOnClickListener(view -> PledgeRecordActivity.start(this));
        mBinding.tvAll.setOnClickListener(view -> WithdrawRecordActivity.start(this));
        mBinding.setAdapter(new WithdrawRecordAdapter());
        PledgeDetailViewModel viewModel = getViewModel(PledgeDetailViewModel.class);
        mBinding.setVm(viewModel);
    }

    @Override
    public void initDatas() {

    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, PledgeDetailActivity.class));
    }
}
