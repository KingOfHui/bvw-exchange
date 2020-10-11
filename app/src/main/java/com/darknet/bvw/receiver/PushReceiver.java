package com.darknet.bvw.receiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.darknet.bvw.MyApp;
import com.darknet.bvw.R;
import com.darknet.bvw.activity.XchainMainThreeActivity;
import com.darknet.bvw.model.PushModel;
import com.google.gson.Gson;

import cn.jpush.android.api.JPushInterface;

public class PushReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";

    private static NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    private int notificationId;

    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            Bundle bundle = intent.getExtras();

            this.context = context;

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Log.e(TAG, "[MyReceiver] 接收Registration Id : " + regId);
                //send the Registration Id to your server...

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                Log.e(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                Log.e(TAG, "[MyReceiver] 接收到推送下来的自定义消息EXTRA_EXTRA=: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
//				processCustomMessage(context, bundle);
//                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
//                ZqhLogUtils.e(TAG, "[MyReceiver] 接收Registration Id : " + regId);
//                sendNotice(bundle.getString(JPushInterface.EXTRA_MESSAGE));
//                receivingNotification(bundle);

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                Log.e(TAG, "[MyReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                Log.e(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
//                receivingNotification(bundle);
//                printBundle(bundle);
//                String sssVal = printBundle(bundle);
//                Log.e(TAG, "[MyReceiver] 接收到推送下来的通知content: " + sssVal);


            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                Log.e(TAG, "[MyReceiver] 用户点击打开了通知");

                printBundle(bundle);

//                printBundle(bundle);
//                String sssVal = printBundle(bundle);

//                ZqhLogUtils.e(TAG, "[MyReceiver] 用户点击打开了通知: " + sssVal);
//                //打开自定义的Activity
//                Intent i = new Intent(context, TestActivity.class);
//                i.putExtras(bundle);
//                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                context.startActivity(i);

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                Log.e(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            } else {
                Log.e(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {

        }
    }


    private void receivingNotification(Bundle bundle){
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        Log.e(TAG, " title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        Log.e(TAG, "message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Log.e(TAG, "extras : " + extras);
//        String jsonVal = bundle.getString(JPushInterface.EXTRA_EXTRA);
        if(!TextUtils.isEmpty(message)){
            Gson gson = new Gson();
            PushModel response = gson.fromJson(message, PushModel.class);
            Log.e(TAG, "jsonVal=" + response.toString());
//            handlePage(response);
            sendNotice(response.getContent());
        }
    }


    // 打印所有的 intent extra 数据
    private void printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Log.e(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    String jsonVal = bundle.getString(JPushInterface.EXTRA_EXTRA);

                    if (TextUtils.isEmpty(jsonVal)) {
                        return;
                    }

                    Log.e(TAG, "Get message extra JSON error!"+jsonVal);
                    Gson gson = new Gson();
                    PushModel response = gson.fromJson(jsonVal, PushModel.class);
                    Log.e(TAG, "jsonVal=" + response.toString());
                    handlePage(response);
//                    sendNotice(jsonVal);

                } catch (Exception e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.get(key));
            }
        }
    }


    private void handlePage(PushModel pushReceModel) {


        Intent mainIntent = new Intent(context, XchainMainThreeActivity.class);
        context.startActivity(mainIntent);

//        if (pushReceModel != null) {
//            String fTypeVal = pushReceModel.getfType();
//            if (fTypeVal != null && fTypeVal.equals("health")) {
//                //健康模块  ,sport,viscera,spirit,sleep
//                String sTypeVal = pushReceModel.getsType();
//                String idVal = pushReceModel.getSungoId();
//                if (TextUtils.isEmpty(sTypeVal) && TextUtils.isEmpty(idVal)) {
//                    //直接跳转健康页面
//                    Intent mainIntent = new Intent(context, MainActivity.class);
//                    context.startActivity(mainIntent);
//                } else if (sTypeVal != null) {
//                    Intent jumpIntent = new Intent();
//                    if (sTypeVal.equals("sport")) {
//                        jumpIntent.setClass(context, YunDongDetailActivity.class);
//                    } else if (sTypeVal.equals("viscera")) {
//                        jumpIntent.setClass(context, ZangfuDetailActivity.class);
//                    } else if (sTypeVal.equals("spirit")) {
//                        jumpIntent.setClass(context, JingShenDetailActivity.class);
//                    } else if (sTypeVal.equals("sleep")) {
//                        jumpIntent.setClass(context, ShuiMianDetailActivity.class);
//                    } else {
//                        jumpIntent.setClass(context, MainActivity.class);
//                    }
//                    context.startActivity(jumpIntent);
//                }
//            } else if (fTypeVal != null && fTypeVal.equals("promotion")) {
//                //晋级模块  medition ,diet , sport ,shopping_mall
//                String sTypeVal = pushReceModel.getsType();
//                String idVal = pushReceModel.getSungoId();
//
//                if (TextUtils.isEmpty(sTypeVal) && TextUtils.isEmpty(idVal)) {
//
//                    Intent mainIntent = new Intent(context, MainActivity.class);
//                    context.startActivity(mainIntent);
//                    //跳转晋级模块,通过发通知的方式
//                    JiJiEvent jiJiEvent = new JiJiEvent();
//                    jiJiEvent.setType(1);
//                    EventBus.getDefault().post(jiJiEvent);
////                    JiJiEvent
//                } else if (sTypeVal != null) {
//
//                    Intent mainIntent = new Intent(context, MainActivity.class);
//                    context.startActivity(mainIntent);
//                    //跳转晋级模块,通过发通知的方式
//                    JiJiEvent jiJiEvent = new JiJiEvent();
//                    if (sTypeVal.equals("medition")) {
//                        //跳转晋级模块,通过发通知的方式
//                        jiJiEvent.setType(1);
//                        EventBus.getDefault().post(jiJiEvent);
//                        if (!TextUtils.isEmpty(idVal)) {
//                            try {
//                                Intent radioIntent = new Intent(context, RadioAlumActivity.class);
//                                radioIntent.putExtra("alumId", Integer.parseInt(idVal));
//                                context.startActivity(radioIntent);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                    } else if (sTypeVal.equals("diet")) {
//                        //跳转晋级模块,通过发通知的方式
//                        jiJiEvent.setType(2);
//                        EventBus.getDefault().post(jiJiEvent);
//                    } else if (sTypeVal.equals("sport")) {
//                        //跳转晋级模块,通过发通知的方式
//                        jiJiEvent.setType(3);
//                        EventBus.getDefault().post(jiJiEvent);
//                    } else if (sTypeVal.equals("shopping_mall")) {
//                        jiJiEvent.setType(4);
//                        EventBus.getDefault().post(jiJiEvent);
//                    }
//                }
//            } else if (fTypeVal != null && fTypeVal.equals("week")) {
//                Intent mainIntent = new Intent(context, MainActivity.class);
//                context.startActivity(mainIntent);
//                EventBus.getDefault().post(new WeekJumpEvent());
//            } else if (fTypeVal != null && fTypeVal.equals("msg")) {
//                Intent mainIntent = new Intent(context, PushMsgListActivity.class);
//                context.startActivity(mainIntent);
//            } else if (fTypeVal != null && fTypeVal.equals("articleDetail")) {
//                try {
//                    String sTypeVal = pushReceModel.getsType();
//                    Intent mainIntent = new Intent(context, FoodDetailActivity.class);
//                    int articleId = Integer.parseInt(sTypeVal);
//                    mainIntent.putExtra("articleId", articleId);
//                    context.startActivity(mainIntent);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
    }


    //send msg to MainActivity
//    private void processCustomMessage(Context context, Bundle bundle) {
//        if (MainActivity.isForeground) {
//            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//            Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
//            msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
//            if (!ExampleUtil.isEmpty(extras)) {
//                try {
//                    JSONObject extraJson = new JSONObject(extras);
//                    if (extraJson.length() > 0) {
//                        msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
//                    }
//                } catch (JSONException e) {
//
//                }
//
//            }
//            LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
//        }
//    }


    private void sendNotice(String msgContent) {

        Toast.makeText(context, msgContent, Toast.LENGTH_LONG).show();

        Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + msgContent);

        if (msgContent == null) {
            return;
        }

        if (mNotificationManager == null) {
            mNotificationManager = (NotificationManager) MyApp.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
//			ExampleApplication.sp.putInt(KEY_NOTIFICATION_ID, NOTIFICATION_ID);
        }

        mBuilder = new NotificationCompat.Builder(MyApp.getInstance())
                .setSmallIcon(R.mipmap.logo_icon)
                .setContentTitle("BIW")
                .setAutoCancel(true)
                .setContentText(msgContent);

//        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(MyApplication.getInstance());
//        builder.statusBarDrawable = R.mipmap.icon;
//        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL
//                | Notification.FLAG_SHOW_LIGHTS;  //设置为自动消失和呼吸灯闪烁
//        builder.notificationDefaults = Notification.DEFAULT_SOUND
//                | Notification.DEFAULT_VIBRATE
//                | Notification.DEFAULT_LIGHTS;  // 设置为铃声、震动、呼吸灯闪烁都要
//        JPushInterface.setPushNotificationBuilder(1, builder);

        Intent intent = new Intent(MyApp.getInstance(), PushJumpActivity.class);
        intent.putExtra("key_notify", msgContent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mBuilder.setContentIntent(PendingIntent.getActivity(MyApp.getInstance(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT));

//		int temp = ExampleApplication.sp.getInt(KEY_NOTIFICATION_ID);
        int temp = 1000;
        notificationId = ++temp;
        mNotificationManager.notify(notificationId, mBuilder.build());
        // 存储
//		BaseApp.sp.putInt(KEY_NOTIFICATION_ID, notificationId);

    }

}
