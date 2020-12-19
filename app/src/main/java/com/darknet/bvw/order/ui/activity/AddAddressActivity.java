package com.darknet.bvw.order.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.databinding.ActivityAddAddressBinding;
import com.darknet.bvw.db.AddressDaoUtils;
import com.darknet.bvw.db.Entity.ShippingAddressModel;
import com.darknet.bvw.util.ToastUtils;

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
        mBinding.tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mBinding.etContact.getText().toString().trim();
                String address = mBinding.etAddress.getText().toString().trim();
                String mobile = mBinding.etMobile.getText().toString().trim();
                boolean checked = mBinding.switchDefault.isChecked();
                if (TextUtils.isEmpty(name)) {
                    ToastUtils.showSingleToast("请输入姓名");
                    return;
                }
                if (TextUtils.isEmpty(address)) {
                    ToastUtils.showSingleToast("请输入地址");
                    return;
                }
                if (TextUtils.isEmpty(mobile)) {
                    ToastUtils.showSingleToast("请输入手机号");
                    return;
                }
                ShippingAddressModel model = new ShippingAddressModel();
                model.setIsDefault(checked?1:0);
                model.setMobile(mobile);
                model.setName(name);
                model.setAddress(address);
                AddressDaoUtils.insertNewAddress(model);
                if (checked) {
                    AddressDaoUtils.updateAddress(model);
                }

            }
        });

    }

    @Override
    public void initDatas() {

    }
}
