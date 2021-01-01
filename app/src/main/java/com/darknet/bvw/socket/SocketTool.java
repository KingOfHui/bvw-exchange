package com.darknet.bvw.socket;

import android.util.Log;

import com.darknet.bvw.config.ConfigNetWork;
import com.darknet.bvw.model.KlineItemBean;
import com.darknet.bvw.model.event.KLineEvent;
import com.darknet.bvw.model.event.TradeDetailEvent;
import com.darknet.bvw.model.event.TradePanKouEvent;
import com.darknet.bvw.model.event.TwoFourEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

public class SocketTool {
    private static final String TAG = "SocketTool";
    private StompClient mStompClient;
    private Disposable mRestPingDisposable;
    private final SimpleDateFormat mTimeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
    private CompositeDisposable compositeDisposable;
    private Gson mGson = new GsonBuilder().create();

    private long mLastClickTime = 0;
    public static final long TIME_INTERVAL = 1000L;


    private long tradeDetailmLastClickTime = 0;
    public static final long tradeDetailTIME_INTERVAL = 1000L;

    private long twoFourmLastClickTime = 0;

    //2.本类内部创建对象实例
    private static SocketTool instance = null;

    /**
     * 1.构造方法私有化，外部不能new
     */
    private SocketTool() {

    }

//3.提供一个公有的静态方法，返回实例对象

    public static SocketTool getInstance() {
        if (instance == null) {
            instance = new SocketTool();
        }
        return instance;
    }


    public void init() {
        Log.e(TAG, "...enter...socket....init");
        if (mStompClient == null) {
            mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, ConfigNetWork.JAVA_SOCKET_URL);
//            mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "wss://ws-test.bvw.im/websocket");
        }
        resetSubscriptions();
    }

    public void disconnectStomp() {
        mStompClient.disconnect();
    }

    public void connectStomp(String coinSymbol) {

        mStompClient.withClientHeartbeat(1000).withServerHeartbeat(1000);

        Log.e(TAG, "...enter...socket....connectStomp");


//        resetSubscriptions();

        Disposable dispLifecycle = mStompClient.lifecycle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lifecycleEvent -> {
                    switch (lifecycleEvent.getType()) {
                        case OPENED:
                            Log.e(TAG, "Stomp connection opened");
                            break;
                        case ERROR:
                            Log.e(TAG, "Stomp connection error", lifecycleEvent.getException());
                            break;
                        case CLOSED:
                            Log.e(TAG, "Stomp connection closed");
                            resetSubscriptions();
                            break;
                        case FAILED_SERVER_HEARTBEAT:
                            Log.e(TAG, "Stomp failed server heartbeat");
                            break;
                    }
                });

        compositeDisposable.add(dispLifecycle);

        // Receive greetings          apiUrl = "wss://ws-test.bvw.im/websocket/topic/market/kline/BTC-USDT";


//        Disposable dispTopic = mStompClient.topic("/topic/market/kline/BTC-USDT")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(topicMessage -> {
//                    Log.d(TAG, "Received " + topicMessage.getPayload());
//                    EventBus.getDefault().post(new Gson().fromJson(topicMessage.getPayload(), KlineItemBean.class));
//                }, throwable -> {
//                    Log.e(TAG, "Error on subscribe topic", throwable);
//                });


