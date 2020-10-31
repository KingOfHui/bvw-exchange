package com.darknet.bvw.net.retrofit;



import com.darknet.bvw.net.retrofit.errorhandler.ExceptionHandlerUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class BaseObserver<T> implements Observer<T> {
//    MvvmBaseModel baseModel;
    MvvmNetworkObserver<T> mvvmNetworkObserver;

    public BaseObserver(/*MvvmBaseModel baseModel,*/ MvvmNetworkObserver<T> mvvmNetworkObserver) {
//        this.baseModel = baseModel;
        this.mvvmNetworkObserver = mvvmNetworkObserver;
    }

    @Override
    public void onSubscribe(Disposable d) {

//        if (baseModel != null) {
//            baseModel.addDisposable(d);
//        }
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
            mvvmNetworkObserver.onFailure(new ExceptionHandlerUtil.ResponseThrowable(e,ExceptionHandlerUtil.ERROR.UNKNOWN));
        }
    }

    @Override
    public void onComplete() {

    }

}
