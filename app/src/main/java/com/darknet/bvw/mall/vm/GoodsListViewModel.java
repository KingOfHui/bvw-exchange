package com.darknet.bvw.mall.vm;

import android.app.Application;

import androidx.annotation.NonNull;

import com.darknet.bvw.common.BaseLoadMoreViewModel;
import com.darknet.bvw.common.BaseViewModel;

public class GoodsListViewModel extends BaseLoadMoreViewModel<Object> {
    public GoodsListViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void loadData(int pageNum, boolean isClear) {

    }

}
