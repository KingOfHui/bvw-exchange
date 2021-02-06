package com.darknet.bvw.fund.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.darknet.bvw.base.BaseListBean;
import com.darknet.bvw.common.BaseListViewModel;
import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.fund.bean.DefiBonus;
import com.darknet.bvw.net.retrofit.BIWNetworkApi;
import com.darknet.bvw.net.retrofit.BaseObserver;
import com.darknet.bvw.net.retrofit.MvvmNetworkObserver;
import com.darknet.bvw.qvkuaibao.bean.PosBonus;
import com.darknet.bvw.util.ToastUtils;

/**
 * <pre>
 *     author : dinghui
 *     e-mail : dinghui@bcbook.com
 *     time   : 2021/02/03
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class IncomeFundViewModel extends BaseListViewModel<DefiBonus> {
    public MutableLiveData<String> symbolLive = new MutableLiveData<>();
    public IncomeFundViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void loadData(int pageNum, boolean isClear) {
        showLoading();
        apiService.getDefiBonusList(symbolLive.getValue(), 20, pageNum)
                .compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<BaseListBean<DefiBonus>>>() {
                    @Override
                    public void onSuccess(BaseResponse<BaseListBean<DefiBonus>> t, boolean isFromCache) {
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
}
