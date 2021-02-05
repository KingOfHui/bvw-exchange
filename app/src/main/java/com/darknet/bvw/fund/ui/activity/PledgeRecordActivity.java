package com.darknet.bvw.fund.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.databinding.ActivityPledgeRecordBinding;
import com.darknet.bvw.fund.bean.DefiInvest;
import com.darknet.bvw.fund.ui.adapter.PledgeRecordAdapter;
import com.darknet.bvw.fund.ui.dialog.CancelTipDialog;
import com.darknet.bvw.fund.vm.PledgeRecordViewModel;
import com.darknet.bvw.util.StatusBarUtil;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

/**
 * 质押记录页面
 */
public class PledgeRecordActivity extends BaseBindingActivity<ActivityPledgeRecordBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_pledge_record;
    }

    @Override
    public void initView() {
        StatusBarUtil.setStatusBarColor(this, R.color.bg_141624);
        PledgeRecordViewModel viewModel = getViewModel(PledgeRecordViewModel.class);
        mBinding.titleLayout.layBack.setOnClickListener(v -> finish());
        mBinding.titleLayout.title.setText(R.string.pledge_record);
        PledgeRecordAdapter pledgeRecordAdapter = new PledgeRecordAdapter();
        pledgeRecordAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            DefiInvest item = pledgeRecordAdapter.getItem(position);
            if (item != null) {
                CancelTipDialog cancelTipDialog = new CancelTipDialog(this, item);
                cancelTipDialog.setListener(() -> viewModel.cancelPledge(item.getId()));
                cancelTipDialog.show();
            }
        });
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        decoration.setDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.line_4E4A5E)));
        mBinding.rvIncomeFund.addItemDecoration(decoration);
        mBinding.setAdapter(pledgeRecordAdapter);
        mBinding.setVm(viewModel);
        viewModel.refresh();
    }

    @Override
    public void initDatas() {

    }

    public static void start(Context context, String symbol) {
        Intent intent = new Intent(context, PledgeRecordActivity.class);
        intent.putExtra("symbol", symbol);
        context.startActivity(intent);
    }
}
