package com.darknet.bvw.util.loadmore;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/5/11 14:32
 * <br>desc: 下拉刷新使用
 */
public class SmartLoadmoreHolder<T> extends BaseLoadMoreHolder<T, SmartRefreshLayout>{

	public SmartLoadmoreHolder(SmartRefreshLayout loadmoreLayout, BaseQuickAdapter<T, ? extends BaseViewHolder> adapter) {
		super(loadmoreLayout, adapter);
	}

	@Override
	protected void openLoadMore() {
		mLoadmoreLayout.setEnableLoadMore(true);
	}

	@Override
	protected void closeLoadMore() {
		mLoadmoreLayout.setEnableLoadMore(false);
	}

	@Override
	protected void bindLoadmoreListener(LoadmoreListener<T> listener) {
		mLoadmoreLayout.setOnLoadMoreListener(refreshLayout -> listener.onLoadMore(page, SmartLoadmoreHolder.this));
	}

	@Override
	public void onLoadFinish(boolean isSuccess) {
		mLoadmoreLayout.finishLoadMore(isSuccess);
	}
}
