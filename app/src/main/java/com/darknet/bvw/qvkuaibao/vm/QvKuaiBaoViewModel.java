package com.darknet.bvw.qvkuaibao.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.darknet.bvw.common.BaseListViewModel;
import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.net.retrofit.BIWNetworkApi;
import com.darknet.bvw.net.retrofit.BaseObserver;
import com.darknet.bvw.net.retrofit.MvvmNetworkObserver;
import com.darknet.bvw.qvkuaibao.bean.PosWallet;
import com.darknet.bvw.util.ToastUtils;

import java.util.List;

public class QvKuaiBaoViewModel extends BaseListViewModel<String> {

    public MutableLiveData<List<PosWallet>> posWalletListLive = new MutableLiveData<>();
    public QvKuaiBaoViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void loadData(int pageNum, boolean isClear) {
        apiService.getWalletList().compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<List<PosWallet>>>() {
                    @Override
                    public void onSuccess(BaseResponse<List<PosWallet>> t, boolean isFromCache) {
                        posWalletListLive.setValue(t.getData());
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        ToastUtils.showToast(throwable.getMessage());
                    }
                }));
    }
}
