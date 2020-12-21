package com.darknet.bvw.mall.presenter;

import com.darknet.bvw.base.Presenter;
import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.mall.bean.CategoryBean;
import com.darknet.bvw.mall.ui.CategoryActivity;
import com.darknet.bvw.net.retrofit.ApiInterface;
import com.darknet.bvw.net.retrofit.BIWNetworkApi;
import com.darknet.bvw.net.retrofit.BaseObserver;
import com.darknet.bvw.net.retrofit.MvvmNetworkObserver;

import java.util.List;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/12/21 16:19
 * <br>desc: TODO
 */
public class CategoryPresenter extends Presenter<CategoryActivity> {

	public CategoryPresenter(CategoryActivity categoryActivity) {
		super(categoryActivity);
	}

	public void loadCategory(CategoryBean category) {
		if(CategoryBean.isHome(category)){
			loadFirstCategory();
		}else {
			loadSecondCategory(category);
		}

	}

	private void loadSecondCategory(CategoryBean category) {
		BIWNetworkApi.getService(ApiInterface.class)
				.categorySecond(category.getId())
				.compose(BIWNetworkApi.getInstance().applySchedulers(new BaseObserver<>(new MvvmNetworkObserver<BaseResponse<List<CategoryBean>>>() {
					@Override
					public void onSuccess(BaseResponse<List<CategoryBean>> response, boolean isFromCache) {
						mView.initViewPager(response);
					}

					@Override
					public void onFailure(Throwable throwable) {
					}
				}))).subscribe();
	}

	private void loadFirstCategory() {
		BIWNetworkApi.getService(ApiInterface.class)
				.category()
				.compose(BIWNetworkApi.getInstance().applySchedulers(new BaseObserver<>(new MvvmNetworkObserver<BaseResponse<List<CategoryBean>>>() {
					@Override
					public void onSuccess(BaseResponse<List<CategoryBean>> response, boolean isFromCache) {
						mView.initViewPager(response);
					}

					@Override
					public void onFailure(Throwable throwable) {
					}
				})));
	}
}