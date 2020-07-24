package com.darknet.bvw.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.darknet.bvw.socket.SocketTool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.Nullable;

/**
 * @author lijingya
 * @description 添加描述
 * @email lijingya@91118.com
 * @createDate 2020/1/17
 * @company 杭州天音
 */
public class WorkManagerService extends Service {

    private ExecutorService mExecutorService;

    public static final String EXTRA_DATA = "extra.data";

    private SocketTool socketTool;

    public static void startService(Context context, Bundle bundle) {
        Intent intent = new Intent(context, WorkManagerService.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startService(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null && null != mExecutorService && !mExecutorService.isShutdown()) {
            Bundle data = intent.getExtras();
            if (data != null) {
                mExecutorService.submit(new TaskRunnable(data.getString(EXTRA_DATA)));
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mExecutorService = Executors.newSingleThreadExecutor();
    }

    final class TaskRunnable implements Runnable {

        private String data;

        public TaskRunnable(String data) {
            this.data = data;
        }

        @Override
        public void run() {
            socketTool = SocketTool.getInstance();
            socketTool.init();
            socketTool.connectStomp(data);
        }
    }

    @Override
    public void onDestroy() {
        socketTool.disConnect();
        socketTool.disconnectStomp();
        super.onDestroy();
    }
}
