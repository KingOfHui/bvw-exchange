package com.darknet.bvw.net.retrofit;


import com.darknet.bvw.common.BaseResponse;
import com.darknet.bvw.net.retrofit.errorhandler.ExceptionHandlerUtil;

import java.io.IOException;

import io.reactivex.functions.Function;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BIWNetworkApi extends NetWorkApi {
    private static final String BASE_URL = "https://api.bitw.im/";
    private static final String BASE_URL_TEST = "https://api.bitw.im/";

    private static volatile BIWNetworkApi sInstance;

    public static BIWNetworkApi getInstance() {
        if (sInstance == null) {
            synchronized (BIWNetworkApi.class) {
                if (sInstance == null) {
                    sInstance = new BIWNetworkApi();
                }
            }
        }
        return sInstance;
    }

    private BIWNetworkApi() {
        super();
    }

    public static <T> T getService(Class<T> service) {
        return getInstance().getRetrofit(service).create(service);
    }

    @Override
    protected Interceptor getInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
//                builder.addHeader("token", "");

                return chain.proceed(builder.build());
            }
        };
    }

    @Override
    protected <T> Function<T, T> getAppErrorHandler() {
        return new Function<T, T>() {
            @Override
            public T apply(T response) throws Exception {
                //response不为0，出现错误
                if (response instanceof BaseResponse && ((BaseResponse) response).getCode() != 0) {
                    ExceptionHandlerUtil.ServerException exception = new ExceptionHandlerUtil.ServerException();
                    exception.code = ((BaseResponse) response).getCode();
                    exception.message = ((BaseResponse) response).getMsg() != null ? ((BaseResponse) response).getMsg() : "";
                    throw exception;
                }
                return response;
            }
        };
    }

    @Override
    public String getFormal() {
        return BASE_URL;
    }

    @Override
    public String getTest() {
        return BASE_URL_TEST;
    }
}
