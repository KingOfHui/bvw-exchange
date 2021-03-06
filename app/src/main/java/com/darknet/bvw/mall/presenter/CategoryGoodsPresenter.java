package com.darknet.bvw.mall.presenter;

import com.darknet.bvw.base.BaseListBean;
import com.darknet.bvw.base.Presenter;
import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.mall.bean.CategoryBean;
import com.darknet.bvw.mall.bean.GoodsDetailBean;
import com.darknet.bvw.mall.ui.CategoryGoodsFragment;
import com.darknet.bvw.net.retrofit.ApiInterface;
import com.darknet.bvw.net.retrofit.BIWNetworkApi;
import com.darknet.bvw.net.retrofit.BaseObserver;
import com.darknet.bvw.net.retrofit.MvvmNetworkObserver;

import java.util.List;
import java.util.OptionalDouble;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/12/21 16:24
 * <br>desc: TODO
 */
public class CategoryGoodsPresenter extends Presenter<CategoryGoodsFragment> {

	private String mOrder;
	private String mSort = "asc";
	private int mFirstCategoryId;
	private Integer mSecondCategoryId;

	public CategoryGoodsPresenter(CategoryGoodsFragment categoryGoodsFragment) {
		super(categoryGoodsFragment);
	}

	public void loadById() {
		mView.showLoading();
		BIWNetworkApi.getService(ApiInterface.class)
				.shopListByCategory(mFirstCategoryId, mSecondCategoryId, 1000, mOrder, equals(mOrder, "price") ? mSort:null, 1)
				.compose(BIWNetworkApi.getInstance().applySchedulers())
				.subscribe(new BaseObserver<>(new MvvmNetworkObserver<BaseResponse<BaseListBean<GoodsDetailBean>>>() {
					@Override
					public void onSuccess(BaseResponse<BaseListBean<GoodsDetailBean>> response, boolean isFromCache) {
						mView.refreshUI(BaseListBean.getItems(response.getData()));
						mView.dismissDialog();
					}

					@Override
					public void onFailure(Throwable throwable) {
						mView.dismissDialog();
					}
				}));
	}

	public void updateSortRule(String order, String sort) {
		if(equals(mOrder, "price")){
			mSort = sort;
		} else if(equals(order, mOrder)){
			return;
		}
		mOrder = order;

		loadById();
	}

	public String getSort() {
		return mSort;
	}

	private boolean equals(String s1, String s2){
		if(s1 == null) return s2 == null;
		if(s2 == null) return false;
		return s1.equals(s2);
	}

	public void setCategoryId(int firstCategoryId, Integer secondCategoryId) {
		mFirstCategoryId = firstCategoryId;
		mSecondCategoryId = secondCategoryId;
	}
}
