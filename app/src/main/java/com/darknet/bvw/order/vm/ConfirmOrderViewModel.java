package com.darknet.bvw.order.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.common.BaseViewModel;
import com.darknet.bvw.mall.bean.GoodsDetailBean;
import com.darknet.bvw.net.retrofit.BIWNetworkApi;
import com.darknet.bvw.net.retrofit.BaseObserver;
import com.darknet.bvw.net.retrofit.MvvmNetworkObserver;
import com.darknet.bvw.net.retrofit.RequestBodyBuilder;
import com.darknet.bvw.order.bean.CartData;
import com.darknet.bvw.order.bean.CouponBean;
import com.darknet.bvw.order.bean.MyCouponBean;
import com.darknet.bvw.order.bean.SubmitOrderReq;
import com.darknet.bvw.order.bean.OrderResp;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;

public class ConfirmOrderViewModel extends BaseViewModel {

    public MutableLiveData<List<OrderResp>> submitCartOrderLive = new MutableLiveData<>();
    public MutableLiveData<Boolean> submitOrderLive = new MutableLiveData<>();
    public MutableLiveData<OrderResp> mOrderDetailLiveData = new MutableLiveData<>();
    public MutableLiveData<List<CartData.CartItemListBean>> cartItemListLive = new MutableLiveData<>();

    public ConfirmOrderViewModel(@NonNull Application application) {
        super(application);
    }

    public void updateOrderAddress() {
        RequestBody params = new RequestBodyBuilder().addParams("address_id", 1)
                .addParams("id", 2).build();
        apiService.updateOrderAddress(params);
    }

    public void submitCartOrder(int addressId, String remark, MyCouponBean selectCouponBean, int productId) {
        showLoading();
        SubmitOrderReq req = new SubmitOrderReq();
        req.setAddress_id(addressId);
        req.setNote(remark);
        SubmitOrderReq.CouponBean couponBean = new SubmitOrderReq.CouponBean();
        if (selectCouponBean != null) {
            couponBean.setCoupon_id(selectCouponBean.getId());
            couponBean.setMall_id(productId);
        }
        req.setCoupon(couponBean);
        apiService.submitCart(new RequestBodyBuilder().build(req))
                .compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<List<OrderResp>>>() {
                    @Override
                    public void onSuccess(BaseResponse<List<OrderResp>> t, boolean isFromCache) {
                        List<OrderResp> data = t.getData();
                        submitCartOrderLive.setValue(data);
                        hideLoading();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        hideLoading();
                    }
                }));
    }

    public void submitOrder(int addressId, String remark, MyCouponBean selectCouponBean, int quantity, int productSkuId, int productId) {
        showLoading();
        SubmitOrderReq req = new SubmitOrderReq();
        req.setAddress_id(addressId);
        req.setNote(remark);
        req.setProduct_sku_id(productSkuId);
        req.setQuantity(quantity);
        SubmitOrderReq.CouponBean couponBean = new SubmitOrderReq.CouponBean();
        if (selectCouponBean != null) {
            couponBean.setCoupon_id(selectCouponBean.getId());
            couponBean.setMall_id(productId);
        }
        req.setCoupon(couponBean);
        apiService.submitOrder(new RequestBodyBuilder().build(req)).compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<List<OrderResp>>>() {
                    @Override
                    public void onSuccess(BaseResponse<List<OrderResp>> t, boolean isFromCache) {
                        List<OrderResp> data = t.getData();
                        submitCartOrderLive.setValue(data);
                        submitOrderLive.setValue(true);
                        hideLoading();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        hideLoading();
                    }
                }));
    }

    public void getCartList() {
        showLoading();
        apiService.getCartList().compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<CartData>>() {
                    @Override
                    public void onSuccess(BaseResponse<CartData> t, boolean isFromCache) {
                        CartData data = t.getData();
                        List<CartData.CartItemListBean> cart_item_list = data.getCart_item_list();
                        ArrayList<CartData.CartItemListBean> cartItemListBeans = new ArrayList<>();
                        for (CartData.CartItemListBean cartItemListBean : cart_item_list) {
                            if (cartItemListBean.getCheck() == 1) {
                                cartItemListBeans.add(cartItemListBean);
                            }
                        }
                        cartItemListLive.setValue(cartItemListBeans);
                        hideLoading();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        hideLoading();
                    }
                }));
    }
}
