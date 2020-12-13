package com.darknet.bvw.order.ui;

import android.content.Context;
import android.content.Intent;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.activity.fragment.IncomeRecordFragment;
import com.darknet.bvw.activity.fragment.IncomeRecordFragment2;
import com.darknet.bvw.base.BaseFragmentPagerAdapter;
import com.darknet.bvw.common.BaseBindingFragment;
import com.darknet.bvw.common.BaseFragment;
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
        BaseFragmentPagerAdapter<Fragment> adapter = new BaseFragmentPagerAdapter<>(getSupportFragmentManager());
        adapter.add(OrderListFragment.newInstance(), getString(R.string.hz_input_all_sign));
        adapter.add(OrderListFragment.newInstance(), getString(R.string.order_to_be_paid));
        adapter.add(OrderListFragment.newInstance(), getString(R.string.order_to_be_taken));
        adapter.add(OrderListFragment.newInstance(), getString(R.string.order_to_be_delivered));
        mBinding.viewPager.setOffscreenPageLimit(3);
        mBinding.viewPager.setAdapter(adapter);
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
    }

    @Override
    public void initDatas() {

    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, OrderListActivity.class));
    }
}
