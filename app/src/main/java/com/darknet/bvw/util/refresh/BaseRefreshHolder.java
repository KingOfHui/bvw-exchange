package com.darknet.bvw.util.refresh;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.darknet.bvw.util.loadmore.BaseLoadMoreHolder;

import androidx.annotation.NonNull;


import java.util.List;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/5/10 00:28
 * <br>desc: 下拉刷新通用逻辑处理
 */
public abstract class BaseRefreshHolder<T, RefreshLayout> implements RefreshListener.Emitter<T> {

	protected BaseLoadMoreHolder<T, ?> mLoadmoreHolder;
	protected RefreshLayout mRefreshLayout;
	protected BaseQuickAdapter<T, ? extends BaseViewHolder> mAdapter;
	protected RefreshListener<T> mListener;
	protected boolean refreshing;

	public BaseRefreshHolder(RefreshLayout refreshLayout, BaseQuickAdapter<T, ? extends BaseViewHolder> adapter) {
		mRefreshLayout = refreshLayout;
		mAdapter = adapter;
	}

	public BaseRefreshHolder(BaseLoadMoreHolder<T, RefreshLayout> loadmoreHolder, RefreshLayout refreshLayout, BaseQuickAdapter<T, ? extends BaseViewHolder> adapter) {
		mRefreshLayout = refreshLayout;
		mAdapter = adapter;
		mLoadmoreHolder = loadmoreHolder;
	}

	public final void bind(@NonNull BaseLoadMoreHolder<T, ?> loadmoreHolder) {
		mLoadmoreHolder = loadmoreHolder;
	}

	public final void setRefreshListener(RefreshListener<T> listener) {
		mListener = listener;
		bindRefreshListener(emitter -> {
			// NOTE: refreshing 是代码调用刷新时
			if (refreshing) {
				refreshFinish();
				return;
			}
			listener.onRefresh(emitter);
		});
	}

	protected abstract void bindRefreshListener(RefreshListener<T> listener);

	public final void refresh() {
		if (mListener == null) return;
		if (refreshing) return;
		refreshing = true;
		mListener.onRefresh(this);
	}

	@Override
	public final void refreshFinish() {
		refreshing = false;
		onRefreshFinish();
	}

	public abstract void onRefreshFinish();

	@Override
	public final void setNewData(List<T> datas) {
		if (mLoadmoreHolder != null) mLoadmoreHolder.refreshed();
		mAdapter.setNewData(datas);
	}

	@Override
	public void setMaxPage(int maxPage) {
		if (mLoadmoreHolder != null) {
			mLoadmoreHolder.setMaxPage(maxPage);
		}
	}
}
