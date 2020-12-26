package com.darknet.bvw.mall.vm;

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
import com.darknet.bvw.order.bean.event.CartEvent;

import org.greenrobot.eventbus.EventBus;

public class GoodsDetailViewModel extends BaseViewModel {
    public MutableLiveData<GoodsDetailBean> productDetailLive = new MutableLiveData<>();
    public MutableLiveData<Boolean> isAddSuccessLive = new MutableLiveData<>();
    public GoodsDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void getGoodsDetail(int productId) {
        apiService.getProductDetail(productId).compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<GoodsDetailBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<GoodsDetailBean> t, boolean isFromCache) {
                        productDetailLive.setValue(t.getData());
                    }

                    @Override
                    public void onFailure(Throwable throwable) {

                    }
                }));

    }

    public void addToCart(int skuId, int quantity) {

        apiService.addToCart(new RequestBodyBuilder().addParams("product_sku_id",skuId).addParams("quantity",quantity).build())
                .compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<Object>>() {
                    @Override
                    public void onSuccess(BaseResponse<Object> t, boolean isFromCache) {
                        isAddSuccessLive.setValue(true);
                        EventBus.getDefault().post(new CartEvent());
                    }

                    @Override
                    public void onFailure(Throwable throwable) {

                    }
                }));
    }
}
