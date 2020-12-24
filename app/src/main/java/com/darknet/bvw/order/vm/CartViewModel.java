package com.darknet.bvw.order.vm;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.darknet.bvw.common.BaseListViewModel;
import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.net.retrofit.BIWNetworkApi;
import com.darknet.bvw.net.retrofit.BaseObserver;
import com.darknet.bvw.net.retrofit.MvvmNetworkObserver;
import com.darknet.bvw.net.retrofit.RequestBodyBuilder;
import com.darknet.bvw.order.bean.CartData;

import java.util.List;

public class CartViewModel extends BaseListViewModel<CartData.CartItemListBean> {

    public MutableLiveData<Boolean> checkCartSuccessLive = new MutableLiveData<>();
    public MutableLiveData<List<CartData.CartItemListBean>> cartItemListLive = new MutableLiveData<>();

    public CartViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void loadData(int pageNum, boolean isClear) {
        showLoading();
        apiService.getCartList().compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<CartData>>() {
                    @Override
                    public void onSuccess(BaseResponse<CartData> t, boolean isFromCache) {
                        CartData data = t.getData();
                        List<CartData.CartItemListBean> cart_item_list = data.getCart_item_list();
                        notifyResultToTopViewModel(cart_item_list);
                        cartItemListLive.setValue(cart_item_list);
                        hideLoading();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        hideLoading();
                    }
                }));
    }

    public void checkCartByProduct(String idsUnSelect, String idsSelect) {
        showLoading();
        apiService.checkCartByProduct(new RequestBodyBuilder().addParams("check", 1).addParams("ids", idsSelect).build())
                .compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<Object>() {
                    @Override
                    public void onSuccess(Object t, boolean isFromCache) {
                        if (TextUtils.isEmpty(idsUnSelect)) {
                            checkCartSuccessLive.setValue(true);
                            hideLoading();
                        } else {
                            uncheckCartByProduct(idsUnSelect);
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        hideLoading();
                    }
                }));
    }

    public void uncheckCartByProduct(String idsUnSelect) {
        apiService.checkCartByProduct(new RequestBodyBuilder().addParams("check", 0).addParams("ids", idsUnSelect).build())
                .compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<Object>() {
                    @Override
                    public void onSuccess(Object t, boolean isFromCache) {
                        checkCartSuccessLive.setValue(true);
                        hideLoading();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        hideLoading();
                    }
                }));

    }

    public void addToCart(int skuId, int quantity) {
        showLoading();
        apiService.addToCart(new RequestBodyBuilder().addParams("product_sku_id", skuId).addParams("quantity", quantity).build())
                .compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<Object>>() {
                    @Override
                    public void onSuccess(BaseResponse<Object> t, boolean isFromCache) {
                        hideLoading();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        hideLoading();
                    }
                }));
    }

    public void subCartGoods(int skuId, int quantity) {
        showLoading();
        apiService.subCartGoods(new RequestBodyBuilder().addParams("product_sku_id", skuId).addParams("quantity", quantity).build())
                .compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<Object>>() {
                    @Override
                    public void onSuccess(BaseResponse<Object> t, boolean isFromCache) {
                        hideLoading();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        hideLoading();

                    }
                }));
    }
}
