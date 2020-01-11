package com.darknet.bvw.view;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.darknet.bvw.R;

public class UpdateDialog {
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
    private UpdateItemClick updateItemClick;

    public UpdateDialog(UpdateItemClick updateItemClick) {
        this.updateItemClick = updateItemClick;
    }

    public void showTips(final Context context, String content) {

        this.context = context;
        try {
            //剪切板Data对象
            ad = new AlertDialog.Builder(context).create();
            ad.show();
            ad.setContentView(R.layout.dialog_update_view);
            ad.setCanceledOnTouchOutside(false);
            //在弹框上显示软键盘，注意此行需要放到setContentView下面
            ad.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

            Button oneClickView = (Button) ad.findViewById(R.id.update_dialog_one_click_btn);

            TextView twoClickView = (TextView) ad.findViewById(R.id.update_dialog_two_click_btn);


            oneClickView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateItemClick.updateClick();
                    ad.dismiss();
                }
            });

            twoClickView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateItemClick.dismissClick();
                    ad.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public interface UpdateItemClick{
        void updateClick();
        void dismissClick();
    }

}

