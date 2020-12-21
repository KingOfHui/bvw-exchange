package com.darknet.bvw.order.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.Observer;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.databinding.ActivityAddAddressBinding;
import com.darknet.bvw.db.AddressDaoUtils;
import com.darknet.bvw.db.Entity.ShippingAddressModel;
import com.darknet.bvw.net.retrofit.ApiInterface;
import com.darknet.bvw.net.retrofit.BIWNetworkApi;
import com.darknet.bvw.net.retrofit.BaseObserver;
import com.darknet.bvw.net.retrofit.RequestBodyBuilder;
import com.darknet.bvw.order.bean.ShippingAddress;
import com.darknet.bvw.order.vm.MyAddressViewModel;
import com.darknet.bvw.util.ToastUtils;

import java.io.Serializable;

public class AddAddressActivity extends BaseBindingActivity<ActivityAddAddressBinding> {

    private ShippingAddress mAddress;
    private boolean mIsAdd;

    public static void start(Context context, ShippingAddress item, boolean isAdd) {
        Intent intent = new Intent(context, AddAddressActivity.class);
        intent.putExtra("address", item);
        intent.putExtra("isAdd", isAdd);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_address;
    }

    @Override
    public void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mAddress = (ShippingAddress) bundle.getSerializable("address");
            mIsAdd = bundle.getBoolean("isAdd");
        }
        if (mAddress != null) {
            mBinding.etAddress.setText(mAddress.getDetail_info());
            mBinding.etContact.setText(mAddress.getUser_name());
            mBinding.etMobile.setText(mAddress.getTel_number());
            mBinding.switchDefault.setChecked(mAddress.getDefault_state() == 1);
        }
        MyAddressViewModel viewModel = getViewModel(MyAddressViewModel.class);
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

                ShippingAddress shippingAddress = new ShippingAddress();
                shippingAddress.setTel_number(mobile);
                shippingAddress.setUser_name(name);
                shippingAddress.setDetail_info(address);
                shippingAddress.setDefault_state(checked ? 1 : 0);
                if (mIsAdd) {
                    viewModel.addAddress(shippingAddress);
                } else {
                    viewModel.updateAddress(shippingAddress);
                }

                /*ShippingAddressModel model = new ShippingAddressModel();
                model.setIsDefault(checked?1:0);
                model.setMobile(mobile);
                model.setName(name);
                model.setAddress(address);
                AddressDaoUtils.insertNewAddress(model);
                if (checked) {
                    AddressDaoUtils.updateAddress(model);
                }*/
            }
        });
        viewModel.isSuccess.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    finish();
                }
            }
        });

    }

    @Override
    public void initDatas() {

    }
}
