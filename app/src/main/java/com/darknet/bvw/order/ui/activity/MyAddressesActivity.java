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

import java.util.ArrayList;

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
        mBinding.setAdapter(new AddressAdapter());
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            strings.add("position:" + i);
        }
        mBinding.getAdapter().setNewData(strings);
        mBinding.getAdapter().setOnItemClickListener((adapter, view, position) -> AddAddressActivity.start(MyAddressesActivity.this));
    }

    @Override
    public void initDatas() {

    }

    public static class AddressAdapter extends BaseDataBindingAdapter<String, ItemAddressBinding> {
        public AddressAdapter() {
            super(R.layout.item_address);
        }

        @Override
        protected void convert(ItemAddressBinding binding, String item) {

        }
    }

}
