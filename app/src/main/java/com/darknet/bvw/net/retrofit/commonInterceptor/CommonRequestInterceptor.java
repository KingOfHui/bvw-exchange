package com.darknet.bvw.net.retrofit.commonInterceptor;


import android.os.Build;

import com.darknet.bvw.BuildConfig;
import com.darknet.bvw.MyApp;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.util.bitcoinj.BitcoinjKit;
import com.darknet.bvw.util.language.SPUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CommonRequestInterceptor implements Interceptor {

//    private INetworkRequiredInfo requiredInfo;
//
//    public CommonRequestInterceptor(INetworkRequiredInfo requiredInfo) {
//        this.requiredInfo = requiredInfo;
//    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        int lanType = SPUtil.getInstance(MyApp.getInstance()).getSelectLanguage();

//        Log.e("jiguang","language="+lanType);

        ETHWalletModel walletModel = WalletDaoUtils.getCurrent();
        String privateKey = walletModel.getPrivateKey();
        String addressVals = walletModel.getAddress();
        String msg = "" + System.currentTimeMillis();
        String signVal = BitcoinjKit.signMessageBy58(msg, privateKey);
        builder.addHeader("Chain-Authentication", addressVals + "#" + msg + "#" + signVal);
        if(lanType == 1){
            //中文
//            Request request = chain.request().newBuilder()
            builder.addHeader("Accept-Language", "zh-CN");
//                    .build();

//            return chain.proceed(request);
        }else {
            builder.addHeader("Accept-Language", "en-US");
//                    .build();

//            return chain.proceed(request);
        }
        builder.addHeader("os", Build.MODEL);
        builder.addHeader("appVersion", BuildConfig.VERSION_NAME);
        return chain.proceed(builder.build());
    }
}
