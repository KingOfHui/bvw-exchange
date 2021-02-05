package com.darknet.bvw.fund.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.darknet.bvw.R;
import com.darknet.bvw.util.ToastUtils;
import com.darknet.bvw.view.dialog.BottomDialog;
import com.jingui.lib.utils.ViewUtil;

public class PledgeDialog extends BottomDialog {
    private String amount = "";
    private String symbol = "";
    public PledgeDialog(@NonNull Context context, String amount, String symbol) {
        super(context);
        this.amount = amount;
        this.symbol = symbol;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pledge);
        TextView tvAmount = findViewById(R.id.tvAmount);
        TextView tvSure = findViewById(R.id.tvSure);
        EditText etPwd = findViewById(R.id.etPwd);
        tvAmount.setText(String.format("%s%s", amount, symbol));
        findViewById(R.id.ivClose).setOnClickListener(view -> dismiss());
        tvSure.setOnClickListener(view -> {
            String pwd = etPwd.getText().toString().trim();

            if (TextUtils.isEmpty(pwd)) {
                ToastUtils.showToast(getContext().getString(R.string.empty_pwd));
                return;
            }
            if (mListener != null) {
                mListener.sure(pwd);
            }
        });
    }

    private OnSureClickListener mListener;

    public void setListener(OnSureClickListener listener) {
        mListener = listener;
    }

    public interface OnSureClickListener{
        void sure(String pwd);
    }
}
