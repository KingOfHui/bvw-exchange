package com.darknet.bvw.common;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName BaseRefreshViewModel
 * @Description 实现加载更多基类ViewModel
 * @Author dinghui
 * @Date 2020/11/25 0025 15:41
 */
public abstract class BaseListViewModel<DATA> extends BaseViewModel {

    private final int INIT_PAGE_NUM = 1;
    private int mPageNum = INIT_PAGE_NUM;
    private boolean mIsClear = true;
    protected MutableLiveData<Boolean> refreshing = new MutableLiveData<>(); //是否正在刷新
    protected MutableLiveData<Boolean> hasMore = new MutableLiveData<>(); //是否有更多数据

    private MutableLiveData<List<DATA>> mListLive = new MutableLiveData<>(); //列表数据

    public BaseListViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<Boolean> getRefreshing() {
        if (refreshing.getValue() == null) {
            refreshing.setValue(false);
        }
        return refreshing;
    }

    public LiveData<Boolean> getHasMore() {
        if (hasMore.getValue() == null) {
            hasMore.setValue(true);
        }
        return hasMore;
    }

    public LiveData<List<DATA>> getListLive() {
        if (mListLive.getValue() == null) {
            mListLive.setValue(new ArrayList<>());
        }
        return mListLive;
    }

    public void refresh() {
        refreshing.setValue(true);
        mPageNum = INIT_PAGE_NUM;
        mIsClear = true;
        loadData(1, true);
    }

    public void tryToLoadNext() {
        refreshing.setValue(true);
        mIsClear = false;
        loadData(mPageNum, false);
    }

    protected void notifyResultToTopViewModel(List<? extends DATA> dataList, int size) {
        List<DATA> list = getListLive().getValue();
        if (list == null) {
            return;
        }
        if (mIsClear) {
            list.clear();
        }
        if (dataList != null && !dataList.isEmpty()) {
            mPageNum ++;
            hasMore.setValue(dataList.size() >= size);
            list.addAll(dataList);
            mListLive.setValue(list);
        }
        refreshing.setValue(false);
    }

    protected void loadFailed(String errorMessage) {
        refreshing.setValue(false);
    }

    protected abstract void loadData(int pageNum, boolean isClear);
}
