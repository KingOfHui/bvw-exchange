package com.darknet.bvw.fund.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.darknet.bvw.base.BaseListBean;
import com.darknet.bvw.common.BaseListViewModel;
import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.fund.bean.DefiTotalDataBySymbol;
import com.darknet.bvw.fund.bean.DefiWithdraw;
import com.darknet.bvw.net.retrofit.BIWNetworkApi;
import com.darknet.bvw.net.retrofit.BaseObserver;
import com.darknet.bvw.net.retrofit.MvvmNetworkObserver;
import com.darknet.bvw.util.ToastUtils;

public class PledgeDetailViewModel extends BaseListViewModel<DefiWithdraw> {

    public MutableLiveData<DefiTotalDataBySymbol> mDefiTotalDataBySymbolMutableLiveData = new MutableLiveData<>();
    public PledgeDetailViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void loadData(int pageNum, boolean isClear) {
        showLoading();
        apiService.getWithdrawList(20,pageNum)
                .compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<BaseListBean<DefiWithdraw>>>() {
                    @Override
                    public void onSuccess(BaseResponse<BaseListBean<DefiWithdraw>> t, boolean isFromCache) {
                        hideLoading();
                        notifyResultToTopViewModel(BaseListBean.getItems(t.getData()), 20);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        hideLoading();
                        ToastUtils.showToast(throwable.getMessage());
                    }
                }));
    }

    public void getTotalDataBySymbol() {
        showLoading();
        apiService.getTotalDataBySymbol()
                .compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<DefiTotalDataBySymbol>>() {
                    @Override
                    public void onSuccess(BaseResponse<DefiTotalDataBySymbol> t, boolean isFromCache) {
                        hideLoading();
                        mDefiTotalDataBySymbolMutableLiveData.setValue(t.getData());
                        refresh();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        hideLoading();
                        ToastUtils.showToast(throwable.getMessage());
                    }
                }));
    }
}
