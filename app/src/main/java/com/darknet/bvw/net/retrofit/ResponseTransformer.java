package com.darknet.bvw.net.retrofit;

import androidx.annotation.NonNull;

import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.net.retrofit.errorhandler.HttpErrorHandler;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

/**
 * @author : dinghui
 * @date : 2020/12/21 17:00
 */
@SuppressWarnings("unchecked")
public class ResponseTransformer<T> implements ObservableTransformer<BaseResponse<T>, T> {

    @NonNull
    @Override
    public ObservableSource<T> apply(Observable<BaseResponse<T>> upstream) {
        return upstream.map(tBaseResponse -> {
            return tBaseResponse.getData() == null ? (T)new Object() : tBaseResponse.getData();
        }).onErrorResumeNext(new HttpErrorHandler<T>());
    }
}
