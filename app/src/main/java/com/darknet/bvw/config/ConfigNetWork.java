package com.darknet.bvw.config;

import com.darknet.bvw.BuildConfig;

public class ConfigNetWork {

    /**
     * 请求Root
     */
    public static String JAVA_API_URL;// = BuildConfig.JAVA_API_URL;
    public static String JAVA_SOCKET_URL;// = BuildConfig.JAVA_SOCKET_URL;

    public static String WEB_URL;

    public static void initWebWork(){
//        if(BuildConfig.WORK.equalsIgnoreCase("line")){
//            WEB_URL ="file:///android_asset/prod/index.html";
//        }else {
//            WEB_URL ="file:///android_asset/test/index.html";
//        }
//        if (BuildConfig.DEBUG) {
            JAVA_API_URL = "https://t.bvw.im/";
            JAVA_SOCKET_URL = "wss://ws-test.bvw.im/websocket";
            WEB_URL ="file:///android_asset/test/index.html";
//        }else {
//            JAVA_API_URL = "https://api.bvw.im/";
//            JAVA_SOCKET_URL = "wss://ws.bvw.im/websocket";
//            WEB_URL ="file:///android_asset/prod/index.html";
//        }
    }


}
