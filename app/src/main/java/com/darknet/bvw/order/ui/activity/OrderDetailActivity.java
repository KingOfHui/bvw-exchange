package com.darknet.bvw.order.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.databinding.ActivityOrderDetailBinding;

/**
 * @ClassName OrderDetailActivity
 * @Description
 * @Author dinghui
 * @Date 2020/12/14 0014 10:55
 */
public class OrderDetailActivity extends BaseBindingActivity<ActivityOrderDetailBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initView() {
        mBinding.layoutTitle.layBack.setOnClickListener(v -> finish());
        mBinding.layoutTitle.title.setText(R.string.order_detail);

    }

    @Override
    public void initDatas() {

    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, OrderDetailActivity.class));
    }
}
