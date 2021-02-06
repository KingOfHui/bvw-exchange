package com.darknet.bvw.fund.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.darknet.bvw.R;
import com.darknet.bvw.fund.bean.DefiInvest;
import com.darknet.bvw.view.dialog.CenterBaseDialog;

import java.math.BigDecimal;

public class CancelTipDialog extends CenterBaseDialog {
    private DefiInvest invest;
    public CancelTipDialog(@NonNull Context context, DefiInvest invest) {
        super(context);
        this.invest = invest;
    }

    @Override
    public void configAttrs(WindowManager.LayoutParams params, Window window) {
        super.configAttrs(params, window);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pledge_cancel_tip);
        TextView tvTip = findViewById(R.id.tvTip);
        try {
            if (invest != null) {
                tvTip.setText(String.format(getContext().getString(R.string.cancel_pledge_tip), invest.getProduct_symbol(), invest.getMin_lock_days(),
                        invest.getMin_lock_days(),invest.getMin_lock_release_rate().multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        findViewById(R.id.tvSure).setOnClickListener(view -> {
            if (mListener != null) {
                dismiss();
                mListener.sure();
            }
        });
        findViewById(R.id.ivClose).setOnClickListener(v -> dismiss());
    }

    private OnSureClickListener mListener;

    public void setListener(OnSureClickListener listener) {
        mListener = listener;
    }

    public interface OnSureClickListener{
        void sure();
    }
}
