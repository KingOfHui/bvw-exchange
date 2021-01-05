package com.darknet.bvw.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.darknet.bvw.R;
import com.darknet.bvw.model.DepthResponse;
import com.darknet.bvw.util.ArithmeticUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class OutAdapter extends BAdapter<DepthResponse.DataBean.BidsBean> {

//    @Override
//    public int getCount() {
//        return getList().size() <= 7 ? getList().size() : 7;
//    }

    private BigDecimal bigFenMuVal;
    private int limit = 5;

    public void setFenMu(BigDecimal muVal) {
        this.bigFenMuVal = muVal;
    }

    public OutAdapter(Context context, List<DepthResponse.DataBean.BidsBean> list) {
        super(context, list);
    }

    public void setLimit(int limit) {
        this.limit = limit;
        notifyDataSetChanged();
    }
    @Override
    public int getContentView() {
        return R.layout.item_change_out;
    }

    @Override
    public void onInitView(View view, int position) {
        DepthResponse.DataBean.BidsBean bean = getList().get(position);
        TextView mPrice = view.findViewById(R.id.item_change_out_price);
        TextView mAmount = view.findViewById(R.id.item_change_out_amount);
        ProgressBar mPro = view.findViewById(R.id.progress02);


        if (!TextUtils.isEmpty(bean.getPrice())) {
            mPrice.setText(new BigDecimal(bean.getPrice()).setScale(limit, BigDecimal.ROUND_DOWN).toPlainString());
        } else {
            mPrice.setText("--");
        }


        try {
            if (!TextUtils.isEmpty(bean.getAmount())) {
                String amount = ArithmeticUtils.divide(bean.getAmount(), "1000", 1);
                if (Double.valueOf(amount) > 1) {
                    mAmount.setText(amount + " k");
                } else {
//                mAmount.setText(ArithmeticUtils.divide(bean.getAmount(), "1", 5));
                    mAmount.setText(new BigDecimal(bean.getAmount()).setScale(5, BigDecimal.ROUND_DOWN).toPlainString());
                }
            } else {
                mAmount.setText("--");
            }
        }catch (Exception e){
            e.printStackTrace();
        }



        try {

            if (bean.getCurrentCount() != null && bigFenMuVal != null) {
                String percentVal = ArithmeticUtils.divide(bean.getCurrentCount(), bigFenMuVal.toPlainString(), 6);
                String lastVal = ArithmeticUtils.multiply(percentVal, "100", 0);
                if (Integer.valueOf(lastVal) > 100) {
                    mPro.setProgress(100);
                } else {

                    if (ArithmeticUtils.compare(lastVal, "1")) {

                        mPro.setProgress(Integer.valueOf(lastVal));
                    } else {
                        mPro.setProgress(Integer.valueOf(0));
                    }
                }
            } else {
                mPro.setProgress(Integer.valueOf(0));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


//        if (!TextUtils.isEmpty(bean.getPrecent())) {
//            try {
//                if (Integer.valueOf(bean.getPrecent()) > 100) {
//                    mPro.setProgress(100);
//                } else {
//                    mPro.setProgress(Integer.valueOf(bean.getPrecent()));
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            mPro.setProgress(0);
//        }


    }
}
