package com.darknet.bvw.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.BidIntroActivity;

public class BidDialogView {
    public void showTips(final Context context,String contnetVal) {

        //剪切板Data对象

        final AlertDialog ad = new AlertDialog.Builder(context).create();
        ad.show();
        ad.setContentView(R.layout.bid_dialog_view);
        ad.setCanceledOnTouchOutside(true);
        //在弹框上显示软键盘，注意此行需要放到setContentView下面
        ad.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        TextView contentView = (TextView) ad.findViewById(R.id.dialog_fail_view);
        LinearLayout bidLayout = (LinearLayout)ad.findViewById(R.id.bid_dialog_sure_layout);

        contentView.setText(contnetVal);

        bidLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bidIntent =new Intent(context, BidIntroActivity.class);
                context.startActivity(bidIntent);
                ad.dismiss();
            }
        });


//        final Timer t = new Timer();
//        t.schedule(new TimerTask() {
//            public void run() {
////                ((BaseActivity) context).finish();
//                ad.dismiss();
//                t.cancel();
//            }
//        }, 2000);



    }

}

