package com.darknet.bvw.order.vm;

import android.app.Application;

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

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;

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
        Observable.zip(apiService.checkCartByProduct(new RequestBodyBuilder().addParams("check", 1).addParams("ids", idsSelect).build()),
                apiService.checkCartByProduct(new RequestBodyBuilder().addParams("check", 0).addParams("ids", idsUnSelect).build()),
                (objectBaseResponse, objectBaseResponse2) -> 1)
                .compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<Integer>() {
                    @Override
                    public void onSuccess(Integer t, boolean isFromCache) {
                        checkCartSuccessLive.setValue(true);
                        hideLoading();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        hideLoading();
                    }
                }));
    }

}
