package com.darknet.bvw;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;

import com.darknet.bvw.db.Entity.DaoMaster;
import com.darknet.bvw.db.Entity.DaoSession;
import com.darknet.bvw.db.MyDBHelper;
import com.darknet.bvw.util.SharedPreferencesUtil;
import com.darknet.bvw.util.UserAgentIntercepter;
import com.darknet.bvw.util.language.LocalManageUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.github.jokar.multilanguages.library.LanguageLocalListener;
import com.github.jokar.multilanguages.library.MultiLanguage;
import com.kk.taurus.playerbase.config.PlayerLibrary;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okhttputils.OkHttpUtils;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;

public class MyApp extends Application {

    private static MyApp sInstance;

    private String jiguangId;

    private DaoSession daoSession;

    public static MyApp getInstance() {
        return sInstance;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    //----------------------------国际化配置--------------
    @Override
    protected void attachBaseContext(Context base) {
        sInstance = this;
        //第一次进入app时保存系统选择语言(为了选择随系统语言时使用，如果不保存，切换语言后就拿不到了）
        LocalManageUtil.saveSystemCurrentLanguage(base);
        super.attachBaseContext(MultiLanguage.setLocal(base));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //用户在系统设置页面切换语言时保存系统选择语言(为了选择随系统语言时使用，如果不保存，切换语言后就拿不到了）
        LocalManageUtil.saveSystemCurrentLanguage(getApplicationContext(), newConfig);
        MultiLanguage.onConfigurationChanged(getApplicationContext());
    }
    //--------------------------国际化结束--------


    //聊天相关
//    private WfcUIKit wfcUIKit;

    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(this);
        //初始化库
        PlayerLibrary.init(this);
        //初始化激光
        //初始化视频播放
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
//
        jiguangId = JPushInterface.getRegistrationID(this);

        SharedPreferencesUtil.getInstance(this, "bvw");
        //---------------国际化------
        MultiLanguage.init(new LanguageLocalListener() {
            @Override
            public Locale getSetLanguageLocale(Context context) {
                //返回自己本地保存选择的语言设置
                return LocalManageUtil.getSetLanguageLocale(context);
            }
        });
        MultiLanguage.setApplicationLanguage(this);
        //----------------国际化结束------------


        // bugly，务必替换为你自己的!!!
        CrashReport.initCrashReport(getApplicationContext(), BuildConfig.BuglyId, false);


        //初始化数据库
        initDb();
        //网络初始化
        initOkGo();


        //野火
//        Config.validateConfig();

        // bugly，务必替换为你自己的!!!
//        CrashReport.initCrashReport(getApplicationContext(), BuildConfig.BuglyId, false);
//        // 只在主进程初始化
//        if (getCurProcessName(this).equals(BuildConfig.APPLICATION_ID)) {
//            wfcUIKit = new WfcUIKit();
//            wfcUIKit.init(this);
//            PushService.init(this, BuildConfig.APPLICATION_ID);
//            MessageViewHolderManager.getInstance().registerMessageViewHolder(LocationMessageContentViewHolder.class);
//            setupWFCDirs();
//        }
    }


    //初始化数据库
    private void initDb() {
        //创建数据库表
//        DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper(this, "wallet", null);

        MyDBHelper myDBHelper = new MyDBHelper(this,"wallet",null);
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        daoSession = new DaoMaster(db).newSession();



    }

