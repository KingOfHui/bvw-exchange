package com.darknet.bvw.socket;

import android.util.Log;

import com.darknet.bvw.model.KlineItemBean;
import com.darknet.bvw.model.event.KLineEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Locale;

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

    public void init() {
        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "wss://ws-test.bvw.im/websocket");
        resetSubscriptions();
    }

    public void disconnectStomp() {
        mStompClient.disconnect();
    }

    public void connectStomp(String coinSymbol) {

        mStompClient.withClientHeartbeat(1000).withServerHeartbeat(1000);

        resetSubscriptions();

        Disposable dispLifecycle = mStompClient.lifecycle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lifecycleEvent -> {
                    switch (lifecycleEvent.getType()) {
                        case OPENED:
                            Log.e(TAG,"Stomp connection opened");
                            break;
                        case ERROR:
                            Log.e(TAG, "Stomp connection error", lifecycleEvent.getException());
                            break;
                        case CLOSED:
                            Log.e(TAG,"Stomp connection closed");
                            resetSubscriptions();
                            break;
                        case FAILED_SERVER_HEARTBEAT:
                            Log.e(TAG,"Stomp failed server heartbeat");
                            break;
                    }
                });

        compositeDisposable.add(dispLifecycle);

        // Receive greetings          apiUrl = "wss://ws-test.bvw.im/websocket/topic/market/kline/BTC-USDT";


        Disposable dispTopic = mStompClient.topic("/topic/market/kline/BTC-USDT")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topicMessage -> {
                    Log.d(TAG, "Received " + topicMessage.getPayload());
                    EventBus.getDefault().post(new Gson().fromJson(topicMessage.getPayload(), KlineItemBean.class));
                }, throwable -> {
                    Log.e(TAG, "Error on subscribe topic", throwable);
                });


        //K线订阅
        Disposable dispTopic1 = mStompClient.topic("/topic/market/kline/" + coinSymbol)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topicMessage -> {
                    //{"marketId":"BVW-USDT","openPrice":0.44,"highestPrice":0.44,"lowestPrice":0.44,"closePrice":0.44,"time":1578502620000,"period":"1min","count":0,"volume":0,"turnover":0}
                    Log.d(TAG, "Received--K线订阅 " + topicMessage.getPayload());
                    KLineEvent kLineEvent = new GsonBuilder().create().fromJson(topicMessage.getPayload(), KLineEvent.class);
                    EventBus.getDefault().post(kLineEvent);
                }, throwable -> {
                    Log.e(TAG, "Error on subscribe topic", throwable);
                });

        //盘口订阅
        Disposable tradeDispTopic = mStompClient.topic("/topic/market/trade-plate/" + coinSymbol)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topicMessage -> {
                    Log.d(TAG, "Received--盘口订阅 " + topicMessage.getPayload());
                }, throwable -> {
                    Log.e(TAG, "Error on subscribe topic", throwable);
                });
        compositeDisposable.add(tradeDispTopic);
        compositeDisposable.add(dispTopic);
        compositeDisposable.add(dispTopic1);
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
            compositeDisposable.dispose();
        }
        compositeDisposable = new CompositeDisposable();
    }

    public void disConnect() {
        mStompClient.disconnect();

        if (mRestPingDisposable != null) mRestPingDisposable.dispose();
        if (compositeDisposable != null) compositeDisposable.dispose();
    }

}
