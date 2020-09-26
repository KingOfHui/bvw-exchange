package com.darknet.bvw.util;

import android.content.Context;
import android.util.Log;
import com.darknet.bvw.util.language.SPUtil;
import java.io.IOException;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class UserAgentIntercepter implements Interceptor {

    private Context context;

    public UserAgentIntercepter(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        String jiguangId = JPushInterface.getRegistrationID(context);
//        String jiguangId = "00000";

//        Log.e("jiguang","jiguang="+jiguangId);

        int lanType = SPUtil.getInstance(context).getSelectLanguage();

//        Log.e("jiguang","language="+lanType);

       if(lanType == 1){
            //中文
           Request request = chain.request().newBuilder()
                   .addHeader("clientId", jiguangId)
//                   .addHeader("lang","cn")
                   .addHeader("Accept-Language","zh_CN")
                   .build();

           return chain.proceed(request);
        }else {
           Request request = chain.request().newBuilder()
                   .addHeader("clientId", jiguangId)
//                   .addHeader("lang","en")
                   .addHeader("Accept-Language","en_US")
                   .build();

           return chain.proceed(request);
        }
    }
}
