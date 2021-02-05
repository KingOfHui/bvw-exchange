package com.darknet.bvw.fund.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.darknet.bvw.R;
import com.darknet.bvw.base.BaseListBean;
import com.darknet.bvw.common.BaseListViewModel;
import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.fund.bean.DefiInvest;
import com.darknet.bvw.net.retrofit.BIWNetworkApi;
import com.darknet.bvw.net.retrofit.BaseObserver;
import com.darknet.bvw.net.retrofit.MvvmNetworkObserver;
import com.darknet.bvw.net.retrofit.RequestBodyBuilder;
import com.darknet.bvw.util.ToastUtils;

public class PledgeRecordViewModel extends BaseListViewModel<DefiInvest> {
    public MutableLiveData<String> symbolLive = new MutableLiveData<>();

    public PledgeRecordViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void loadData(int pageNum, boolean isClear) {
        showLoading();
        apiService.getDefiInvestList(20, pageNum)
                .compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<BaseListBean<DefiInvest>>>() {
                    @Override
                    public void onSuccess(BaseResponse<BaseListBean<DefiInvest>> t, boolean isFromCache) {
                        notifyResultToTopViewModel(BaseListBean.getItems(t.getData()));
                        hideLoading();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        hideLoading();
                        ToastUtils.showToast(throwable.getMessage());
                    }
                }));
    }

    public void cancelPledge(int id) {
        apiService.investRelease(new RequestBodyBuilder()
                .addParams("id", id)
                .build()).compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<Object>>() {
                    @Override
                    public void onSuccess(BaseResponse<Object> t, boolean isFromCache) {
                        ToastUtils.showToast(getApplication().getString(R.string.cancel_pledge_success));
                        refresh();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        ToastUtils.showToast(throwable.getMessage());
                    }
                }));
    }
}
