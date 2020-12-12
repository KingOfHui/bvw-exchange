package com.darknet.bvw.order.vm;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;

import com.darknet.bvw.common.BaseLoadMoreViewModel;
import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.common.BaseViewModel;
import com.darknet.bvw.model.response.NoticeResponse;
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
public class OrderListViewModel extends BaseLoadMoreViewModel {
    public OrderListViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    @SuppressLint("CheckResult")
    protected void loadData(int pageNum, boolean isClear) {
        //        Observable<BaseResponse<NoticeResponse.NoticeData>> observable =
        BIWNetworkApi.getService(ApiInterface.class).getTopic1()
                .compose(BIWNetworkApi.getInstance()
                        .applySchedulers(new BaseObserver<>(this,
                                new MvvmNetworkObserver<BaseResponse<NoticeResponse.NoticeData>>() {
                                    @Override
                                    public void onSuccess(BaseResponse<NoticeResponse.NoticeData> t, boolean isFromCache) {
                                    }

                                    @Override
                                    public void onFailure(Throwable throwable) {

                                    }
                                }
                        )));
    }
}
