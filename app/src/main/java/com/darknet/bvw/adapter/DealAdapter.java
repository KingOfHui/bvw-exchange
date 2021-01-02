package com.darknet.bvw.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.darknet.bvw.R;
import com.darknet.bvw.model.response.JiaoYiResponse;
import com.darknet.bvw.util.language.SPUtil;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

public class DealAdapter extends BAdapter<JiaoYiResponse.JiaoYiData> {


    private Context context;

    public DealAdapter(Context context, List<JiaoYiResponse.JiaoYiData> list) {
        super(context, list);
        this.context = context;
    }

    public void addAllJiaoYiData(List<JiaoYiResponse.JiaoYiData> list) {
        getList().clear();
        getList().addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public int getContentView() {
        return R.layout.item_deal;
    }

    @Override
    public void onInitView(View view, int position) {

        JiaoYiResponse.JiaoYiData jiaoYiModel = getList().get(position);

        TextView timeView = view.findViewById(R.id.deal_item_time_view);
        TextView statusView = view.findViewById(R.id.deal_item_status_view);
        TextView priceView = view.findViewById(R.id.deal_item_price_view);
        TextView showView = view.findViewById(R.id.deal_item_show_view);

        Date date = new Date(jiaoYiModel.getTime());
        SimpleDateFormat simpleFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        timeView.setText(simpleFormatter.format(date));

        if (jiaoYiModel.getDirection() == 1) {
            statusView.setText(context.getResources().getString(R.string.deal_type_sell_sign));
            statusView.setTextColor(context.getResources().getColor(R.color._FC6767));
//            int lanType = SPUtil.getInstance(context).getSelectLanguage();
//
//            if (lanType == 0) {
//                statusView.setText(context.getResources().getString(R.string.deal_type_sell_sign));
//            }  else {
//
//            }

        } else {
            statusView.setText(context.getResources().getString(R.string.deal_type_buy_sign));
            statusView.setTextColor(context.getResources().getColor(R.color._01FCDA));
        }

        priceView.setText(jiaoYiModel.getPrice());
        showView.setText(jiaoYiModel.getAmount().setScale(4, BigDecimal.ROUND_DOWN).toPlainString());


    }
}
