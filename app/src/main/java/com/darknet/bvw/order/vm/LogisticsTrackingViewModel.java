package com.darknet.bvw.order.vm;

import android.app.Application;

import androidx.annotation.NonNull;

import com.darknet.bvw.common.BaseListViewModel;

import java.util.ArrayList;

public class LogisticsTrackingViewModel extends BaseListViewModel<String> {
    public LogisticsTrackingViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void loadData(int pageNum, boolean isClear) {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            strings.add("ffjdks" + i);
        }
        notifyResultToTopViewModel(strings,20);
    }
}
