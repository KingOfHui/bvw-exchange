package com.darknet.bvw.net.retrofit;

import com.google.gson.Gson;

import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @ClassName JsonUtils
 * @Description
 * @Author dinghui
 * @Date 2020/12/21 0021 13:31
 */
public class RequestBodyBuilder {
    private HashMap<String, Object> map = new HashMap<>();

    public RequestBodyBuilder addParams(String key, Object value) {
        map.put(key, value);
        return this;
    }


    public RequestBody build() {
        return FormBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map));
    }

    public RequestBody build(Object object) {
        return FormBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(object));
    }
}
