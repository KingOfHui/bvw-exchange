package com.darknet.bvw.util.loadmore;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/5/28 10:21
 * <br>desc: 使用BaseQuickAdapter自带的加载更多
 */
public class QuickLoadmoreHolder<T> extends BaseLoadMoreHolder<T, BaseQuickAdapter>{

	// 加载完是否展示 没有更多数据 的文案
	private boolean nomoreTextIsShowing = true;

	public QuickLoadmoreHolder(BaseQuickAdapter loadmoreLayout, BaseQuickAdapter<T, ? extends BaseViewHolder> adapter) {
		super(loadmoreLayout, adapter);
	}

	/**
	 * 加载完是否展示 没有更多数据 的文案
	 */
	public void setNomoreTextIsShowing(boolean needShow) {
		this.nomoreTextIsShowing = needShow;
	}

	@Override
	protected void openLoadMore() {
		mLoadmoreLayout.setEnableLoadMore(true);
	}

	@Override
	protected void closeLoadMore() {
		mLoadmoreLayout.loadMoreEnd(!nomoreTextIsShowing);
	}

	@Override
	protected void bindLoadmoreListener(LoadmoreListener<T> listener) {
		mLoadmoreLayout.setOnLoadMoreListener(() -> listener.onLoadMore(page, QuickLoadmoreHolder.this));
	}

	@Override
	public void onLoadFinish(boolean isSuccess) {
		mLoadmoreLayout.loadMoreComplete();
	}
}
