package com.darknet.bvw.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;
import android.widget.TextView;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.MessageCenterActivity;
import com.darknet.bvw.model.event.PushEvent;

import org.greenrobot.eventbus.EventBus;

public class SuccessDialogybView {

    private static final int CHECK_ECG_DATA = 111;
    //10秒检测一次
    private Handler mHandlerTwo = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == CHECK_ECG_DATA) {
//                EventBus.getDefault().post(new PushEvent());
//
//                Intent tradeIntent = new Intent(context, MessageCenterActivity.class);
//                context.startActivity(tradeIntent);

                if (!((Activity) context).isFinishing() && !((Activity) context).isDestroyed()) {
                    ad.dismiss();
                }
                ((Activity) context).finish();
                return true;
            }
            return false;
        }
    });


    AlertDialog ad;
    Context context;

    public void showTips(final Context context, String content) {

        this.context = context;
        try {
            //剪切板Data对象
            ad = new AlertDialog.Builder(context).create();
            ad.show();
            ad.setContentView(R.layout.dialog_success_view);
            ad.setCanceledOnTouchOutside(false);
            //在弹框上显示软键盘，注意此行需要放到setContentView下面
            ad.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

            TextView contentView = (TextView) ad.findViewById(R.id.success_dialog_content);
            contentView.setText(content);

            mHandlerTwo.sendEmptyMessageDelayed(CHECK_ECG_DATA, 2000);


//            final Timer t = new Timer();
//            t.schedule(new TimerTask() {
//                public void run() {
//                    try {
//                        t.cancel();
//
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//            }, 2000);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

