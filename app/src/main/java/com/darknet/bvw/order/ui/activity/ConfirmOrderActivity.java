package com.darknet.bvw.order.ui.activity;

import android.content.Context;
import android.content.Intent;

import androidx.databinding.ViewDataBinding;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.databinding.ActivityOrderConfirmBinding;

/**
 * @ClassName ConfirmOrderActivity
 * @Description
 * @Author dinghui
 * @Date 2020/12/15 0015 14:47
 */
public class ConfirmOrderActivity extends BaseBindingActivity<ActivityOrderConfirmBinding> {
    public static void start(Context context) {
        context.startActivity(new Intent(context, ConfirmOrderActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_confirm;
    }

    @Override
    public void initView() {
        mBinding.layoutTitle.layBack.setOnClickListener(view -> finish());
        mBinding.layoutTitle.title.setText(getString(R.string.confirm_order));
    }

    @Override
    public void initDatas() {

    }
}
