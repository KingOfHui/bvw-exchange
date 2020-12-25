package com.darknet.bvw.order.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.databinding.ActivityOrderConfirmBinding;
import com.darknet.bvw.mall.bean.GoodsDetailBean;
import com.darknet.bvw.order.bean.CartData;
import com.darknet.bvw.order.bean.CouponBean;
import com.darknet.bvw.order.bean.MyCouponBean;
import com.darknet.bvw.order.bean.ShippingAddress;
import com.darknet.bvw.order.ui.adapter.ConfirmGoodsAdapter;
import com.darknet.bvw.order.vm.ConfirmOrderViewModel;
import com.darknet.bvw.order.vm.MyAddressViewModel;
import com.darknet.bvw.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.collection.CollectionUtil;

/**
 * @ClassName ConfirmOrderActivity
 * @Description
 * @Author dinghui
 * @Date 2020/12/15 0015 14:47
 */
public class ConfirmOrderActivity extends BaseBindingActivity<ActivityOrderConfirmBinding> {

    private ShippingAddress mAddress;
    private MyCouponBean mSelectCouponBean;
    private MyAddressViewModel mAddressViewModel;
    List<CartData.CartItemListBean> mCartItemListBeans = new ArrayList<>();

    public static void start(Context context, GoodsDetailBean.SkuListBean selectSkuListBean, GoodsDetailBean goodsDetailBean) {
        Intent intent = new Intent(context, ConfirmOrderActivity.class);
        intent.putExtra("selectSkuListBean", selectSkuListBean);
        intent.putExtra("goodsDetailBean", goodsDetailBean);
        context.startActivity(intent);
    }
    public static void start(Context context) {
        Intent intent = new Intent(context, ConfirmOrderActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_confirm;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        Intent data = getIntent();
        GoodsDetailBean.SkuListBean selectSkuListBean = (GoodsDetailBean.SkuListBean) data.getSerializableExtra("selectSkuListBean");
        GoodsDetailBean goodsDetailBean = (GoodsDetailBean) data.getSerializableExtra("goodsDetailBean");
        mAddressViewModel = getViewModel(MyAddressViewModel.class);
        ConfirmOrderViewModel orderViewModel = getViewModel(ConfirmOrderViewModel.class);

        if (selectSkuListBean != null && goodsDetailBean != null) {
            CartData.CartItemListBean cartItemListBean = new CartData.CartItemListBean();
            cartItemListBean.setProduct_name(goodsDetailBean.getName());
            cartItemListBean.setSp1(selectSkuListBean.getSp1());
            cartItemListBean.setPrice(new BigDecimal(selectSkuListBean.getPrice()));
            cartItemListBean.setProduct_img_url(goodsDetailBean.getImg_url());
            cartItemListBean.setOriginal_price(new BigDecimal(selectSkuListBean.getOriginal_price()));
            cartItemListBean.setQuantity(selectSkuListBean.getQuantity());
            mCartItemListBeans.add(cartItemListBean);
        } else {
            orderViewModel.getCartList();
        }
        mBinding.layoutTitle.layBack.setOnClickListener(view -> finish());
        mBinding.layoutTitle.title.setText(getString(R.string.confirm_order));
        ConfirmGoodsAdapter adapter = new ConfirmGoodsAdapter();
        adapter.setNewData(mCartItemListBeans);
        mBinding.setAdapter(adapter);
        orderViewModel.cartItemListLive.observe(this, adapter::setNewData);
        mBinding.tvAddressTip.setOnClickListener(view -> {
            Intent intent = new Intent(this, MyAddressesActivity.class);
            intent.putExtra("selectId", mAddress.getId());
            startActivityForResult(intent, 10000);
        });
        mBinding.clDiscounts.setOnClickListener(view -> {
            startActivity(new Intent(this,CouponListActivity.class));
        });
        mBinding.ivEdit.setOnClickListener(view -> AddAddressActivity.start(this, mAddress, false));
        mAddressViewModel.refresh();
        mAddressViewModel.selectAddress.observe(this, shippingAddress -> {
            if (shippingAddress != null) {
                mAddress = shippingAddress;
                mBinding.tvContact.setText(String.format("%s   %s", mAddress.getUser_name(), mAddress.getTel_number()));
                mBinding.tvAddress.setText(String.format("%s%s%s%s%s",
                        mAddress.getNation(), mAddress.getProvince(), mAddress.getCity(), mAddress.getCounty(), mAddress.getDetail_info()));
            }
        });
        mBinding.tvSubmitOrder.setOnClickListener(view -> {
            if (mAddress == null) {
                ToastUtils.showToast(getString(R.string.select_address_first));
                return;
            }

            String remark = mBinding.etRemark.getText().toString().trim();


            if (selectSkuListBean == null) {
                orderViewModel.submitCartOrder(mAddress.getId(),remark,mSelectCouponBean,orderViewModel.cartItemListLive.getValue().get(0).getProduct_id());
            } else {
                orderViewModel.submitOrder(mAddress.getId(),remark,mSelectCouponBean,
                        selectSkuListBean.getQuantity(),selectSkuListBean.getId(),selectSkuListBean.getProduct_id());
            }
        });
        orderViewModel.submitCartOrderLive.observe(this, submitOrderResps -> {
            if (CollectionUtil.isNotEmpty(submitOrderResps) && submitOrderResps.size() == 1) {
                PayOrderActivity.start(this, submitOrderResps.get(0).getId());
            } else {
                OrderListActivity.start(this, 1);
            }
            finish();
        });
    }

    @Override
    public void initDatas() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10000 && resultCode == RESULT_OK && data != null) {
            ShippingAddress address = (ShippingAddress) data.getSerializableExtra("address");
            if (address != null) {
                mAddressViewModel.selectAddress.setValue(address);
            }
        }
    }

    @Subscribe()
    public void onEvent(MyCouponBean couponBean) {
        mSelectCouponBean = couponBean;
        mBinding.tvDiscounts.setVisibility(View.VISIBLE);
        mBinding.tvDiscounts.setText(String.format("%s USDT", couponBean.getDiscount()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
