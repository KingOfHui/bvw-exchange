package com.darknet.bvw.order.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.darknet.bvw.R;
import com.darknet.bvw.util.ValueUtil;
import com.darknet.bvw.view.dialog.BottomDialog;

import java.math.BigDecimal;

public class PayOrderDialog extends BottomDialog {
    private BigDecimal mAmount;
    public PayOrderDialog(@NonNull Context context, String amount) {
        super(context);
        mAmount = new BigDecimal(amount);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pay_order);
        TextView tvTotal = findViewById(R.id.tvTotalPrice);
        tvTotal.setText(String.format("%s USDT", ValueUtil.stripTrailingZeros(mAmount)));
        findViewById(R.id.tvPay).setOnClickListener(view -> {
            if (mOnPayClickListener != null) {
                mOnPayClickListener.payClick();
            }
        });
        findViewById(R.id.ivClose).setOnClickListener(view -> dismiss());
    }

    private OnPayClickListener mOnPayClickListener;

    public PayOrderDialog setOnPayClickListener(OnPayClickListener onPayClickListener) {
        mOnPayClickListener = onPayClickListener;
        return this;
    }

    public interface OnPayClickListener{
        void payClick();
    }
}
