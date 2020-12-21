package com.darknet.bvw.mall.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.darknet.bvw.common.BaseListViewModel;
import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.mall.bean.GoodsBannerBean;
import com.darknet.bvw.mall.bean.GoodsBean;
import com.darknet.bvw.mall.bean.ShopHomeBean;
import com.darknet.bvw.net.retrofit.ApiInterface;
import com.darknet.bvw.net.retrofit.BIWNetworkApi;
import com.darknet.bvw.net.retrofit.BaseObserver;
import com.darknet.bvw.net.retrofit.MvvmNetworkObserver;

import java.util.ArrayList;
import java.util.List;

public class GoodsListViewModel extends BaseListViewModel<GoodsBean> {

    private MutableLiveData<List<GoodsBannerBean>> mBanner = new MutableLiveData<>();
    public GoodsListViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<GoodsBannerBean>> getBanner() {
        return mBanner;
    }

    @Override
    protected void loadData(int pageNum, boolean isClear) {
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

}
