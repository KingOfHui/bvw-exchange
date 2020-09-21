package com.darknet.bvw.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.darknet.bvw.R;

public class CommonInputPwdDialogView {
    public void showTips(final Context context, final OnCloseListener listener) {

        //剪切板Data对象

        final AlertDialog ad = new AlertDialog.Builder(context).create();
        ad.show();
        ad.setContentView(R.layout.dialog_pwd_input);
        ad.setCanceledOnTouchOutside(false);
        //在弹框上显示软键盘，注意此行需要放到setContentView下面
        ad.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        TextView sureView = (TextView) ad.findViewById(R.id.common_sure);
        TextView cancleView = (TextView) ad.findViewById(R.id.common_cancel);
        EditText contentView = (EditText) ad.findViewById(R.id.common_content);


        sureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contentView!=null){
                    InputMethodManager imm = ( InputMethodManager ) contentView.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );
                    if ( imm.isActive( ) ) {
                        imm.hideSoftInputFromWindow( contentView.getApplicationWindowToken( ) , 0 );
                    }
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                ad.dismiss();
                String content = contentView.getText().toString().trim();
                listener.sureClick(content);

                    }
                },200);
            }
        });

        cancleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.dismiss();
            }
        });
    }

    public interface OnCloseListener {
        void closeClick();

        void sureClick(String contentVal);
    }
}
