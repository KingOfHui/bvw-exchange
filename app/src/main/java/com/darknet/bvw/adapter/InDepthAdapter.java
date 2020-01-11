package com.darknet.bvw.adapter;

import android.content.Context;
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


    public InDepthAdapter(Context context, List<DepthResponse.DataBean.AsksBean> list) {
        super(context, list);
    }

    @Override
    public int getContentView() {
        return R.layout.item_change_depth_in;
    }

    public void setData(List<DepthResponse.DataBean.AsksBean> list){
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
//        String amount = ArithmeticUtils.divide(bean.getAmount(), "1000", 2);
//        if (Double.valueOf(amount) > 1) {
//            mAmount.setText(amount + " k");
//        } else {
//            mAmount.setText(ArithmeticUtils.divide(bean.getAmount(), "1", 1));
//        }

//        mAmount.setText(bean.getAmount());

//        mPrice.setText(bean.getPrice());


        if(bean.getAmount().contains("-")){
            mAmount.setText(bean.getAmount());
        }else {
            mAmount.setText(new BigDecimal(bean.getAmount()).stripTrailingZeros().setScale(5, BigDecimal.ROUND_DOWN).toPlainString());
        }


        if(bean.getPrice().contains("-")){
            mPrice.setText(bean.getPrice());
        }else {
            mPrice.setText(new BigDecimal(bean.getPrice()).stripTrailingZeros().setScale(5, BigDecimal.ROUND_DOWN).toPlainString());
        }

//        if (Integer.valueOf(bean.getPrecent()) > 100) {
//            mPro.setProgress(100);
//        } else {
//            mPro.setProgress(Integer.valueOf(bean.getPrecent()));
//        }
    }
}
