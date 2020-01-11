//package com.zqh.http;
//
//import android.os.Handler;
//
//import java.io.IOException;
//import java.util.Map;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
///**
// * Created by wanghaofei on 2018/3/26.
// */
//
//public class OkHttpProcessor implements IHttpProcessor {
//
//    private OkHttpClient okHttpClient;
//    //Okhttp返回的结果是在子线程,通过handler抛传给主线程
//    private Handler handler;
//
//    public OkHttpProcessor() {
//        okHttpClient = new OkHttpClient();
//        handler = new Handler();
//    }
//
//
//    @Override
//    public void post(String url, Map<String, Object> params, final ICallback callback) {
//        //此处需要添加一个请求头，这个是okhttp的bug
//        RequestBody requestBody = null;
//        final Request request = new Request.Builder().url(url).post(requestBody).header("User-Agent","a").build();
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, final IOException e) {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        callback.onFailure(e.toString());
//                    }
//                });
//            }
//
//            @Override
//            public void onResponse(final Call call, final Response response) throws IOException {
//
//                if (response.isSuccessful()) {
//
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            callback.onSuccess(response.body().toString());
//                        }
//                    });
//                }
//
//            }
//        });
//    }
//
//    @Override
//    public void get(String url, Map<String, Object> params, ICallback callback) {
//
//    }
//}
