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
 * 作者：created by albert on 2019-06-13 21:00
 * 邮箱：lvzhongdi@icloud.com
 *
 * @param
 **/
public class InDepthAdapter extends BAdapter<DepthResponse.DataBean.AsksBean> {


    private Context context;

    private BigDecimal bigFenMuVal;
    private int mLimit = 5;

    public void setLimit(int limit) {
        mLimit = limit;
        notifyDataSetChanged();
    }

    public InDepthAdapter(Context context, List<DepthResponse.DataBean.AsksBean> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public int getContentView() {
        return R.layout.item_change_depth_in;
    }

    public void setData(List<DepthResponse.DataBean.AsksBean> list,BigDecimal bigVal){
        this.bigFenMuVal = bigVal;
        getList().clear();
        getList().addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void onInitView(View view, int position) {
        DepthResponse.DataBean.AsksBean bean = getList().get(position);
        ProgressBar mPro = view.findViewById(R.id.progress01);
        TextView mAmount = view.findViewById(R.id.item_change_amount);
        TextView mPrice = view.findViewById(R.id.item_change_price);
        TextView mPosition = view.findViewById(R.id.item_change_position);
        mPosition.setText((position+1) + "");


//        Log.e("deapthval","bean.getAmount()1111="+bean.getAmount());

        if(bean.getAmount().contains("--")){
            mAmount.setText(bean.getAmount());
        }else {

//            Log.e("deapthval","bean.getAmount()1="+bean.getAmount());

//            BigDecimal bd2 = new BigDecimal(bean.getAmount());
//            mAmount.setText(bd2.setScale(5, BigDecimal.ROUND_HALF_UP).toPlainString()+"2222");

            mAmount.setText(new BigDecimal(bean.getAmount()).setScale(mLimit, BigDecimal.ROUND_DOWN).toPlainString());

//            mAmount.setText(new BigDecimal(bean.getAmount()).stripTrailingZeros().setScale(5, BigDecimal.ROUND_DOWN).toPlainString());
        }


        if(bean.getPrice().contains("-")){
            mPrice.setText(bean.getPrice());
        }else {
            mPrice.setText(new BigDecimal(bean.getPrice()).setScale(mLimit, BigDecimal.ROUND_DOWN).toPlainString());

//            mPrice.setText(new BigDecimal(bean.getPrice()).stripTrailingZeros().setScale(5, BigDecimal.ROUND_DOWN).toPlainString());
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



//        if (Integer.valueOf(bean.getPrecent()) > 100) {
//            mPro.setProgress(100);
//        } else {
//            mPro.setProgress(Integer.valueOf(bean.getPrecent()));
//        }
    }
}
