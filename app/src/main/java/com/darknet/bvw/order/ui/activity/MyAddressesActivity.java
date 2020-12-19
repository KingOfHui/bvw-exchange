package com.darknet.bvw.order.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.base.BaseDataBindingAdapter;
import com.darknet.bvw.databinding.ActivityMyAddressesBinding;
import com.darknet.bvw.databinding.ItemAddressBinding;
import com.darknet.bvw.db.AddressDaoUtils;
import com.darknet.bvw.db.Entity.ShippingAddressModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MyAddressesActivity
 * @Description
 * @Author dinghui
 * @Date 2020/12/15 0015 17:21
 */
public class MyAddressesActivity extends BaseBindingActivity<ActivityMyAddressesBinding> {

    public static void start(Context context) {
        context.startActivity(new Intent(context, MyAddressesActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_addresses;
    }

    @Override
    public void initView() {
        mBinding.layoutTitle.layBack.setOnClickListener((view -> finish()));
        mBinding.layoutTitle.title.setText(getString(R.string.my_shipping_address));
        mBinding.tvAddAddress.setOnClickListener((view -> AddAddressActivity.start(this)));
        mBinding.setAdapter(new AddressAdapter());
        mBinding.getAdapter().setOnItemClickListener((adapter, view, position) -> AddAddressActivity.start(MyAddressesActivity.this));
    }

    @Override
    public void initDatas() {
        List<ShippingAddressModel> list = AddressDaoUtils.queryAll();
        mBinding.getAdapter().setNewData(list);
    }

    public static class AddressAdapter extends BaseDataBindingAdapter<ShippingAddressModel, ItemAddressBinding> {
        public AddressAdapter() {
            super(R.layout.item_address);
        }

        @Override
        protected void convert(ItemAddressBinding binding, ShippingAddressModel item) {
            binding.tvName.setText(item.getName());
            binding.tvMobile.setText(item.getMobile());
            binding.tvAddress.setText(item.getAddress());
            binding.cbSelect.setSelected(true);
        }
    }

}
