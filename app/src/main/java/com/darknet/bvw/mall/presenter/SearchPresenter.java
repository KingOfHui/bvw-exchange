package com.darknet.bvw.mall.presenter;

import com.darknet.bvw.base.BaseListBean;
import com.darknet.bvw.base.Presenter;
import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.mall.bean.GoodsDetailBean;
import com.darknet.bvw.mall.ui.search.SearchResultFragment;
import com.darknet.bvw.net.retrofit.ApiInterface;
import com.darknet.bvw.net.retrofit.BIWNetworkApi;
import com.darknet.bvw.net.retrofit.BaseObserver;
import com.darknet.bvw.net.retrofit.MvvmNetworkObserver;
import com.darknet.bvw.util.loadmore.LoadmoreListener;
import com.darknet.bvw.util.refresh.RefreshListener;

/**
 * Created by guoshiwen on 2020/12/23.
 */
public class SearchPresenter extends Presenter<SearchResultFragment> implements RefreshListener<GoodsDetailBean>, LoadmoreListener<GoodsDetailBean> {

	private String keyword;
	public SearchPresenter(SearchResultFragment fragment) {
		super(fragment);
	}

	public void search(String text) {
		keyword = text;
		mView.getRefreshHolder().refresh();
	}

	@Override
	public void onLoadMore(int page, LoadmoreListener.Emitter<GoodsDetailBean> emitter) {
		BIWNetworkApi.getService(ApiInterface.class)
				.search(keyword, 20, page)
				.compose(BIWNetworkApi.getInstance().applySchedulers())
				.subscribe(new BaseObserver<>(new MvvmNetworkObserver<BaseResponse<BaseListBean<GoodsDetailBean>>>() {
					@Override
					public void onSuccess(BaseResponse<BaseListBean<GoodsDetailBean>> response, boolean isFromCache) {
						emitter.loadSuccessFinish(page);
						emitter.setMaxPage(response.getData().getTotalPage());
						emitter.addData(BaseListBean.getItems(response.getData()));
					}

					@Override
					public void onFailure(Throwable throwable) {
						emitter.loadErrorFinish(page);
					}
				}));
	}

	@Override
	public void onRefresh(RefreshListener.Emitter<GoodsDetailBean> emitter) {
		mView.showLoading();
		BIWNetworkApi.getService(ApiInterface.class)
				.search(keyword, 20, 1)
				.compose(BIWNetworkApi.getInstance().applySchedulers())
				.subscribe(new BaseObserver<>(new MvvmNetworkObserver<BaseResponse<BaseListBean<GoodsDetailBean>>>() {
					@Override
					public void onSuccess(BaseResponse<BaseListBean<GoodsDetailBean>> response, boolean isFromCache) {
						emitter.refreshFinish();
						emitter.setMaxPage(response.getData().getTotalPage());
						emitter.setNewData(BaseListBean.getItems(response.getData()));
						mView.hideLoading();
					}

					@Override
					public void onFailure(Throwable throwable) {
						emitter.refreshFinish();
						mView.hideLoading();
					}
				}));
	}
}