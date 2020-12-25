package com.darknet.bvw.order.ui.activity;

import android.content.Context;
import android.content.Intent;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.databinding.ActivityMyCouponListBinding;
import com.darknet.bvw.order.ui.adapter.MyCouponAdapter;
import com.darknet.bvw.order.vm.MyCouponViewModel;
import com.darknet.bvw.util.StatusBarUtil;

/**
 * @ClassName CouponListActivity
 * @Description
 * @Author dinghui
 * @Date 2020/12/24 0024 10:29
 */
public class MyCouponListActivity extends BaseBindingActivity<ActivityMyCouponListBinding> {
    public static void start(Context context) {
        context.startActivity(new Intent(context, MyCouponListActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_coupon_list;
    }

    @Override
    public void initView() {
        StatusBarUtil.setStatusBarColor(this, R.color.color_bg_181523);
        mBinding.layoutTitle.title.setText(getString(R.string.my_cash_coupon));
        MyCouponAdapter adapter = new MyCouponAdapter();
        mBinding.setAdapter(adapter);
    }

    @Override
    public void initDatas() {
        MyCouponViewModel viewModel = getViewModel(MyCouponViewModel.class);
        mBinding.setVm(viewModel);
        viewModel.refresh();
    }
}
