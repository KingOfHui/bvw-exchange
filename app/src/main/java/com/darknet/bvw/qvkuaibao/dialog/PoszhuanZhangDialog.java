package com.darknet.bvw.qvkuaibao.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.darknet.bvw.R;
import com.darknet.bvw.util.ToastUtils;
import com.darknet.bvw.util.ValueUtil;
import com.darknet.bvw.view.dialog.BottomDialog;
import com.jingui.lib.utils.ViewUtil;

import java.math.BigDecimal;

public class PoszhuanZhangDialog extends BottomDialog {
    private BigDecimal mAmount;
    private TextView mTvAmountTip;
    private EditText mEtAmount;

    public PoszhuanZhangDialog(@NonNull Context context, String amount) {
        super(context);
//        mAmount = new BigDecimal(amount);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pos_zhuan_zhang);
        mTvAmountTip = findViewById(R.id.tvAmountTip);
        mEtAmount = findViewById(R.id.etAmount);
        EditText etPwd = findViewById(R.id.etPwd);
        ViewUtil.addFilter(mEtAmount, ViewUtil.get2NumPoint(6, 1000));
        findViewById(R.id.tvPay).setOnClickListener(view -> {
            String pwd = etPwd.getText().toString().trim();
            String amount = mEtAmount.getText().toString().trim();
            if (TextUtils.isEmpty(pwd)) {
                ToastUtils.showToast(getContext().getString(R.string.empty_pwd));
                return;
            }
            if (mOnPayClickListener != null) {
                mOnPayClickListener.payClick(amount, pwd);
            }
        });
        findViewById(R.id.ivClose).setOnClickListener(view -> dismiss());
    }

    private OnPayClickListener mOnPayClickListener;

    public PoszhuanZhangDialog setIsIn(boolean isIn) {
        mTvAmountTip.setText(isIn ? getContext().getString(R.string.transfer_in_number) : getContext().getString(R.string.transfer_out_number));
        mEtAmount.setHint(isIn ? getContext().getString(R.string.input_in_number) : getContext().getString(R.string.input_out_number));
        return this;
    }

    public PoszhuanZhangDialog setOnPayClickListener(OnPayClickListener onPayClickListener) {
        mOnPayClickListener = onPayClickListener;
        return this;
    }

    public interface OnPayClickListener {
        void payClick(String amount, String pwd);
    }
}