    //网络设置
    private void initOkGo() {

//        String headerContent = "{clientId="+jiguangId+",clientOs=android,clientOsVersion="+getSystemVersion()+",appVersion="+getCurVersionName(this)"}";

        //---------这里给出的是示例代码,告诉你可以这么传,实际使用的时候,根据需要传,不需要就不传-------------//
        HttpHeaders headers = new HttpHeaders();
        headers.put("Accept-Encoding", "gzip");    //header不支持中文，不允许有特殊字符
        headers.put("Content-Type", "text/xml");
        headers.put("clientId", jiguangId);
        headers.put("clientOs", "android");


//        int lanType = SPUtil.getInstance(this).getSelectLanguage();
//
//        if(lanType == 1){
//            //英文
//            headers.put("lang", "en");
//        }else if(lanType == 0){
//            //中文
//            headers.put("lang", "cn");
//        }else {
//            headers.put("lang", "en");
//        }





        String sysversion = getSystemVersion();

        if (sysversion == null) {
            headers.put("clientOsVersion", "");
        } else {
            headers.put("clientOsVersion", sysversion);
        }

        String appversion = getCurVersionName(this);

        if (appversion == null) {
            headers.put("appVersion", "");
        } else {
            headers.put("appVersion", appversion);
        }


        // {clientId=8d27de1a481e5c140f827841e990551ab817355361edbf230b3ab1c28208c83b, clientOs=ios, clientOsVersion=ios 13.1, appVersion=1.0.0}

//        headers.put("Connection", "close");

//        HttpParams params = new HttpParams();
//        params.put("commonParamsKey1", "commonParamsValue1");     //param支持中文,直接传,不要自己编码
//        params.put("commonParamsKey2", "这里支持中文参数");
        //----------------------------------------------------------------------------------------//

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setColorLevel(Level.INFO);                               //log颜色级别，决定了log在控制台显示的颜色
        builder.addInterceptor(loggingInterceptor);
        builder.addInterceptor(new UserAgentIntercepter(this));
        //通知栏看请求
//        builder.addInterceptor(new ChuckInterceptor(this));

        //添加OkGo默认debug日志
        //第三方的开源库，使用通知显示当前请求的log，不过在做文件下载的时候，这个库好像有问题，对文件判断不准确
        //builder.addInterceptor(new ChuckInterceptor(this));

        //超时时间设置，默认60秒
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);   //全局的连接超时时间
//        builder.
        //自动管理cookie（或者叫session的保持），以下几种任选其一就行
        //builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));            //使用sp保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));              //使用数据库保持cookie，如果cookie不过期，则一直有效
        //builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));            //使用内存保持cookie，app退出后，cookie消失

        //https相关设置，以下几种方案根据需要自己设置
        //方法一：信任所有证书,不安全有风险
//        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
//        //方法二：自定义信任规则，校验服务端证书
//        HttpsUtils.SSLParams sslParams2 = HttpsUtils.getSslSocketFactory(new SafeTrustManager());
//        //方法三：使用预埋证书，校验服务端证书（自签名证书）
//        //HttpsUtils.SSLParams sslParams3 = HttpsUtils.getSslSocketFactory(getAssets().open("srca.cer"));
//        //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
//        //HttpsUtils.SSLParams sslParams4 = HttpsUtils.getSslSocketFactory(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"));
//        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);
//        //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
//        builder.hostnameVerifier(new SafeHostnameVerifier());


        //必须调用初始化
        OkHttpUtils.init(this);
        //以下都不是必须的，根据需要自行选择
        OkHttpUtils.getInstance()//
                .debug("OkHttpUtils")                                              //是否打开调试
                .setConnectTimeout(OkHttpUtils.DEFAULT_MILLISECONDS)               //全局的连接超时时间
                .setReadTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS)                  //全局的读取超时时间
                .setWriteTimeOut(15000);                 //全局的写入超时时间
//                .setWriteTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS);                 //全局的写入超时时间
        //.setCookieStore(new MemoryCookieStore())                           //cookie使用内存缓存（app退出后，cookie消失）
        //.setCookieStore(new PersistentCookieStore())                       //cookie持久化存储，如果cookie不过期，则一直有效
//                .addCommonHeaders(headers)                                         //设置全局公共头
//                .addCommonParams(params);


        // 其他统一的配置
        // 详细说明看GitHub文档：https://github.com/jeasonlzy/
        OkGo.getInstance().init(this)                           //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置会使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3)
                .addCommonHeaders(headers);
        //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
        //全局公共头
//                .addCommonParams(params);                       //全局公共参数
    }

//    private void setupWFCDirs() {
//        File file = new File(Config.VIDEO_SAVE_DIR);
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//        file = new File(Config.AUDIO_SAVE_DIR);
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//        file = new File(Config.FILE_SAVE_DIR);
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//        file = new File(Config.PHOTO_SAVE_DIR);
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//    }

    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }


    //野火------------
//    private void setupWFCDirs() {
//        File file = new File(Config.VIDEO_SAVE_DIR);
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//        file = new File(Config.AUDIO_SAVE_DIR);
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//        file = new File(Config.FILE_SAVE_DIR);
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//        file = new File(Config.PHOTO_SAVE_DIR);
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//    }

    private String getSystemModel() {
        try {
            return android.os.Build.MODEL;
        } catch (Exception e) {
            e.printStackTrace();
            return "获取失败";
        }
    }

    public static String getSystemVersion() {
        try {
            return android.os.Build.VERSION.RELEASE;
        } catch (Exception e) {
            e.printStackTrace();
            return "获取失败";
        }
    }

    public static String getCurVersionName(Context ctx) {
        try {
            PackageInfo pInfo = ctx.getPackageManager().getPackageInfo(
                    ctx.getPackageName(), 0);
            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }


    //------------------野火
}
