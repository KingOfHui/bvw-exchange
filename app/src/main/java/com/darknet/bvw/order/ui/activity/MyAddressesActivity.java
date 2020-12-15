package com.darknet.bvw.order.ui.activity;

import androidx.databinding.ViewDataBinding;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.base.BaseDataBindingAdapter;
import com.darknet.bvw.databinding.ActivityMyAddressesBinding;
import com.darknet.bvw.databinding.ItemAddressBinding;

/**
 * @ClassName MyAddressesActivity
 * @Description
 * @Author dinghui
 * @Date 2020/12/15 0015 17:21
 */
public class MyAddressesActivity extends BaseBindingActivity<ActivityMyAddressesBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_my_addresses;
    }

    @Override
    public void initView() {
        mBinding.setAdapter(new AddressAdapter());
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
