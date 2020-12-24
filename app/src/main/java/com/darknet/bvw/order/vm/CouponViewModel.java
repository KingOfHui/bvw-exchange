package com.darknet.bvw.order.vm;

import android.app.Application;

import androidx.annotation.NonNull;

import com.darknet.bvw.base.BaseListBean;
import com.darknet.bvw.common.BaseListViewModel;
import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.net.retrofit.BIWNetworkApi;
import com.darknet.bvw.net.retrofit.BaseObserver;
import com.darknet.bvw.net.retrofit.MvvmNetworkObserver;
import com.darknet.bvw.order.bean.CouponBean;

import java.util.List;

/**
 * @ClassName CouponViewModel
 * @Description
 * @Author dinghui
 * @Date 2020/12/24 0024 17:08
 */
public class CouponViewModel extends BaseListViewModel<CouponBean> {
    public CouponViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void loadData(int pageNum, boolean isClear) {
        showLoading();
        apiService.getCouponList().compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<BaseListBean<CouponBean>>>() {
                    @Override
                    public void onSuccess(BaseResponse<BaseListBean<CouponBean>> t, boolean isFromCache) {
                        List<CouponBean> items = BaseListBean.getItems(t.getData());
                        notifyResultToTopViewModel(items);
                        hideLoading();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        hideLoading();
                    }
                }));
    }
}
