package com.darknet.bvw.order.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.base.BaseDataBindingAdapter;
import com.darknet.bvw.databinding.ActivityMyAddressesBinding;
import com.darknet.bvw.databinding.ItemAddressBinding;
import com.darknet.bvw.order.bean.ShippingAddress;
import com.darknet.bvw.order.vm.MyAddressViewModel;

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
        MyAddressViewModel viewModel = getViewModel(MyAddressViewModel.class);
        mBinding.setVm(viewModel);
        mBinding.layoutTitle.layBack.setOnClickListener((view -> finish()));
        mBinding.layoutTitle.title.setText(getString(R.string.my_shipping_address));
        mBinding.tvAddAddress.setOnClickListener((view -> AddAddressActivity.start(this, null, true)));
        mBinding.setAdapter(new AddressAdapter());
//        mBinding.getAdapter().setOnItemClickListener((adapter, view, position) -> AddAddressActivity.start(MyAddressesActivity.this, null));
        viewModel.refresh();
    }

    @Override
    public void initDatas() {
//        List<ShippingAddressModel> list = AddressDaoUtils.queryAll();
//        mBinding.getAdapter().setNewData(list);
    }

    public static class AddressAdapter extends BaseDataBindingAdapter<ShippingAddress, ItemAddressBinding> {
        public AddressAdapter() {
            super(R.layout.item_address);
        }

        @Override
        protected void convert(ItemAddressBinding binding, ShippingAddress item) {
            binding.tvName.setText(item.getUser_name());
            binding.tvMobile.setText(item.getTel_number());
            binding.tvAddress.setText(item.getDetail_info());
            binding.cbSelect.setSelected(true);
            binding.cbSelect.setEnabled(true);
            binding.tvEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddAddressActivity.start(v.getContext(), item, false);
                }
            });
        }
    }

}
