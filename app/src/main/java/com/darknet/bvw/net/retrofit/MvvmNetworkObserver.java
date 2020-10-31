package com.darknet.bvw.net.retrofit;

public interface MvvmNetworkObserver<F> {

    void onSuccess(F t, boolean isFromCache);

    void onFailure(Throwable throwable);
}
