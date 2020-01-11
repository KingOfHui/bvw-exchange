package com.darknet.bvw.net;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by wanghaofei on 2018/3/26.
 */
public class HttpHelper implements IHttpProcessor {

    private static IHttpProcessor mIHttpProcessor = null;


    private Map<String, Object> mParams;

    private static HttpHelper _instance;


    private HttpHelper() {
        mParams = new HashMap<>();
    }

    //初始化网络库
    public static void init(IHttpProcessor httpProcessor) {
        mIHttpProcessor = httpProcessor;
    }


    public static HttpHelper obtain() {

        synchronized (HttpHelper.class) {
            if (_instance == null) {
                _instance = new HttpHelper();
            }
        }
        return _instance;
    }

    @Override
    public void post(String url, Map<String, Object> params, ICallback callback) {

        final String finalUrl = appendParams(url, params);
        mIHttpProcessor.post(finalUrl, params, callback);
    }

    @Override
    public void get(String url, Map<String, Object> params, ICallback callback) {

        final String finalUrl = appendParams(url, params);
        mIHttpProcessor.get(finalUrl, params, callback);
    }

    public static String appendParams(String url, Map<String, Object> params) {
        if (params == null || params.isEmpty()) {
            return url;
        }

        StringBuilder urlBuilder = new StringBuilder();
        if (urlBuilder.indexOf("?") <= 0) {
            urlBuilder.append("?");
        } else {
            if (!urlBuilder.toString().endsWith("?")) {
                urlBuilder.append("&");
            }
        }

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            urlBuilder.append(entry.getKey()).append("=").append(encode(entry.getValue().toString()));
        }
        return urlBuilder.toString();

    }


    private static String encode(String str) {
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            Log.e("参数转码异常", e.toString());
            throw new RuntimeException(e);
        }
    }
}
