package com.darknet.bvw.mall.vm;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;

import com.darknet.bvw.adapter.BaseViewHolder;
import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.common.BaseViewModel;
import com.darknet.bvw.model.response.NoticeResponse;
import com.darknet.bvw.net.retrofit.ApiInterface;
import com.darknet.bvw.net.retrofit.BIWNetworkApi;
import com.darknet.bvw.net.retrofit.BaseObserver;
import com.darknet.bvw.net.retrofit.MvvmNetworkObserver;

import io.reactivex.Observable;

/**
 * @ClassName MallViewModel
 * @Description
 * @Author dinghui
 * @Date 2020/12/12 0012 15:54
 */
public class MallViewModel extends BaseViewModel {
    public MallViewModel(@NonNull Application application) {
        super(application);
    }

    @SuppressLint("CheckResult")
    public void getOrders() {
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
