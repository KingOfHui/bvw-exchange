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
            mBinding.etNation.setText(mAddress.getNation());
            mBinding.etProvince.setText(mAddress.getProvince());
            mBinding.etCity.setText(mAddress.getCity());
            mBinding.etContry.setText(mAddress.getCounty());
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
                String nation = mBinding.etNation.getText().toString().trim();
                String province = mBinding.etProvince.getText().toString().trim();
                String city = mBinding.etCity.getText().toString().trim();
                String contry = mBinding.etContry.getText().toString().trim();
                String postal = mBinding.etPostal.getText().toString().trim();

                boolean checked = mBinding.switchDefault.isChecked();
                if (TextUtils.isEmpty(name)) {
                    ToastUtils.showSingleToast(getString(R.string.input_name));
                    return;
                }
                if (TextUtils.isEmpty(nation)) {
                    ToastUtils.showSingleToast("请输入国家");
                    return;
                }
                 if (TextUtils.isEmpty(province)) {
                    ToastUtils.showSingleToast("请输入省/州/地区");
                    return;
                }
                 if (TextUtils.isEmpty(city)) {
                    ToastUtils.showSingleToast("请输入城市");
                    return;
                }
                 if (TextUtils.isEmpty(contry)) {
                    ToastUtils.showSingleToast("请输入县");
                    return;
                }
                 if (TextUtils.isEmpty(address)) {
                    ToastUtils.showSingleToast("请输入详细地址");
                    return;
                }
                 if (TextUtils.isEmpty(postal)) {
                    ToastUtils.showSingleToast("请输入邮政编码");
                    return;
                }
                if (TextUtils.isEmpty(mobile)) {
                    ToastUtils.showSingleToast(getString(R.string.input_mobile));
                    return;
                }

                if (mAddress == null) {
                    mAddress = new ShippingAddress();
                }
                mAddress.setNation(nation);
                mAddress.setProvince(province);
                mAddress.setCity(city);
                mAddress.setCounty(contry);
                mAddress.setPostal(postal);
                mAddress.setTel_number(mobile);
                mAddress.setUser_name(name);
                mAddress.setDetail_info(address);
                mAddress.setDefault_state(checked ? 1 : 0);
                if (mIsAdd) {
                    viewModel.addAddress(mAddress);
                } else {
                    viewModel.updateAddress(mAddress);
                }
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
