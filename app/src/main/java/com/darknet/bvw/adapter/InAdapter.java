package com.darknet.bvw.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
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

    private BigDecimal bigFenMuVal;


    public void setFenMu(BigDecimal muVal){
        this.bigFenMuVal = muVal;
    }



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
            mPrice.setText(new BigDecimal(bean.getPrice()).setScale(5, BigDecimal.ROUND_DOWN).toPlainString());
        } else {
            mPrice.setText("-");
        }

        try {
            if (!TextUtils.isEmpty(bean.getAmount())) {
                String amount = ArithmeticUtils.divide(bean.getAmount(), "1000", 2);
                if (Double.valueOf(amount) > 1) {
                    mAmount.setText(amount + " k");
                } else {
//                mAmount.setText(ArithmeticUtils.divide(bean.getAmount(), "1", 5));

                    Log.e("deapthval","bean.getAmount()="+bean.getAmount());

                    mAmount.setText(new BigDecimal(bean.getAmount()).setScale(5, BigDecimal.ROUND_DOWN).toPlainString());
                }
            } else {
                mAmount.setText("-");
            }
        }catch (Exception e){
            e.printStackTrace();
        }




        try {

            if(bean.getCurrentCount() != null && bigFenMuVal != null){
                String percentVal = ArithmeticUtils.divide(bean.getCurrentCount(),bigFenMuVal.toPlainString(),6);
                String lastVal = ArithmeticUtils.multiply(percentVal,"100",0);
                if (Integer.valueOf(lastVal) > 100) {
                    mPro.setProgress(100);
                } else {

                    if(ArithmeticUtils.compare(lastVal,"1")){

                        mPro.setProgress(Integer.valueOf(lastVal));
                    }else {
                        mPro.setProgress(Integer.valueOf(0));
                    }
                }
            }else {
                mPro.setProgress(Integer.valueOf(0));
            }

        }catch (Exception e){
            e.printStackTrace();
        }





//        if (!TextUtils.isEmpty(bean.getPrice())) {
//           try {
//               if (Integer.valueOf(bean.getPrecent()) > 100) {
//                   mPro.setProgress(100);
//               } else {
//                   mPro.setProgress(Integer.valueOf(bean.getPrecent()));
//               }
//           }catch (Exception e){
//
//           }
//        } else {
//            mPro.setProgress(0);
//        }

    }
}
