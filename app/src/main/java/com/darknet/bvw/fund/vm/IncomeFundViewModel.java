package com.darknet.bvw.fund.vm;

import android.app.Application;

import androidx.annotation.NonNull;

import com.darknet.bvw.common.BaseListViewModel;

/**
 * <pre>
 *     author : dinghui
 *     e-mail : dinghui@bcbook.com
 *     time   : 2021/02/03
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class IncomeFundViewModel extends BaseListViewModel<String> {
    public IncomeFundViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void loadData(int pageNum, boolean isClear) {

    }
}