//        还有另外两个，交易明细是k线页面的成交 交易明细：/topic/market/trade/    24k就是涨幅榜  交易对ID 24K：/topic/market/thumb


        //K线订阅
        Disposable dispTopic1 = mStompClient.topic("/topic/ex/market/kline/" + coinSymbol)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topicMessage -> {
                    //{"marketId":"BIW-USDT","openPrice":0.44,"highestPrice":0.44,"lowestPrice":0.44,"closePrice":0.44,"time":1578502620000,"period":"1min","count":0,"volume":0,"turnover":0}
                    Log.e(TAG, "Received--K线订阅 " + topicMessage.getPayload());
                    KLineEvent kLineEvent = new GsonBuilder().create().fromJson(topicMessage.getPayload(), KLineEvent.class);
                    EventBus.getDefault().post(kLineEvent);
                }, throwable -> {
                    Log.e(TAG, "Error on subscribe topic", throwable);
                });


        //盘口订阅  ex/market/plate/
        Disposable tradeDispTopic = mStompClient.topic("/topic/ex/market/plate/" + coinSymbol)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topicMessage -> {

                    long nowTime = System.currentTimeMillis();
                    if (nowTime - mLastClickTime > TIME_INTERVAL) {
                        // do something
                        mLastClickTime = nowTime;
                        Log.e(TAG, "Received--盘口订阅 发送事件mLastClickTime=" + mLastClickTime + ";nowTime=" + nowTime + topicMessage.getPayload());
                        EventBus.getDefault().post(new TradePanKouEvent());
                    } else {
//                        Toast.makeText(MainActivity.this, "不要重复点击", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Received--盘口订阅 调用频率太高了 " + nowTime);
//                        Log.e(TAG, "Received--盘口订阅 调用频率太高了 "+mLastClickTime);
                    }


                }, throwable -> {
                    Log.e(TAG, "Error on subscribe topic", throwable);
                });


        //24K行情数据
        Disposable twoFourTopic = mStompClient.topic("/topic/ex/market/thumb")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topicMessage -> {

                    long nowTime = System.currentTimeMillis();
                    if (nowTime - twoFourmLastClickTime > TIME_INTERVAL) {
                        // do something
                        twoFourmLastClickTime = nowTime;
                        Log.e(TAG, "Received--24K行情数据 发送事件mLastClickTime=" + twoFourmLastClickTime + ";nowTime=" + nowTime + topicMessage.getPayload());
                        EventBus.getDefault().post(new TwoFourEvent());
                    } else {
//                        Toast.makeText(MainActivity.this, "不要重复点击", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Received--24K行情数据 调用频率太高了 " + nowTime);
//                        Log.e(TAG, "Received--盘口订阅 调用频率太高了 "+mLastClickTime);
                    }


                }, throwable -> {
                    Log.e(TAG, "Error on subscribe topic", throwable);
                });


        //交易明细
        Disposable tradeDetailTopic = mStompClient.topic("/topic/ex/market/trade/" + coinSymbol)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topicMessage -> {

                    long nowTime = System.currentTimeMillis();
                    if (nowTime - tradeDetailmLastClickTime > tradeDetailTIME_INTERVAL) {
                        // do something
                        tradeDetailmLastClickTime = nowTime;
                        Log.e(TAG, "Received--交易明细 发送事件mLastClickTime=" + tradeDetailmLastClickTime + ";nowTime=" + nowTime + topicMessage.getPayload());
                        EventBus.getDefault().post(new TradeDetailEvent());
                    } else {
//                        Toast.makeText(MainActivity.this, "不要重复点击", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Received--交易明细 调用频率太高了 " + nowTime);
//                        Log.e(TAG, "Received--盘口订阅 调用频率太高了 "+mLastClickTime);
                    }


                }, throwable -> {
                    Log.e(TAG, "Error on subscribe topic", throwable);
                });

        compositeDisposable.add(tradeDispTopic);
//        compositeDisposable.add(dispTopic);
        compositeDisposable.add(dispTopic1);
        compositeDisposable.add(twoFourTopic);
        compositeDisposable.add(tradeDetailTopic);
        mStompClient.connect();
    }

//    public void sendEchoViaStomp(View v) {
////        if (!mStompClient.isConnected()) return;
//        compositeDisposable.add(mStompClient.send("/topic/hello-msg-mapping", "Echo STOMP " + mTimeFormat.format(new Date()))
//                .compose(applySchedulers())
//                .subscribe(() -> {
//                    Log.d(TAG, "STOMP echo send successfully");
//                }, throwable -> {
//                    Log.e(TAG, "Error send STOMP echo", throwable);
//                    toast(throwable.getMessage());
//                }));
//    }
//
//    public void sendEchoViaRest(View v) {
//        mRestPingDisposable = RestClient.getInstance().getExampleRepository()
//                .sendRestEcho("Echo REST " + mTimeFormat.format(new Date()))
//                .compose(applySchedulers())
//                .subscribe(() -> {
//                    Log.d(TAG, "REST echo send successfully");
//                }, throwable -> {
//                    Log.e(TAG, "Error send REST echo", throwable);
//                    toast(throwable.getMessage());
//                });
//    }

//    private void addItem(EchoModel echoModel) {
//        mDataSet.add(echoModel.getEcho() + " - " + mTimeFormat.format(new Date()));
//        mAdapter.notifyDataSetChanged();
//        mRecyclerView.smoothScrollToPosition(mDataSet.size() - 1);
//    }
//
//    private void toast(String text) {
//        Log.i(TAG, text);
//        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
//    }
//
//    protected CompletableTransformer applySchedulers() {
//        return upstream -> upstream
//                .unsubscribeOn(Schedulers.newThread())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//    }

    private void resetSubscriptions() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
            compositeDisposable.dispose();
            compositeDisposable = null;
        }
        compositeDisposable = new CompositeDisposable();
    }

    public void disConnect() {
        mStompClient.disconnect();

        if (mRestPingDisposable != null) mRestPingDisposable.dispose();
        if (compositeDisposable != null) compositeDisposable.dispose();
    }

}
