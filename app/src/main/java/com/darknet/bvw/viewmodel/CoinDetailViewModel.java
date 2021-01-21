package com.darknet.bvw.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.darknet.bvw.common.BaseListViewModel;
import com.darknet.bvw.order.bean.MyCouponBean;

public class CoinDetailViewModel extends BaseListViewModel<MyCouponBean> {
    public CoinDetailViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void loadData(int pageNum, boolean isClear) {

    }
}
