package com.darknet.bvw.order.ui.activity;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.databinding.ActivityOrderConfirmBinding;
import com.darknet.bvw.order.bean.ShippingAddress;
import com.darknet.bvw.order.ui.adapter.OrderGoodsAdapter;
import com.darknet.bvw.order.vm.ConfirmOrderViewModel;
import com.darknet.bvw.order.vm.MyAddressViewModel;

/**
 * @ClassName ConfirmOrderActivity
 * @Description
 * @Author dinghui
 * @Date 2020/12/15 0015 14:47
 */
public class ConfirmOrderActivity extends BaseBindingActivity<ActivityOrderConfirmBinding> {

    private ShippingAddress mAddress;
    private MyAddressViewModel mAddressViewModel;

    public static void start(Context context) {
        context.startActivity(new Intent(context, ConfirmOrderActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_confirm;
    }

    @Override
    public void initView() {
        mAddressViewModel = getViewModel(MyAddressViewModel.class);
        ConfirmOrderViewModel orderViewModel = getViewModel(ConfirmOrderViewModel.class);
        mAddressViewModel.refresh();
        mAddressViewModel.selectAddress.observe(this, shippingAddress -> {
            if (shippingAddress != null) {
                mAddress = shippingAddress;
                mBinding.tvContact.setText(String.format("%s   %s", mAddress.getUser_name(), mAddress.getTel_number()));
                mBinding.tvAddress.setText(mAddress.getNation() + mAddress.getProvince() + mAddress.getCity() + mAddress.getCounty() + mAddress.getDetail_info());
            }
        });
        mBinding.layoutTitle.layBack.setOnClickListener(view -> finish());
        mBinding.layoutTitle.title.setText(getString(R.string.confirm_order));
        mBinding.setAdapter(new OrderGoodsAdapter());
        mBinding.tvAddressTip.setOnClickListener(view -> {
            Intent intent = new Intent(this, MyAddressesActivity.class);
            intent.putExtra("selectId", mAddress.getId());
            startActivityForResult(intent,10000);
        });
        mBinding.ivEdit.setOnClickListener(view -> AddAddressActivity.start(this, mAddress, false));
    }

    @Override
    public void initDatas() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10000 && resultCode == RESULT_OK && data != null) {
            ShippingAddress address = (ShippingAddress) data.getSerializableExtra("address");
            if (address!=null) {
               mAddressViewModel.selectAddress.setValue(address);
            }
        }
    }
}
