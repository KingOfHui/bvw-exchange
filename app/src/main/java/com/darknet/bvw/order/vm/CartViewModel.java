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

public class CartViewModel extends BaseListViewModel<CartData.CartItemListBean> {

    public MutableLiveData<Boolean> checkCartSuccessLive = new MutableLiveData<>();
    public MutableLiveData<Boolean> refreshCartSuccessLive = new MutableLiveData<>();

    public CartViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void loadData(int pageNum, boolean isClear) {
        apiService.getCartList().compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<CartData>>() {
                    @Override
                    public void onSuccess(BaseResponse<CartData> t, boolean isFromCache) {
                        CartData data = t.getData();
                        notifyResultToTopViewModel(data.getCart_item_list());
                        refreshCartSuccessLive.setValue(true);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {

                    }
                }));
    }

    public void checkCartByProduct(int check, String ids) {
        apiService.checkCartByProduct(new RequestBodyBuilder().addParams("check", check).addParams("ids", ids).build())
                .compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<Object>>() {
                    @Override
                    public void onSuccess(BaseResponse<Object> t, boolean isFromCache) {
                        checkCartSuccessLive.setValue(true);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {

                    }
                }));
    }

}
