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

public class InAdapter extends BAdapter<DepthResponse.DataBean.AsksBean> {


//    @Override
//    public int getCount() {
//        return getList().size() <= 7 ? getList().size() : 7;
//    }

    public InAdapter(Context context, List<DepthResponse.DataBean.AsksBean> list) {
        super(context, list);
    }

    @Override
    public int getContentView() {
        return R.layout.item_change_in;
    }

    @Override
    public void onInitView(View view, int position) {
        DepthResponse.DataBean.AsksBean bean = getList().get(position);
        ProgressBar mPro = view.findViewById(R.id.progress01);
        TextView mAmount = view.findViewById(R.id.item_change_amount);
        TextView mPrice = view.findViewById(R.id.item_change_price);

        if (!TextUtils.isEmpty(bean.getPrice())) {
            mPrice.setText(new BigDecimal(bean.getPrice()).setScale(5, RoundingMode.HALF_EVEN).toPlainString());
        } else {
            mPrice.setText("-");
        }
        if (!TextUtils.isEmpty(bean.getAmount())) {
            String amount = ArithmeticUtils.divide(bean.getAmount(), "1000", 2);
            if (Double.valueOf(amount) > 1) {
                mAmount.setText(amount + " k");
            } else {
                mAmount.setText(ArithmeticUtils.divide(bean.getAmount(), "1", 1));
            }
        } else {
            mAmount.setText("-");
        }
        if (!TextUtils.isEmpty(bean.getPrice())) {
           try {
               if (Integer.valueOf(bean.getPrecent()) > 100) {
                   mPro.setProgress(100);
               } else {
                   mPro.setProgress(Integer.valueOf(bean.getPrecent()));
               }
           }catch (Exception e){

           }
        } else {
            mPro.setProgress(0);
        }
    }
}
