package com.darknet.bvw.order.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.common.BaseViewModel;
import com.darknet.bvw.net.retrofit.BIWNetworkApi;
import com.darknet.bvw.net.retrofit.BaseObserver;
import com.darknet.bvw.net.retrofit.MvvmNetworkObserver;
import com.darknet.bvw.net.retrofit.RequestBodyBuilder;
import com.darknet.bvw.order.bean.SubmitOrderReq;
import com.darknet.bvw.order.bean.SubmitOrderResp;
import com.darknet.bvw.order.ui.activity.OrderListActivity;

import java.util.List;

import cn.hutool.core.collection.CollectionUtil;
import okhttp3.RequestBody;

public class ConfirmOrderViewModel extends BaseViewModel {

    public MutableLiveData<List<SubmitOrderResp>> submitOrderLive = new MutableLiveData<>();

    public ConfirmOrderViewModel(@NonNull Application application) {
        super(application);
    }

    public void updateOrderAddress() {
        RequestBody params = new RequestBodyBuilder().addParams("address_id", 1)
                .addParams("id", 2).build();
        apiService.updateOrderAddress(params);
    }

    public void submitCartOrder() {
        SubmitOrderReq req = new SubmitOrderReq();
        req.setAddress_id(1);
//        req.setCoupon();
        req.setNote("");
        apiService.submitCart(new RequestBodyBuilder().build(req))
                .compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<List<SubmitOrderResp>>>() {
                    @Override
                    public void onSuccess(BaseResponse<List<SubmitOrderResp>> t, boolean isFromCache) {
                        List<SubmitOrderResp> data = t.getData();
                        submitOrderLive.setValue(data);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {

                    }
                }));
    }
}
