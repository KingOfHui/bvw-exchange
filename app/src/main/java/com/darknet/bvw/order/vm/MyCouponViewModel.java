package com.darknet.bvw.order.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.darknet.bvw.base.BaseListBean;
import com.darknet.bvw.common.BaseListViewModel;
import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.net.retrofit.BIWNetworkApi;
import com.darknet.bvw.net.retrofit.BaseObserver;
import com.darknet.bvw.net.retrofit.MvvmNetworkObserver;
import com.darknet.bvw.order.bean.CouponBean;
import com.darknet.bvw.order.bean.MyCouponBean;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.collection.CollectionUtil;

/**
 * @ClassName CouponViewModel
 * @Description
 * @Author dinghui
 * @Date 2020/12/24 0024 17:08
 */
public class MyCouponViewModel extends BaseListViewModel<MyCouponBean> {

    public MutableLiveData<List<String>> selectCouponList = new MutableLiveData<>();
    public MyCouponViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void loadData(int pageNum, boolean isClear) {
        showLoading();
        apiService.getMyCouponList().compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(this, new MvvmNetworkObserver<BaseResponse<BaseListBean<MyCouponBean>>>() {
                    @Override
                    public void onSuccess(BaseResponse<BaseListBean<MyCouponBean>> t, boolean isFromCache) {
                        List<MyCouponBean> items = BaseListBean.getItems(t.getData());
                        List<String> list = selectCouponList.getValue();
                        ArrayList<MyCouponBean> myCouponBeans = new ArrayList<>();
                        if (CollectionUtil.isNotEmpty(list)) {
                            for (MyCouponBean item : items) {
                                boolean contains = list.contains(item.getTx_hash());
                                if (!contains) {
                                    myCouponBeans.add(item);
                                }
                            }
                            notifyResultToTopViewModel(myCouponBeans);
                        } else {
                            notifyResultToTopViewModel(items);
                        }
                        hideLoading();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        hideLoading();
                    }
                }));
    }
}
