package com.darknet.bvw.net.retrofit;

import android.app.Application;
import android.content.Context;

import com.darknet.bvw.BuildConfig;
import com.darknet.bvw.net.retrofit.commonInterceptor.CommonRequestInterceptor;
import com.darknet.bvw.net.retrofit.commonInterceptor.CommonResponseInterceptor;
import com.darknet.bvw.net.retrofit.errorhandler.HttpErrorHandler;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class NetWorkApi implements IEnvironment {
    public static final String TAG = "NetWorkApi";

    private String mBaseUrl;
    private static boolean mIsFormal = true;
//    private static INetworkRequiredInfo iNetworkRequiredInfo;
    public static Map<String, Retrofit> retrofitMap = new HashMap<>();


    protected NetWorkApi() {
        if (!mIsFormal) {
            mBaseUrl = getTest();
        } else {
            this.mBaseUrl = getFormal();
        }
    }


    public static void init(Application context) {
//        iNetworkRequiredInfo = networkRequiredInfo;
        mIsFormal = EnvironmentActivity.isOfficialEnvironment(context);
    }

    protected Retrofit getRetrofit(Class service) {
        if (retrofitMap.get(mBaseUrl + service.getName()) != null) {
            return retrofitMap.get(mBaseUrl + service.getName());
        }
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(mBaseUrl);
        builder.client(getOkHttpClient());
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        Retrofit retrofit = builder.build();
        retrofitMap.put(mBaseUrl + service.getName(), retrofit);
        return retrofit;
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        if (getInterceptor() != null) {
            okHttpClientBuilder.addInterceptor(getInterceptor());
        }
        okHttpClientBuilder.addInterceptor(new CommonRequestInterceptor());
        okHttpClientBuilder.addInterceptor(new CommonResponseInterceptor());
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClientBuilder.addInterceptor(httpLoggingInterceptor);
        }
        return okHttpClientBuilder.build();
    }


    public <T> ObservableTransformer<T, T> applySchedulers(final Observer<T> observer) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                Observable<T> observable = (Observable<T>) upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(getAppErrorHandler())
                        .onErrorResumeNext(new HttpErrorHandler<T>());
                observable.subscribe(observer);
                return observable;
            }
        };
    }

    protected abstract Interceptor getInterceptor();

    protected abstract <T> Function<T, T> getAppErrorHandler();


}
