package com.darknet.bvw.util.binding;

import androidx.databinding.BindingAdapter;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * @ClassName RefreshLayoutBindingAdapter
 * @Description 下拉刷新扩展属性
 * @Author dinghui
 * @Date 2020/12/22 0025 16:25
 */
public class RefreshLayoutBindingAdapter {

    @BindingAdapter(value = {"refreshing", "hasMore"}, requireAll = false)
    public static void bindSmartRefreshLayout(SmartRefreshLayout smartRefreshLayout, boolean refreshing, boolean hasMore) {
        if (!refreshing) {
            smartRefreshLayout.finishRefresh();
            smartRefreshLayout.finishLoadMore();
        }

        if (!hasMore) {
            smartRefreshLayout.setNoMoreData(true);
        }
    }

    @BindingAdapter(value = {"refreshEnable"})
    public static void bindSmartRefreshLayoutRefresh(SmartRefreshLayout smartLayout, boolean refreshEnable) {
        smartLayout.setEnableRefresh(refreshEnable);
    }

    @BindingAdapter(value = {"loadMoreEnable"})
    public static void bindSmartRefreshLayoutLoadMore(SmartRefreshLayout smartLayout, boolean loadMoreEnable) {
        smartLayout.setEnableLoadMore(loadMoreEnable);
    }

    @BindingAdapter(value = {"onRefreshListener", "onLoadMoreListener"}, requireAll = false)
    public static void bindListener(SmartRefreshLayout smartLayout, OnRefreshListener refreshListener,
                                    OnLoadMoreListener loadMoreListener) {
        smartLayout.setOnRefreshListener(refreshListener);
        smartLayout.setOnLoadMoreListener(loadMoreListener);
    }

    @BindingAdapter(value = {"autoRefresh"})
    public static void bindSmartRefreshLayout(SmartRefreshLayout smartLayout, Boolean autoRefresh) {
        if (autoRefresh) smartLayout.autoRefresh();
    }
}

