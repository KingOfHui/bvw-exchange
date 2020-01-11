//package com.darknet.bvw.net;
//import android.util.Log;
//import org.json.JSONObject;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by wanghaofei on 2018/3/29.
// */
//
//public class HttpConfig {
//
////    public void doPostRequest(final BaseRequestModel request, final GenericsCallback genericsCallback) {
////        Log.e("wang","..here..");
////        if (null == request.getUrl()) {
////            //  logicBack.error("网络地址为空", Config.re_error);
////            Log.e("wang","网络地址为空");
////            return;
////        }
////
////        String url = Config.JAVA_API_URL + request.getUrl();
////
////        Log.e("wang","url="+url);
////
////        if (request.getRequestParams() == null) {
////            try {
////                OkHttpUtils
////                        .post()//
////                        .url(url)//
////                        .addHeader("Content-Type","text/xml")
////                        .addHeader("Accept-Encoding","gzip")
////                        .build()//
////                        .execute(genericsCallback);
////            } catch (Exception e) {
////
////            }
////        }else{
////
////            Log.e("params",request.getRequestParams().toString());
////
////            try {
////                OkHttpUtils
////                        .post()//
////                        .params(request.getRequestParams())
////                        .url(url)//
////                        .addHeader("Content-Type","text/xml")
////                        .addHeader("Accept-Encoding","gzip")
////                        .build()//
////                        .execute(genericsCallback);
////            } catch (Exception e) {
////
////            }
////        }
////    }
//
//    public static BaseRequestModel getBaseRequest(String url, JSONObject json) {
//        BaseRequestModel request = new BaseRequestModel();
//        request.setUrl(url);
//        Map<String, String> map = new HashMap();
//        map.put("info",json.toString());
//        if (json != null) {
//            Log.e("request",json.toString());
//        }
////        request.setRequestParams(map);
//        return request;
//    }
//
//
//}
