package com.darknet.bvw.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.darknet.bvw.R;
import com.darknet.bvw.model.ChangeDepthResponse;
import com.darknet.bvw.util.ArithmeticUtils;

import java.util.List;


/**
 * 作者：created by albert on 2019-06-13 21:00
 * 邮箱：lvzhongdi@icloud.com
 *
 * @param
 **/
public class InDepthAdapter extends BAdapter<ChangeDepthResponse.AsksBean> {


    public InDepthAdapter(Context context, List<ChangeDepthResponse.AsksBean> list) {
        super(context, list);
    }

    @Override
    public int getContentView() {
        return R.layout.item_change_depth_in;
    }

    @Override
    public void onInitView(View view, int position) {
        ChangeDepthResponse.AsksBean bean = getList().get(position);
        ProgressBar mPro = view.findViewById(R.id.progress01);
        TextView mAmount = view.findViewById(R.id.item_change_amount);
        TextView mPrice = view.findViewById(R.id.item_change_price);
        TextView mPosition = view.findViewById(R.id.item_change_position);
        mPosition.setText(position + "");
        String amount = ArithmeticUtils.divide(bean.getAmount(), "1000", 2);
        if (Double.valueOf(amount) > 1) {
            mAmount.setText(amount + " k");
        } else {
            mAmount.setText(ArithmeticUtils.divide(bean.getAmount(), "1", 1));
        }
        mPrice.setText(bean.getPrice());
        if (Integer.valueOf(bean.getPrecent()) > 100) {
            mPro.setProgress(100);
        } else {
            mPro.setProgress(Integer.valueOf(bean.getPrecent()));
        }
    }
}
