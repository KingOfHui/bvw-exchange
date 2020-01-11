//package com.darknet.bvw.receiver;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//public class TTestActivity extends Activity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
////        PushMsgBean pushMsgBean = (PushMsgBean)getIntent().getSerializableExtra("pushbean");
//        String pushMsgBean = (String)getIntent().getSerializableExtra("key_notify");
//
//        TextView tv = new TextView(this);
//        tv.setText("用户自定义打开的Activity");
////        Intent intent = getIntent();
////        if (null != intent) {
////	        Bundle bundle = getIntent().getExtras();
////            String title = null;
////            String content = null;
////            if(bundle!=null){
////                title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
////                content = bundle.getString(JPushInterface.EXTRA_ALERT);
////            }
////
////        }
//
////        tv.setText("Title : " + pushMsgBean.title + "  " + "Content : " + pushMsgBean.content);
//        tv.setText("Title : " + pushMsgBean);
//
//        addContentView(tv, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
//    }
//
//}
