package com.darknet.bvw.net.retrofit.errorhandler;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * HttpResponseFunc处理一下两类网络错误
 * 1.http请求相关的错误，例如：404,403，socket timeout等
 * 2.应用数据的错误会抛出RuntimeException，最后也会走到这个函数来统一处理
 * @param <T>
 */
public class HttpErrorHandler<T> implements Function<Throwable, Observable<T>> {

    @Override
    public Observable<T> apply(Throwable throwable) throws Exception {
        return Observable.error(ExceptionHandlerUtil.handleException(throwable));
    }

}
