package com.darknet.bvw.order.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.darknet.bvw.R;
import com.darknet.bvw.activity.BaseBindingActivity;
import com.darknet.bvw.databinding.ActivityOrderConfirmBinding;
import com.darknet.bvw.mall.bean.GoodsDetailBean;
import com.darknet.bvw.order.bean.CartData;
import com.darknet.bvw.order.bean.MyCouponBean;
import com.darknet.bvw.order.bean.ShippingAddress;
import com.darknet.bvw.order.bean.event.CartEvent;
import com.darknet.bvw.order.ui.adapter.ConfirmGoodsAdapter;
import com.darknet.bvw.order.vm.ConfirmOrderViewModel;
import com.darknet.bvw.order.vm.MyAddressViewModel;
import com.darknet.bvw.util.ToastUtils;
import com.darknet.bvw.util.ValueUtil;

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
    private ConfirmGoodsAdapter mAdapter;
    private CartData.CartItemListBean mSelectCartItemListBean;

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

        mBinding.hlvDiscounts.setRightText(String.format("%s USDT", "0"));
        mBinding.hlvFreight.setRightText(String.format("%s USDT", "0"));
        mBinding.layoutTitle.layBack.setOnClickListener(view -> finish());
        mBinding.layoutTitle.title.setText(getString(R.string.confirm_order));
        mBinding.ivEdit.setOnClickListener(view -> AddAddressActivity.start(this, mAddress, false));

        Intent data = getIntent();
        GoodsDetailBean.SkuListBean selectSkuListBean = (GoodsDetailBean.SkuListBean) data.getSerializableExtra("selectSkuListBean");
        GoodsDetailBean goodsDetailBean = (GoodsDetailBean) data.getSerializableExtra("goodsDetailBean");
        mAddressViewModel = getViewModel(MyAddressViewModel.class);
        ConfirmOrderViewModel orderViewModel = getViewModel(ConfirmOrderViewModel.class);

        mAdapter = new ConfirmGoodsAdapter();
        initRvData(selectSkuListBean, goodsDetailBean, orderViewModel);
        mAdapter.setNewData(mCartItemListBeans);
        mBinding.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.tvSelectDiscounts) {
                List<CartData.CartItemListBean> list = mAdapter.getData();
                mSelectCartItemListBean = list.get(position);
                ArrayList<String> selectList = new ArrayList<>();
                for (CartData.CartItemListBean cartItemListBean : list) {
                    MyCouponBean selectCouponBean = cartItemListBean.getSelectCouponBean();
                    if (selectCouponBean != null) {
                        selectList.add(selectCouponBean.getTx_hash());
                    }
                }
                CouponListActivity.start(this, selectList);
            }
        });

        orderViewModel.cartItemListLive.observe(this, mAdapter::setNewData);
        orderViewModel.cartDataLive.observe(this, new Observer<CartData>() {
            @Override
            public void onChanged(CartData cartData) {
                mBinding.hlvTotalPrice.setRightText(ValueUtil.stripTrailingZeros(cartData.getChecked_product_amount()));
                mBinding.clDiscounts.setOnClickListener(view -> {
                    startActivity(new Intent(ConfirmOrderActivity.this, CouponListActivity.class));
                });
            }
        });
        mBinding.tvAddressTip.setOnClickListener(view -> {
            Intent intent = new Intent(this, MyAddressesActivity.class);
            intent.putExtra("selectId", mAddress != null ? mAddress.getId() : -1);
            startActivityForResult(intent, 10000);
        });
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
                orderViewModel.submitCartOrder(mAddress.getId(), remark, mAdapter.getData());
            } else {
                orderViewModel.submitOrder(mAddress.getId(), remark, mSelectCouponBean,
                        selectSkuListBean.getQuantity(), selectSkuListBean.getId(), selectSkuListBean.getProduct_id());
            }
        });
        orderViewModel.submitOrderLive.observe(this, submitOrderResps -> {
            if (CollectionUtil.isNotEmpty(submitOrderResps) && submitOrderResps.size() == 1) {
                PayOrderActivity.start(this, submitOrderResps.get(0).getId());
            } else {
                OrderListActivity.start(this, 1);
            }
            EventBus.getDefault().post(new CartEvent());
            finish();
        });
    }

    private void initRvData(GoodsDetailBean.SkuListBean selectSkuListBean, GoodsDetailBean goodsDetailBean, ConfirmOrderViewModel orderViewModel) {
        if (selectSkuListBean != null && goodsDetailBean != null) {
            mBinding.hlvTotalPrice.setRightText(String.format("%s USDT", goodsDetailBean.getPrice()));
//            float coupon_discount = goodsDetailBean.getCoupon_discount();
//            if (coupon_discount > 0) {
//                mBinding.tvDiscounts.setVisibility(View.VISIBLE);
//                mBinding.tvDiscounts.setText("请选择优惠券");
//                mBinding.clDiscounts.setOnClickListener(view -> {
//                    startActivity(new Intent(this, CouponListActivity.class));
//                });
//            } else {
//                mBinding.tvDiscounts.setVisibility(View.VISIBLE);
//                mBinding.tvDiscounts.setText(R.string.no_used_coupon);
//            }
            CartData.CartItemListBean cartItemListBean = new CartData.CartItemListBean();
            cartItemListBean.setProduct_name(goodsDetailBean.getName());
            cartItemListBean.setSp1(selectSkuListBean.getSp1());
            cartItemListBean.setPrice(new BigDecimal(selectSkuListBean.getPrice()));
            cartItemListBean.setProduct_img_url(goodsDetailBean.getImg_url());
            cartItemListBean.setOriginal_price(new BigDecimal(selectSkuListBean.getOriginal_price()));
            cartItemListBean.setQuantity(selectSkuListBean.getQuantity());
            cartItemListBean.setCoupon_discount(BigDecimal.valueOf(goodsDetailBean.getCoupon_discount()));
            mCartItemListBeans.add(cartItemListBean);
        } else {
            orderViewModel.getCartList();
        }
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
        if (mSelectCartItemListBean != null) {
            BigDecimal coupon_discount = mSelectCartItemListBean.getCoupon_discount();
            BigDecimal discount = couponBean.getDiscount();
            if (discount.compareTo(coupon_discount) < 0) {
                ToastUtils.showToast(String.format(getString(R.string.select_coupon_top), ValueUtil.stripTrailingZeros(coupon_discount)));
                return;
            }
            mSelectCartItemListBean.setSelectCouponBean(couponBean);
            mAdapter.notifyDataSetChanged();
            mBinding.hlvDiscounts.setRightText(String.format("%s USDT", ValueUtil.stripTrailingZeros(mAdapter.getCouponAmount())));
        }
        mSelectCouponBean = couponBean;
//        mBinding.tvDiscounts.setText(String.format("%s USDT", couponBean.getDiscount()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
