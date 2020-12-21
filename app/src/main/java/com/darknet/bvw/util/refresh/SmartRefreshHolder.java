package com.darknet.bvw.util.refresh;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.darknet.bvw.util.loadmore.BaseLoadMoreHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/5/10 21:54
 * <br>desc: 封装 SmartRefreshLayout 相关的刷新逻辑
 */
public class SmartRefreshHolder<T> extends BaseRefreshHolder<T, SmartRefreshLayout> {

	public SmartRefreshHolder(SmartRefreshLayout smartRefreshLayout, BaseQuickAdapter<T, ? extends BaseViewHolder> adapter) {
		super(smartRefreshLayout, adapter);
	}

	public SmartRefreshHolder(BaseLoadMoreHolder<T, SmartRefreshLayout> loadmoreHolder
			, SmartRefreshLayout smartRefreshLayout, BaseQuickAdapter<T, ? extends BaseViewHolder> adapter) {
		super(loadmoreHolder, smartRefreshLayout, adapter);
	}

	@Override
	protected void bindRefreshListener(RefreshListener<T> listener) {
		mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh(@NonNull RefreshLayout refreshLayout) {
				listener.onRefresh(SmartRefreshHolder.this);
			}
		});
	}

	@Override
	public void onRefreshFinish() {
		mRefreshLayout.finishRefresh();
	}
}
