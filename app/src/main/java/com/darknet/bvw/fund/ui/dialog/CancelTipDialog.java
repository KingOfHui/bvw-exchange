package com.darknet.bvw.fund.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.darknet.bvw.R;
import com.darknet.bvw.view.dialog.CenterBaseDialog;

public class CancelTipDialog extends CenterBaseDialog {
    public CancelTipDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void configAttrs(WindowManager.LayoutParams params, Window window) {
        super.configAttrs(params, window);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pledge_cancel_tip);
        findViewById(R.id.tvSure).setOnClickListener(view -> {
            if (mListener != null) {
                mListener.sure();
            }
        });
    }

    private OnSureClickListener mListener;

    public void setListener(OnSureClickListener listener) {
        mListener = listener;
    }

    public interface OnSureClickListener{
        void sure();
    }
}
