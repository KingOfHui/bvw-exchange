package com.darknet.bvw.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;
import android.widget.TextView;

import com.darknet.bvw.R;
import com.darknet.bvw.model.event.FenLieEvent;

import org.greenrobot.eventbus.EventBus;

public class RunningDialog {

    private static final int CHECK_ECG_DATA = 111;

    int temCount = 30;

    //10秒检测一次
    private Handler mHandlerTwo = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == CHECK_ECG_DATA) {

                if (temCount == 0) {
                    if (!((Activity) context).isFinishing() && !((Activity) context).isDestroyed()) {
                        ad.dismiss();
                    }
                    //发送事件，请求订单接口
                    temCount--;
                    EventBus.getDefault().post(new FenLieEvent());
//                    mHandlerTwo.sendEmptyMessageDelayed(CHECK_ECG_DATA, 1000);
                }else{
                    temCount--;
                    contentView.setText(temCount + "S");

                    mHandlerTwo.sendEmptyMessageDelayed(CHECK_ECG_DATA, 1000);
                }
                return true;
            }
            return false;
        }
    });


    AlertDialog ad;
    Context context;
    TextView contentView;

    public void showTips(final Context context, String content) {

        this.context = context;
        try {
            //剪切板Data对象
            ad = new AlertDialog.Builder(context).create();
            ad.show();
            ad.setContentView(R.layout.dialog_running);
            ad.setCanceledOnTouchOutside(false);
            //在弹框上显示软键盘，注意此行需要放到setContentView下面
            ad.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

            contentView = (TextView) ad.findViewById(R.id.run_dialog_content_view);
//            contentView.setText(content);

            mHandlerTwo.sendEmptyMessageDelayed(CHECK_ECG_DATA, 1000);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
