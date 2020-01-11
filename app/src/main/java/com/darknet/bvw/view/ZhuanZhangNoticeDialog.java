package com.darknet.bvw.view;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.darknet.bvw.R;

public class ZhuanZhangNoticeDialog {

//    private static final int CHECK_ECG_DATA = 111;
//    //10秒检测一次
//    private Handler mHandlerTwo = new Handler(new Handler.Callback() {
//        @Override
//        public boolean handleMessage(Message msg) {
//            if (msg.what == CHECK_ECG_DATA) {
//                EventBus.getDefault().post(new PushEvent());
//
//                if (!((Activity) context).isFinishing() && !((Activity) context).isDestroyed()) {
//                    ad.dismiss();
//                }
//                ((Activity) context).finish();
//                return true;
//            }
//            return false;
//        }
//    });


    AlertDialog ad;
    Context context;


    public void showTips(final Context context, String content,DialogClickL dialogClickL) {

        this.context = context;
        try {
            //剪切板Data对象
            ad = new AlertDialog.Builder(context).create();
            ad.show();
            ad.setContentView(R.layout.zhuangzhang_notice_two);
            ad.setCanceledOnTouchOutside(false);
            //在弹框上显示软键盘，注意此行需要放到setContentView下面
            ad.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

            TextView contentView = (TextView) ad.findViewById(R.id.fenlie_fail_two_dialog_view);
//            contentView.setText(content);

            contentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ad.dismiss();
                    dialogClickL.clickView();
                }
            });

//            mHandlerTwo.sendEmptyMessageDelayed(CHECK_ECG_DATA, 2000);


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


    public interface DialogClickL{
        void clickView();
    }


}
