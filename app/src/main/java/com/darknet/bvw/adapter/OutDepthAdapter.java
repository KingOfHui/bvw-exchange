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
 * 作者：created by albert on 2019-06-13 21:01
 * 邮箱：lvzhongdi@icloud.com
 *
 * @param
 **/
public class OutDepthAdapter extends BAdapter<DepthResponse.DataBean.BidsBean> {


    public OutDepthAdapter(Context context, List<DepthResponse.DataBean.BidsBean> list) {
        super(context, list);
    }

    @Override
    public int getContentView() {
        return R.layout.item_change_depth_out;
    }

    public void setMoreData(List<DepthResponse.DataBean.BidsBean> list) {
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
        mPostion.setText((position+1) + "");
//        mPrice.setText(bean.getAmount());


        if(bean.getAmount().contains("-")){
            mPrice.setText(bean.getAmount());
        }else {
            mPrice.setText(new BigDecimal(bean.getAmount()).stripTrailingZeros().setScale(5, BigDecimal.ROUND_DOWN).toPlainString());
        }


        if(bean.getPrice().contains("-")){
            mAmount.setText(bean.getPrice());
        }else {
            mAmount.setText(new BigDecimal(bean.getPrice()).stripTrailingZeros().setScale(5, BigDecimal.ROUND_DOWN).toPlainString());
        }






//        String amount = ArithmeticUtils.divide(bean.getAmount(), "1000", 1);
//        if (Double.valueOf(amount) > 1) {
//            mAmount.setText(amount + " k");
//        } else {
//            mAmount.setText(ArithmeticUtils.divide(bean.getAmount(), "1", 1));
//        }

//        ProgressBar mPro = view.findViewById(R.id.progress02);
//        if (Integer.valueOf(bean.getPrecent()) > 100) {
//            mPro.setProgress(100);
//        } else {
//            mPro.setProgress(Integer.valueOf(bean.getPrecent()));
//        }


    }
}
