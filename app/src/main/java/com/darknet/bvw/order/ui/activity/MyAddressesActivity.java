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

    private MyAddressViewModel mViewModel;

    public static void start(Context context) {
        context.startActivity(new Intent(context, MyAddressesActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_addresses;
    }

    @Override
    public void initView() {
        int selectId = getIntent().getIntExtra("selectId", -1);
        mViewModel = getViewModel(MyAddressViewModel.class);
        mBinding.setVm(mViewModel);
        mBinding.layoutTitle.layBack.setOnClickListener((view -> finish()));
        mBinding.layoutTitle.title.setText(getString(R.string.my_shipping_address));
        mBinding.tvAddAddress.setOnClickListener((view -> AddAddressActivity.start(this, null, true)));
        AddressAdapter adapter = new AddressAdapter(selectId);
        mBinding.setAdapter(adapter);
        mBinding.getAdapter().setOnItemClickListener((adapter2, view, position) -> {
            ShippingAddress shippingAddress = adapter.getData().get(position);
            Intent intent = new Intent();
            intent.putExtra("address", shippingAddress);
            setResult(RESULT_OK,intent);
            finish();
        });
    }

    @Override
    public void initDatas() {
//        List<ShippingAddressModel> list = AddressDaoUtils.queryAll();
//        mBinding.getAdapter().setNewData(list);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.refresh();
    }

    public static class AddressAdapter extends BaseDataBindingAdapter<ShippingAddress, ItemAddressBinding> {
        private int selectId = -1;
        public AddressAdapter(int selectId) {
            super(R.layout.item_address);
            this.selectId = selectId;
        }

        @Override
        protected void convert(ItemAddressBinding binding, ShippingAddress item) {
            binding.tvName.setText(item.getUser_name());
            binding.tvMobile.setText(item.getTel_number());
            binding.tvAddress.setText(item.getDetail_info());
            binding.cbSelect.setSelected(selectId == -1 ? item.getDefault_state() == 1 : item.getId() == selectId);
            binding.tvEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddAddressActivity.start(v.getContext(), item, false);
                }
            });
        }
    }

}
