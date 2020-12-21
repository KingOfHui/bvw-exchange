package com.darknet.bvw.common;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.darknet.bvw.net.retrofit.ApiInterface;
import com.darknet.bvw.net.retrofit.BIWNetworkApi;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author : dinghui
 * @date : 2020/7/3 0:58
 */
public class BaseViewModel extends AndroidViewModel{


    protected CompositeDisposable mDisposableContainer;
    protected ApiInterface apiService;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        mDisposableContainer = new CompositeDisposable();
        apiService = BIWNetworkApi.getService(ApiInterface.class);
    }



    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposableContainer.dispose();
    }

    public void addDisposable(Disposable d) {
        mDisposableContainer.add(d);
    }
}
