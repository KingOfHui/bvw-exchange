package com.darknet.bvw.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.darknet.bvw.R;
import com.darknet.bvw.model.ChangeDepthResponse;
import com.darknet.bvw.model.DepthResponse;
import com.darknet.bvw.util.ArithmeticUtils;

import java.math.BigDecimal;
import java.util.List;


/**
 * 作者：created by albert on 2019-06-13 21:01
 * 邮箱：lvzhongdi@icloud.com
 *
 * @param
 **/
public class OutDepthAdapter extends BAdapter<DepthResponse.DataBean.BidsBean> {

    private Context context;

    private BigDecimal bigFenMuVal;
    private int mLimit = 5;

    public void setLimit(int limit) {
        mLimit = limit;
        notifyDataSetChanged();
    }
//    private BigDecimal tempCountVal=new BigDecimal(0);

    public OutDepthAdapter(Context context, List<DepthResponse.DataBean.BidsBean> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public int getContentView() {
        return R.layout.item_change_depth_out;
    }

    public void setMoreData(List<DepthResponse.DataBean.BidsBean> list, BigDecimal bigVal) {
        this.bigFenMuVal = bigVal;
        getList().clear();
        getList().addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void onInitView(View view, int position) {
        DepthResponse.DataBean.BidsBean bean = getList().get(position);
        TextView mPrice = view.findViewById(R.id.item_change_out_price);
        TextView mAmount = view.findViewById(R.id.item_change_out_amount);
        TextView mPostion = view.findViewById(R.id.item_change_position_price);

        ProgressBar mPro = view.findViewById(R.id.progress02);

        mPostion.setText((position + 1) + "");
//        mPrice.setText(bean.getAmount());


        if (bean.getAmount().contains("--")) {
            mPrice.setText(bean.getAmount());
        } else {
//            Log.e("deapthval","bean.getAmount()2="+bean.getAmount());
            mPrice.setText(new BigDecimal(bean.getAmount()).setScale(mLimit, BigDecimal.ROUND_DOWN).toPlainString());
//            mPrice.setText(new BigDecimal(bean.getAmount()).stripTrailingZeros().setScale(5, BigDecimal.ROUND_DOWN).toPlainString());
        }


        if (bean.getPrice().contains("-")) {
            mAmount.setText(bean.getPrice());
        } else {
            mAmount.setText(new BigDecimal(bean.getPrice()).setScale(mLimit, BigDecimal.ROUND_DOWN).toPlainString());
//            mAmount.setText(new BigDecimal(bean.getPrice()).stripTrailingZeros().setScale(5, BigDecimal.ROUND_DOWN).toPlainString());
        }

        String percentVal = ArithmeticUtils.divide(bean.getCurrentCount(),bigFenMuVal.toPlainString(),6);

        String lastVal = ArithmeticUtils.multiply(percentVal,"100",0);


        if (Integer.valueOf(lastVal) > 100) {
            mPro.setProgress(100);
        } else {

            if(ArithmeticUtils.compare(lastVal,"1")){

                mPro.setProgress(Integer.valueOf(lastVal));
            }else {
                mPro.setProgress(Integer.valueOf(1));
            }
        }

//        String amount = ArithmeticUtils.divide(bean.getAmount(), "1000", 1);
//        if (Double.valueOf(amount) > 1) {
//            mAmount.setText(amount + " k");
//        } else {
//            mAmount.setText(ArithmeticUtils.divide(bean.getAmount(), "1", 1));
//        }

//
//        if (Integer.valueOf(bean.getPrecent()) > 100) {
//            mPro.setProgress(100);
//        } else {
//            mPro.setProgress(Integer.valueOf(bean.getPrecent()));
//        }


    }
}
