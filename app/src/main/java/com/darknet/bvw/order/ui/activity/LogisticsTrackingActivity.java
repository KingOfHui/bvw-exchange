package com.darknet.bvw.order.ui.activity;

import android.content.Context;
import android.content.Intent;

import androidx.databinding.ViewDataBinding;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.base.BaseDataBindingAdapter;
import com.darknet.bvw.databinding.ActivityLogisticsTrackingBinding;
import com.darknet.bvw.databinding.ItemOrderListBinding;
import com.darknet.bvw.order.vm.LogisticsTrackingViewModel;

/**
 * 物流跟踪
 */
public class LogisticsTrackingActivity extends BaseBindingActivity<ActivityLogisticsTrackingBinding> {

    public static void start(Context context) {
        context.startActivity(new Intent(context, LogisticsTrackingActivity.class));
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_logistics_tracking;
    }

    @Override
    public void initView() {
        LogisticsTrackingViewModel viewModel = getViewModel(LogisticsTrackingViewModel.class);
        mBinding.setVm(viewModel);
        mBinding.setAdapter(new LogisticsAdapter());
        viewModel.refresh();
    }

    @Override
    public void initDatas() {

    }

    public static class LogisticsAdapter extends BaseDataBindingAdapter<String, ItemOrderListBinding> {

        public LogisticsAdapter() {
            super(R.layout.item_order_logistics);
        }

        @Override
        protected void convert(ItemOrderListBinding binding, String item) {

        }
    }
}
