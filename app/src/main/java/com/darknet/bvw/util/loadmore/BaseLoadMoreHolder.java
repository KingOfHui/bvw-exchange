package com.darknet.bvw.util.loadmore;

import androidx.annotation.NonNull;
import cn.hutool.core.thread.ThreadUtil;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.darknet.bvw.MyApp;
import com.darknet.bvw.util.refresh.BaseRefreshHolder;

import java.util.List;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/5/11 14:05
 * <br>desc: 通用加载更多
 */
public abstract class BaseLoadMoreHolder <T, RefreshLayout> implements LoadmoreListener.Emitter<T>{

	// 防止 page= MAX_VALUE时 page++ 变成负数导致崩溃发生
	private final int SAFE_OFFSET = 10000;

	protected RefreshLayout mLoadmoreLayout;
	protected BaseQuickAdapter<T, ? extends BaseViewHolder> mAdapter;
	protected LoadmoreListener<T> mListener;
	protected boolean loading;

	protected int page = 1;
	private int maxPage = Integer.MAX_VALUE - SAFE_OFFSET;

	public BaseLoadMoreHolder(RefreshLayout loadmoreLayout, BaseQuickAdapter<T, ? extends BaseViewHolder> adapter) {
		mLoadmoreLayout = loadmoreLayout;
		mAdapter = adapter;
	}

	public final void setLoadmoreListener(LoadmoreListener<T> listener){
		mListener = listener;
		bindLoadmoreListener((page, emitter) -> {
			if(loading){
				return;
			}
			loading = true;
			if(this.page > maxPage){
				disableLoadmore();
				return;
			}
			listener.onLoadMore(page, emitter);
		});
	}

	private void disableLoadmore(){
		AndroidSchedulers.mainThread().scheduleDirect(() -> {
			onLoadFinish(false);
			closeLoadMore();
			loading = false;
		});
	}

	/**
	 * 加载下一页的数据
	 */
	public final void loadNext(){
		if(loading){
			return;
		}
		loading = true;
		if(this.page > maxPage){
			disableLoadmore();
			return;
		}
		mListener.onLoadMore(page, this);
	}

	public void setLoading(){
		loading = true;
	}

	/**
	 * 下拉刷新时调用, 重置页码
	 */
	public final void reset(){
		page = 1;
		mAdapter.setNewData(null);
		openLoadMore();
	}

	public final void refreshed(){
		page = 2;
		if(page > maxPage){
			closeLoadMore();
		}else {
			openLoadMore();
		}
	}

	protected abstract void openLoadMore();

	protected abstract void closeLoadMore();

	protected abstract void bindLoadmoreListener(LoadmoreListener<T> listener);

	public abstract void onLoadFinish(boolean isSuccess);

	@Override
	public final void loadSuccessFinish(int page) {
		loadFinish(page, true);
	}

	@Override
	public void loadErrorFinish(int page) {
		loadFinish(page, false);
	}

	@Override
	public final void loadFinish(int page, boolean isSuccess) {
		loading = false;
		onLoadFinish(isSuccess);
		// 防止刷新后, 加载更多成功又吧page整乱
		if(page != this.page){
			return;
		}

		if(isSuccess) {
			this.page++;
		}
	}

	@Override
	public final void addData(List<T> datas){
		mAdapter.addData(datas);
	}

	@Override
	public final void setNewData(List<T> datas) {
		mAdapter.setNewData(datas);
	}

	@Override
	public final void addData(T data){
		mAdapter.addData(data);
	}

	@Override
	public final void setMaxPage(int maxPage) {
		if(maxPage >= Integer.MAX_VALUE - SAFE_OFFSET) {
			this.maxPage = Integer.MAX_VALUE - SAFE_OFFSET;
		}else {
			this.maxPage = maxPage;
		}

		if(this.page > maxPage){
			closeLoadMore();
		}
	}

	@Override
	public final void nomore() {
		page = maxPage + 1;
		closeLoadMore();
	}

	public final void bind(@NonNull BaseRefreshHolder<T, ?> refreshHolder) {
		refreshHolder.bind(this);
	}
}
