package com.darknet.bvw.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.darknet.bvw.R;
import com.darknet.bvw.activity.MineralInfoActivity;
import com.darknet.bvw.db.Entity.ETHWalletModel;
import com.darknet.bvw.db.WalletDaoUtils;
import com.darknet.bvw.view.dialog.BottomDialog;

public class InputPasswordDialog extends BottomDialog {
    public InputPasswordDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pwd_input_two);

        ImageView closeDialog = (ImageView) findViewById(R.id.close_dialog_two_view);
        EditText contentView = (EditText) findViewById(R.id.common_content);
        Button sureBtn = (Button) findViewById(R.id.wallet_select_create_view);

        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.clickCancel();
                }
                dismiss();
            }
        });


        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    String contentVal = contentView.getText().toString();
                    mListener.clickSure(contentVal);
                }
            }
        });
    }

    OnClickListener mListener;

    public void setListener(OnClickListener listener) {
        mListener = listener;
    }

    public interface OnClickListener{

        void clickSure(String pwd);

        void clickCancel();
    }
}
