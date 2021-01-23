package com.darknet.bvw.qvkuaibao.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.darknet.bvw.base.BaseListBean;
import com.darknet.bvw.common.BaseListViewModel;
import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.net.retrofit.BIWNetworkApi;
import com.darknet.bvw.net.retrofit.BaseObserver;
import com.darknet.bvw.net.retrofit.MvvmNetworkObserver;
import com.darknet.bvw.qvkuaibao.bean.PosBonus;
import com.darknet.bvw.qvkuaibao.bean.PosWalletData;
import com.darknet.bvw.util.ToastUtils;

import java.util.List;

public class PosCoinDetailViewModel extends BaseListViewModel<PosBonus> {

    public MutableLiveData<PosWalletData> mPosWalletDataMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> symbolLive = new MutableLiveData<>();
    public PosCoinDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void getWalletData(String symbol) {
        symbolLive.setValue(symbol);
        apiService.getWalletData(symbol).compose(getCompose())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<PosWalletData>>() {
                    @Override
                    public void onSuccess(BaseResponse<PosWalletData> t, boolean isFromCache) {
                        mPosWalletDataMutableLiveData.setValue(t.getData());
                        refresh();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        ToastUtils.showToast(throwable.getMessage());
                    }
                }));

    }

    @Override
    protected void loadData(int pageNum, boolean isClear) {
        apiService.getBonusList(symbolLive.getValue(), 20, pageNum)
                .compose(getCompose())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<BaseListBean<PosBonus>>>() {
                    @Override
                    public void onSuccess(BaseResponse<BaseListBean<PosBonus>> t, boolean isFromCache) {
                        notifyResultToTopViewModel(BaseListBean.getItems(t.getData()));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        ToastUtils.showToast(throwable.getMessage());
                    }
                }));
    }
}
