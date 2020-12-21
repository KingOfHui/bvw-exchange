package com.darknet.bvw.mall.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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
        BIWNetworkApi.getService(ApiInterface.class)
                .shopListByCategory(mCategory.getId(), null, 20, null, null, page)
                .compose(BIWNetworkApi.getInstance().applySchedulers(
                        new BaseObserver<>(new MvvmNetworkObserver<BaseResponse<List<GoodsDetailBean>>>() {
                    @Override
                    public void onSuccess(BaseResponse<List<GoodsDetailBean>> response, boolean isFromCache) {
                        List<GoodsDetailBean> list = response.getData();
                        notifyResultToTopViewModel(list, 20);
                        mBanner.setValue(null);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        loadFailed(throwable.getMessage());
                    }
                }))).subscribe();
    }

    private void loadHome() {
        BIWNetworkApi.getService(ApiInterface.class)
                .shopHome()
                .compose(BIWNetworkApi.getInstance().applySchedulers(new BaseObserver<>(new MvvmNetworkObserver<BaseResponse<ShopHomeBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<ShopHomeBean> response, boolean isFromCache) {
                        ShopHomeBean data = response.getData();
                        List<GoodsBannerBean> banners = data.getBanners();
                        List<GoodsBean> recommends = data.getRecommends();
                        notifyResultToTopViewModel(recommends, 0);
                        mBanner.setValue(banners);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        loadFailed(throwable.getMessage());
                    }
                }))).subscribe();
    }

    public void setCategory(CategoryBean category) {
        mCategory = category;
    }
}
