package com.darknet.bvw.mall.vm;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;

import com.darknet.bvw.adapter.BaseViewHolder;
import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.common.BaseViewModel;
import com.darknet.bvw.mall.bean.CategoryBean;
import com.darknet.bvw.model.response.NoticeResponse;
import com.darknet.bvw.net.retrofit.ApiInterface;
import com.darknet.bvw.net.retrofit.BIWNetworkApi;
import com.darknet.bvw.net.retrofit.BaseObserver;
import com.darknet.bvw.net.retrofit.MvvmNetworkObserver;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;

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

    public void loadCategory(){
        BIWNetworkApi.getService(ApiInterface.class)
                .category()
                .compose(BIWNetworkApi.getInstance().applySchedulers(new BaseObserver<>(new MvvmNetworkObserver<BaseResponse<List<CategoryBean>>>() {
                    @Override
                    public void onSuccess(BaseResponse<List<CategoryBean>> response, boolean isFromCache) {
                        List<CategoryBean> list = response.getData();
                        list.add(0, CategoryBean.HOME);
                        category.setValue(list);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                    }
                })));
    }
}
