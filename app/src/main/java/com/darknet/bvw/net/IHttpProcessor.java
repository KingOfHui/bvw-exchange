package com.darknet.bvw.net;

import java.util.Map;

/**
 * Created by wanghaofei on 2018/3/26.
 */

public interface IHttpProcessor {

    void post(String url, Map<String,Object> params, ICallback callback);

    void get(String url, Map<String,Object> params, ICallback callback);
}
