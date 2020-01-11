package com.darknet.bvw;

import android.content.Context;

import com.darknet.bvw.model.ChangeDepthResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;

public class DataRequest20 {
    private static String TAG = "DataRequest";
    private static ChangeDepthResponse datas = null;

    public static String getStringFromAssert(Context context, String fileName) {
        try {
            InputStream in = context.getResources().getAssets().open(fileName);
            int length = in.available();
            byte[] buffer = new byte[length];
            in.read(buffer);
            return new String(buffer, 0, buffer.length, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static ChangeDepthResponse getALL(Context context) {

        return new Gson().fromJson(
                getStringFromAssert(context, "deph20.json"),
                new TypeToken<ChangeDepthResponse>() {
                }.getType());
    }


}

