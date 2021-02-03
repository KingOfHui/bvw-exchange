package com.darknet.bvw.fund.vm;

import android.app.Application;

import androidx.annotation.NonNull;

import com.darknet.bvw.common.BaseListViewModel;

public class PledgeRecordViewModel extends BaseListViewModel<String> {
    public PledgeRecordViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void loadData(int pageNum, boolean isClear) {

    }
}
