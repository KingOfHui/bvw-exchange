package com.darknet.bvw.order.vm;

import android.app.Application;

import androidx.annotation.NonNull;

import com.darknet.bvw.common.BaseListViewModel;
import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.net.retrofit.BIWNetworkApi;
import com.darknet.bvw.net.retrofit.BaseObserver;
import com.darknet.bvw.net.retrofit.MvvmNetworkObserver;
import com.darknet.bvw.order.bean.CartData;

import java.util.List;

public class CartViewModel extends BaseListViewModel<CartData.CartItemListBean> {

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
                        List<CartData.CartItemListBean> cart_item_list = data.getCart_item_list();
                        notifyResultToTopViewModel(cart_item_list);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {

                    }
                }));
    }

}
