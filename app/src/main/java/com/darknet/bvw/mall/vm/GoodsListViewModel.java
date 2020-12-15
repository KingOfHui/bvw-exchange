package com.darknet.bvw.mall.vm;

import android.app.Application;

import androidx.annotation.NonNull;

import com.darknet.bvw.common.BaseRefreshViewModel;

import java.util.ArrayList;
import java.util.List;

public class GoodsListViewModel extends BaseRefreshViewModel<Object> {
    public GoodsListViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void loadData(int pageNum, boolean isClear) {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new Object());
        }
        notifyResultToTopViewModel(list, 20);
    }

}
