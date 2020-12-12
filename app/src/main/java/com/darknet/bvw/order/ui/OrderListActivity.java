package com.darknet.bvw.order.ui;

import android.content.Context;
import android.content.Intent;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.databinding.ViewDataBinding;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.databinding.ActivityOrderListBinding;
import com.darknet.bvw.order.vm.OrderListViewModel;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import jnr.ffi.annotations.In;

/**
 * @ClassName OrderListActivity
 * @Description
 * @Author dinghui
 * @Date 2020/12/12 0012 16:48
 */
public class OrderListActivity extends BaseBindingActivity<ActivityOrderListBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_list;
    }

    @Override
    public void initView() {
        OrderListViewModel viewModel = getViewModel(OrderListViewModel.class);
        mBinding.layoutTitle.layBack.setOnClickListener((view -> finish()));
        mBinding.layoutTitle.title.setText(getString(R.string.order_all));
    }

    @Override
    public void initDatas() {

    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, OrderListActivity.class));
    }
}
