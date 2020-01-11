package com.darknet.bvw;

import android.content.Context;
import android.util.Log;

import com.darknet.bvw.model.ChangeDepthResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.util.Random;

public class DataRequest {

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
        ChangeDepthResponse data = null;
        Random random = new Random();
        boolean a = random.nextBoolean();
        if (!a) {
            data = new Gson().fromJson(
                    getStringFromAssert(context, "deph2.json"),
                    new TypeToken<ChangeDepthResponse>() {
                    }.getType());
            Log.e(TAG, "getALL: " + 2);
        } else {
            data = new Gson().fromJson(
                    getStringFromAssert(context, "deph.json"),
                    new TypeToken<ChangeDepthResponse>() {
                    }.getType());
            Log.e(TAG, "getALL: " + 1);
        }
        return data;
    }


}
