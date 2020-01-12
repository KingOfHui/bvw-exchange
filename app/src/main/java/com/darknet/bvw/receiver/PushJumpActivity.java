package com.darknet.bvw.receiver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.darknet.bvw.activity.XchainMainThreeActivity;
import com.darknet.bvw.model.event.PushEvent;

import org.greenrobot.eventbus.EventBus;

public class PushJumpActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String pushMsgBean = (String) getIntent().getSerializableExtra("key_notify");
//        PushMsgBean pushMsgBean = (PushMsgBean) getIntent().getSerializableExtra(Constant.KEY_NOTIFY);

        Log.e("MyReceiver", "[MyReceiver] 接收到推送下来的自定义消息:PushJumpActivity= " + pushMsgBean);

        EventBus.getDefault().post(new PushEvent());

        Intent testIntent = new Intent(PushJumpActivity.this, XchainMainThreeActivity.class);
//        testIntent.putExtra("pushbean",pushMsgBean);
        testIntent.putExtra("key_notify",pushMsgBean);

        startActivity(testIntent);
        finish();

    }
}




