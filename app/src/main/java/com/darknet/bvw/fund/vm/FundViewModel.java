package com.darknet.bvw.fund.vm;

import android.app.Application;

import androidx.annotation.NonNull;

import com.darknet.bvw.base.BaseListBean;
import com.darknet.bvw.common.BaseListViewModel;
import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.fund.bean.DefiProduct;
import com.darknet.bvw.net.retrofit.BIWNetworkApi;
import com.darknet.bvw.net.retrofit.BaseObserver;
import com.darknet.bvw.net.retrofit.MvvmNetworkObserver;
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
public class FundViewModel extends BaseListViewModel<DefiProduct> {
    public FundViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void loadData(int pageNum, boolean isClear) {
        apiService.getDefiProductList(100,1)
                .compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<BaseListBean<DefiProduct>>>() {
                    @Override
                    public void onSuccess(BaseResponse<BaseListBean<DefiProduct>> t, boolean isFromCache) {
                        notifyResultToTopViewModel(BaseListBean.getItems(t.getData()));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        ToastUtils.showToast(throwable.getMessage());
                    }
                }));
    }
}
