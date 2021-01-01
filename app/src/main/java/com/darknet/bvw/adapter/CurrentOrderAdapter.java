package com.darknet.bvw.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.darknet.bvw.R;
import com.darknet.bvw.model.CurrentOrderModel;

import java.util.List;

public class CurrentOrderAdapter extends BAdapter<CurrentOrderModel.DataBean.ItemsBean> {


    private OnCancleListener mListener;


    public void setListener(OnCancleListener listener) {
        mListener = listener;
    }

    public interface OnCancleListener {
        void OnCancle(CurrentOrderModel.DataBean.ItemsBean bean);
    }


    public CurrentOrderAdapter(Context context, List<CurrentOrderModel.DataBean.ItemsBean> list) {
        super(context, list);
    }

    @Override
    public int getContentView() {
        return R.layout.item_current_order;
    }

    @Override
    public void onInitView(View view, int position) {
        TextView mBuySellTV = view.findViewById(R.id.buy_sell_tv);
        TextView mCreateTimeTv = view.findViewById(R.id.my_order_createtime_tv);
        TextView mPriceTv = view.findViewById(R.id.my_order_price_tv);
        TextView mNumberTv = view.findViewById(R.id.my_order_number_tv);
        TextView mTotalTv = view.findViewById(R.id.my_order_total_tv);

        TextView priceType = view.findViewById(R.id.weituo_item_price_view);
        TextView numType = view.findViewById(R.id.weituo_item_num_view);

        LinearLayout cancelLayout = view.findViewById(R.id.cancel_layout);

        CurrentOrderModel.DataBean.ItemsBean bean = getList().get(position);

        if (bean.getDirection() == 0) {
            mBuySellTV.setText(mContext.getString(R.string.trade_trade_buy));
        } else {
            mBuySellTV.setText(mContext.getString(R.string.trade_trade_sell));
        }

        try {
            if(bean.getMarket_id() != null){
                priceType.setText(bean.getMarket_id().split("-")[1]);
                numType.setText(bean.getMarket_id().split("-")[0]);
            }else {
                numType.setText("");
                priceType.setText("");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        mCreateTimeTv.setText(bean.getTime());
        mPriceTv.setText(bean.getPrice().stripTrailingZeros().toPlainString());
        mNumberTv.setText(bean.getAmount().stripTrailingZeros().toPlainString());
        mTotalTv.setText(bean.getPrice().multiply(bean.getAmount()).stripTrailingZeros().toPlainString());


        cancelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnCancle(bean);
            }
        });
    }
}
