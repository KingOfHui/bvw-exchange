package com.darknet.bvw.order.ui.activity;

import android.content.Context;
import android.content.Intent;

import androidx.databinding.ViewDataBinding;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.databinding.ActivityCouponListBinding;
import com.darknet.bvw.order.ui.adapter.CouponAdapter;
import com.darknet.bvw.order.vm.CouponViewModel;
import com.darknet.bvw.util.StatusBarUtil;

/**
 * @ClassName CouponListActivity
 * @Description
 * @Author dinghui
 * @Date 2020/12/24 0024 10:29
 */
public class CouponListActivity extends BaseBindingActivity<ActivityCouponListBinding> {
    public static void start(Context context) {
        context.startActivity(new Intent(context, CouponListActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_coupon_list;
    }

//    @Override
//    public boolean immerse() {
//        return true;
//    }

    @Override
    public void initView() {
        StatusBarUtil.setStatusBarColor(this, R.color.color_bg_181523);
        mBinding.layoutTitle.title.setText(getString(R.string.cash_coupon));
        mBinding.layoutTitle.titleRight.setText(getString(R.string.cash_coupon));
        mBinding.layoutTitle.titleRight.setTextColor(getResources().getColor(R.color._01FCDA));
        mBinding.setAdapter(new CouponAdapter());

    }

    @Override
    public void initDatas() {
        CouponViewModel viewModel = getViewModel(CouponViewModel.class);
        mBinding.setVm(viewModel);
        viewModel.refresh();
    }
}
