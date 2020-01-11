package com.darknet.bvw.view;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.darknet.bvw.R;

public class CommonInputPwdDialogTwoView {
    public void showTips(final Context context, final OnCloseListener listener) {
        //剪切板Data对象
        final AlertDialog ad = new AlertDialog.Builder(context).create();
        ad.show();
        ad.setContentView(R.layout.dialog_pwd_input_two);
        ad.setCanceledOnTouchOutside(false);
        //在弹框上显示软键盘，注意此行需要放到setContentView下面
//        ad.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ad.getWindow().setGravity(Gravity.BOTTOM);

        Button sureView = (Button) ad.findViewById(R.id.wallet_select_create_view);
//        TextView cancleView = (TextView) ad.findViewById(R.id.common_cancel);
        EditText contentView = (EditText) ad.findViewById(R.id.common_content);
        ImageView backView = (ImageView) ad.findViewById(R.id.close_dialog_two_view);


        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        sureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.dismiss();
                String content = contentView.getText().toString().trim();
                listener.sureClick(content);
            }
        });

//        cancleView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ad.dismiss();
//            }
//        });
    }

    public interface OnCloseListener {
        void closeClick();

        void sureClick(String contentVal);
    }
}
