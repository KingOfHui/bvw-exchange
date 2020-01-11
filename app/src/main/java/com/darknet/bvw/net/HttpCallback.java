package com.darknet.bvw.net;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by wanghaofei on 2018/3/26.
 */

public abstract class HttpCallback<T> implements ICallback {

    @Override
    public void onSuccess(String result) {

        Gson gson = new Gson();
        Class<?> clz = analysisClassInfo(this);
        T objResult = (T)gson.fromJson(result,clz);
        onSuccess(objResult);

    }

    @Override
    public void onFailure(String e) {

    }

    public abstract void onSuccess(T result);


    public static Class<?> analysisClassInfo(Object object){
        Type genType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
        return (Class<?>)params[0];
    }
}
