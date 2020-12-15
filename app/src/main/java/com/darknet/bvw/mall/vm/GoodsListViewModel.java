package com.darknet.bvw.mall.vm;

import android.app.Application;

import androidx.annotation.NonNull;

import com.darknet.bvw.common.BaseRefreshViewModel;

public class GoodsListViewModel extends BaseRefreshViewModel<Object> {
    public GoodsListViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void loadData(int pageNum, boolean isClear) {

    }

}
