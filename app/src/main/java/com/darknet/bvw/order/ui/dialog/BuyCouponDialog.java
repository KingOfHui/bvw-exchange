package com.darknet.bvw.order.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.darknet.bvw.R;
import com.darknet.bvw.order.bean.CouponBean;
import com.darknet.bvw.view.dialog.CenterBaseDialog;

public class BuyCouponDialog extends CenterBaseDialog {

    private CouponBean mCouponBean;

    public BuyCouponDialog(@NonNull Context context, CouponBean couponBean) {
        super(context);
        mCouponBean = couponBean;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_coupon_buy);
        TextView tvValueTitle = findViewById(R.id.tvValueTip);
        TextView tvCanBuyUse = findViewById(R.id.tvCanBuy);
        TextView tvValue = findViewById(R.id.tvValue);
        TextView tvBuy = findViewById(R.id.tvBuy);
        if (mCouponBean != null) {
            tvValueTitle.setText(String.format(getContext().getString(R.string.buy_coupon_title), mCouponBean.getDiscount()));
            tvCanBuyUse.setText(String.format(getContext().getString(R.string.buy_can_use_biw), mCouponBean.getPrice()));
            tvValue.setText(String.valueOf(mCouponBean.getDiscount()));
            tvBuy.setOnClickListener(view -> {
                if (mListener != null) {
                    mListener.buy();
                }
            });
        }

    }

    private OnClickBuyListener mListener;

    public BuyCouponDialog setListener(OnClickBuyListener listener) {
        mListener = listener;
        return this;
    }

    public interface OnClickBuyListener {
        void buy();
    }
}
