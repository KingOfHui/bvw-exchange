package com.darknet.bvw.mall.vm;

import android.app.Application;

import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.common.BaseViewModel;
import com.darknet.bvw.mall.bean.CategoryBean;
import com.darknet.bvw.net.retrofit.ApiInterface;
import com.darknet.bvw.net.retrofit.BIWNetworkApi;
import com.darknet.bvw.net.retrofit.BaseObserver;
import com.darknet.bvw.net.retrofit.MvvmNetworkObserver;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import java8.util.function.Function;

/**
 * @ClassName MallViewModel
 * @Description
 * @Author dinghui
 * @Date 2020/12/12 0012 15:54
 */
public class MallViewModel extends BaseViewModel {

    private MutableLiveData<List<CategoryBean>> category = new MutableLiveData<>();

    public MallViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<CategoryBean>> getCategory() {
        return category;
    }

    public void initSearchHint(Function<String, Void> callback){
        BIWNetworkApi.getService(ApiInterface.class)
                .hotKeyword()
                .compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(new MvvmNetworkObserver<BaseResponse<String>>() {
                    @Override
                    public void onSuccess(BaseResponse<String> response, boolean isFromCache) {
                        String data = response.getData();
                        String[] keywords = data.split(",");
                        if(keywords.length > 0) {
                            callback.apply(keywords[0]);
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {

                    }
                }));
    }

    public void loadCategory() {
        BIWNetworkApi.getService(ApiInterface.class)
                .category()
                .compose(BIWNetworkApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<>(new MvvmNetworkObserver<BaseResponse<List<CategoryBean>>>() {
                    @Override
                    public void onSuccess(BaseResponse<List<CategoryBean>> response, boolean isFromCache) {
                        List<CategoryBean> list = response.getData();
                        list.add(0, CategoryBean.HOME);
                        category.setValue(list);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                    }
                }));
    }
}
