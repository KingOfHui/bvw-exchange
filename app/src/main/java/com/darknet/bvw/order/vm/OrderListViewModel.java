package com.darknet.bvw.order.vm;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.darknet.bvw.common.BaseListViewModel;
import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.net.retrofit.ApiInterface;
import com.darknet.bvw.net.retrofit.BIWNetworkApi;
import com.darknet.bvw.net.retrofit.BaseObserver;
import com.darknet.bvw.net.retrofit.MvvmNetworkObserver;

/**
 * @ClassName OrderListViewModel
 * @Description
 * @Author dinghui
 * @Date 2020/12/12 0012 16:51
 */
public class OrderListViewModel extends BaseListViewModel<String> {
    public OrderListViewModel(@NonNull Application application) {
        super(application);
    }

    private MutableLiveData<Integer> tradeStateLive;

    public MutableLiveData<Integer> getTradeStateLive() {
        if (tradeStateLive == null) {
            tradeStateLive = new MutableLiveData<>();
            tradeStateLive.setValue(-1);
        }
        return tradeStateLive;
    }

    @Override
    @SuppressLint("CheckResult")
    protected void loadData(int pageNum, boolean isClear) {
        BIWNetworkApi.getService(ApiInterface.class).getOrderList(getTradeStateLive().getValue(), pageNum, 20)
                .compose(BIWNetworkApi.getInstance()
                        .applySchedulers(new BaseObserver<>(this,
                                new MvvmNetworkObserver<BaseResponse<Object>>() {
                                    @Override
                                    public void onSuccess(BaseResponse<Object> t, boolean isFromCache) {
                                        Log.i("dhdhdh", "onSuccess: " + t.toString());
                                    }

                                    @Override
                                    public void onFailure(Throwable throwable) {
                                        loadFailed(throwable.getMessage());
                                    }
                                }
                        )));

        notifyResultToTopViewModel(null, 20);
    }
}
