package com.darknet.bvw.util.refresh;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/5/10 00:29
 * <br>desc: V4包下 下拉刷新包装器
 */
public class SwipeV4RefreshHolder<T> extends BaseRefreshHolder<T, SwipeRefreshLayout> {

	public SwipeV4RefreshHolder(SwipeRefreshLayout swipeRefreshLayout, BaseQuickAdapter<T, ? extends BaseViewHolder> adapter) {
		super(swipeRefreshLayout, adapter);
	}

	@Override
	public void bindRefreshListener(RefreshListener<T> listener) {
		mRefreshLayout.setOnRefreshListener(() -> listener.onRefresh(SwipeV4RefreshHolder.this));
	}

	@Override
	public void onRefreshFinish() {
		mRefreshLayout.setRefreshing(false);
	}
}
