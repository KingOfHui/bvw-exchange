package com.darknet.bvw.mall.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.darknet.bvw.base.BaseListBean;
import com.darknet.bvw.common.BaseListViewModel;
import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.mall.bean.CategoryBean;
import com.darknet.bvw.mall.bean.GoodsBannerBean;
import com.darknet.bvw.mall.bean.GoodsBean;
import com.darknet.bvw.mall.bean.GoodsDetailBean;
import com.darknet.bvw.mall.bean.ShopHomeBean;
import com.darknet.bvw.net.retrofit.ApiInterface;
import com.darknet.bvw.net.retrofit.BIWNetworkApi;
import com.darknet.bvw.net.retrofit.BaseObserver;
import com.darknet.bvw.net.retrofit.MvvmNetworkObserver;

import java.util.ArrayList;
import java.util.List;

public class GoodsListViewModel extends BaseListViewModel<Object> {

    private MutableLiveData<List<GoodsBannerBean>> mBanner = new MutableLiveData<>();
    private CategoryBean mCategory;

    public GoodsListViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<GoodsBannerBean>> getBanner() {
        return mBanner;
    }

    @Override
    protected void loadData(int pageNum, boolean isClear) {
        if(CategoryBean.isHome(mCategory)){
            loadHome();
        }else {
            loadByCategory(pageNum);
        }
    }

    private void loadByCategory(int page) {
        showLoading();
        BIWNetworkApi.getService(ApiInterface.class)
                .shopListByCategory(mCategory.getId(), null, 20, null, null, page)
                .compose(BIWNetworkApi.getInstance().applySchedulers())
                    .subscribe(new BaseObserver<>(new MvvmNetworkObserver<BaseResponse<BaseListBean<GoodsDetailBean>>>() {
                    @Override
                    public void onSuccess(BaseResponse<BaseListBean<GoodsDetailBean>> response, boolean isFromCache) {
                        List<GoodsDetailBean> list = BaseListBean.getItems(response.getData());
                        notifyResultToTopViewModel(list, 20);
                        mBanner.setValue(null);
                        hideLoading();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        loadFailed(throwable.getMessage());
                        hideLoading();
                    }
                }));
    }

    private void loadHome() {
        showLoading();
        BIWNetworkApi.getService(ApiInterface.class)
                .shopHome()
                .compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(new MvvmNetworkObserver<BaseResponse<ShopHomeBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<ShopHomeBean> response, boolean isFromCache) {
                        ShopHomeBean data = response.getData();
                        List<GoodsBannerBean> banners = data.getBanners();
                        List<GoodsBean> recommends = data.getRecommends();
                        notifyResultToTopViewModel(recommends, Integer.MAX_VALUE);
                        mBanner.setValue(banners);
                        hideLoading();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        loadFailed(throwable.getMessage());
                        hideLoading();
                    }
                }));
    }

    public void setCategory(CategoryBean category) {
        mCategory = category;
    }
}
