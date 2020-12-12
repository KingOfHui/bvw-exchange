package com.darknet.bvw.net.retrofit;


import com.darknet.bvw.common.BaseViewModel;
import com.darknet.bvw.net.retrofit.errorhandler.ExceptionHandlerUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class BaseObserver<T> implements Observer<T> {

    BaseViewModel mViewModel;
    MvvmNetworkObserver<T> mvvmNetworkObserver;

    public BaseObserver(MvvmNetworkObserver<T> mvvmNetworkObserver) {
        this(null, mvvmNetworkObserver);
    }

    public BaseObserver(BaseViewModel viewModel, MvvmNetworkObserver<T> mvvmNetworkObserver) {
        mViewModel = viewModel;
        this.mvvmNetworkObserver = mvvmNetworkObserver;
    }

    @Override
    public void onSubscribe(Disposable d) {

        if (mViewModel != null) {
            mViewModel.addDisposable(d);
        }
    }

    @Override
    public void onNext(T t) {
        mvvmNetworkObserver.onSuccess(t, false);
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ExceptionHandlerUtil.ResponseThrowable) {
            mvvmNetworkObserver.onFailure(e);
        } else {
            mvvmNetworkObserver.onFailure(new ExceptionHandlerUtil.ResponseThrowable(e, ExceptionHandlerUtil.ERROR.UNKNOWN));
        }
    }

    @Override
    public void onComplete() {

    }

}
