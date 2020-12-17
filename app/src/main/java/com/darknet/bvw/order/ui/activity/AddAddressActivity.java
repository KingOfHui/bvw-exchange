package com.darknet.bvw.order.ui.activity;

import android.content.Context;
import android.content.Intent;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.databinding.ActivityAddAddressBinding;

public class AddAddressActivity extends BaseBindingActivity<ActivityAddAddressBinding> {
    public static void start(Context context) {
        context.startActivity(new Intent(context, AddAddressActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_address;
    }

    @Override
    public void initView() {
        mBinding.layoutTitle.layBack.setOnClickListener((view -> finish()));
        mBinding.layoutTitle.title.setText(getString(R.string.add_address));


    }

    @Override
    public void initDatas() {

    }
}
