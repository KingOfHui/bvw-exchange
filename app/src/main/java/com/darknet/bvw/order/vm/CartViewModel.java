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
import com.darknet.bvw.order.bean.event.CartEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class CartViewModel extends BaseListViewModel<CartData.CartItemListBean> {

    private Observable<BaseResponse<CartData>> getCartListObservable;
    public MutableLiveData<Boolean> checkCartSuccessLive = new MutableLiveData<>();
    public MutableLiveData<List<CartData.CartItemListBean>> cartItemListLive = new MutableLiveData<>();
    public MutableLiveData<CartData> cartDataLive = new MutableLiveData<>();
    private Disposable mDisposable;

    public CartViewModel(@NonNull Application application) {
        super(application);
    }

    private Observable<BaseResponse<CartData>> getCartListObservable() {
        if (getCartListObservable == null) {
            getCartListObservable = apiService.getCartList().compose(BIWNetworkApi.getInstance().applySchedulers());
        }
        return getCartListObservable;
    }

    @Override
    protected void loadData(int pageNum, boolean isClear) {
        Observable<BaseResponse<CartData>> cartListObservable = getCartListObservable();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        showLoading();
        cartListObservable.subscribe(new BaseObserver<BaseResponse<CartData>>(this, new MvvmNetworkObserver<BaseResponse<CartData>>() {
            @Override
            public void onSuccess(BaseResponse<CartData> t, boolean isFromCache) {
                CartData data = t.getData();
                List<CartData.CartItemListBean> cart_item_list = data.getCart_item_list();
                notifyResultToTopViewModel(cart_item_list);
                cartDataLive.setValue(data);
                cartItemListLive.setValue(cart_item_list);
                hideLoading();
            }

            @Override
            public void onFailure(Throwable throwable) {
                hideLoading();
            }
        }) {
            @Override
            public void onSubscribe(Disposable d) {
                super.onSubscribe(d);
                mDisposable = d;
            }
        });
    }

    public void addCartGoods(int skuId, int quantity) {
        showLoading();
        apiService.addToCart(new RequestBodyBuilder().addParams("product_sku_id", skuId).addParams("quantity", quantity).build())
                .compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<Object>>() {
                    @Override
                    public void onSuccess(BaseResponse<Object> t, boolean isFromCache) {
                        refresh();
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
                        refresh();
                        hideLoading();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        hideLoading();

                    }
                }));
    }

    public void deleteBySku(int ids) {
        showLoading();
        apiService.deleteBySku(new RequestBodyBuilder().addParams("ids", ids).build())
                .compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<Object>>() {
                    @Override
                    public void onSuccess(BaseResponse<Object> t, boolean isFromCache) {
                        EventBus.getDefault().post(new CartEvent());
                        refresh();
                        hideLoading();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        hideLoading();
                    }
                }));
    }

    public void checkCartBySku(int check, String ids) {
        showLoading();
        apiService.checkCartBySku(new RequestBodyBuilder().addParams("check", check).addParams("ids", ids).build())
                .compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<Object>() {
                    @Override
                    public void onSuccess(Object t, boolean isFromCache) {
                        refresh();
                        hideLoading();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        hideLoading();
                    }
                }));
    }

    public void checkCartByProduct(int check, String ids) {
        showLoading();
        apiService.checkCartByProduct(new RequestBodyBuilder().addParams("check", check).addParams("ids", ids).build())
                .compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<Object>() {
                    @Override
                    public void onSuccess(Object t, boolean isFromCache) {
                        refresh();
                        hideLoading();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        hideLoading();
                    }
                }));
    }
}
