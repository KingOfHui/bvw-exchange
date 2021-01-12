package com.darknet.bvw.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.darknet.bvw.R;
import com.darknet.bvw.model.response.TradeListResponse;
import com.darknet.bvw.model.response.WeiTuoHisResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class WeiTuoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    List<WeiTuoHisResponse.WeiHisModel> listVal = new ArrayList<>();

    public WeiTuoAdapter(Context context) {
        this.context = context;
    }


    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_weituo, parent, false);
        MsgViewHolder viewHolder = new MsgViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MsgViewHolder) {

            WeiTuoHisResponse.WeiHisModel weiHisModel = listVal.get(position);


            if (weiHisModel.getDirection() == 0) {
                ((MsgViewHolder) holder).buyOrSalView.setText(context.getString(R.string.trade_trade_buy));
            } else {
                ((MsgViewHolder) holder).buyOrSalView.setText(context.getString(R.string.trade_trade_sell));
            }

            try {
                if (weiHisModel.getMarket_id() != null) {
                    ((MsgViewHolder) holder).moneyTypeView.setText(" " + weiHisModel.getTrade_symbol());
                    ((MsgViewHolder) holder).priceTypeOneView.setText(" " + weiHisModel.getQuote_symbol());
                    ((MsgViewHolder) holder).priceTypeTwoView.setText(" " + weiHisModel.getTrade_symbol());
                    ((MsgViewHolder) holder).priceTypeThreeView.setText(" " + weiHisModel.getQuote_symbol());
                } else {
                    ((MsgViewHolder) holder).moneyTypeView.setText(" " + "BIW");
                    ((MsgViewHolder) holder).priceTypeOneView.setText(" " + "USDT");
                    ((MsgViewHolder) holder).priceTypeTwoView.setText(" " + "USDT");
                    ((MsgViewHolder) holder).priceTypeThreeView.setText(" " + "USDT");
                }
            } catch (Exception e) {
                e.printStackTrace();
                ((MsgViewHolder) holder).moneyTypeView.setText(" " + "BIW");
            }


            if (weiHisModel.getState().equalsIgnoreCase("0")) {
                ((MsgViewHolder) holder).weituoStateView.setText(context.getString(R.string.in_transation));
                ((MsgViewHolder) holder).weituoStateView.setTextColor(context.getResources().getColor(R.color._01FCDA));
                ((MsgViewHolder) holder).createTimeView.setText(weiHisModel.getTime());
            } else if (weiHisModel.getState().equalsIgnoreCase("1")) {
                ((MsgViewHolder) holder).createTimeView.setText(weiHisModel.getCompleted_time());
                ((MsgViewHolder) holder).weituoStateView.setText(context.getString(R.string.completed));
                ((MsgViewHolder) holder).weituoStateView.setTextColor(context.getResources().getColor(R.color._01FCDA));
            } else if (weiHisModel.getState().equalsIgnoreCase("2")) {
                ((MsgViewHolder) holder).createTimeView.setText(weiHisModel.getCanceled_time());
                ((MsgViewHolder) holder).weituoStateView.setText(context.getString(R.string.cancelled));
                ((MsgViewHolder) holder).weituoStateView.setTextColor(context.getResources().getColor(R.color.red7));
            } else if (weiHisModel.getState().equalsIgnoreCase("3")) {
                ((MsgViewHolder) holder).createTimeView.setText(weiHisModel.getTime());
                ((MsgViewHolder) holder).weituoStateView.setText(context.getString(R.string.timeout));
                ((MsgViewHolder) holder).weituoStateView.setTextColor(context.getResources().getColor(R.color._01FCDA));
            } else if (weiHisModel.getState().equalsIgnoreCase("4")) {
                ((MsgViewHolder) holder).createTimeView.setText(weiHisModel.getTime());
                ((MsgViewHolder) holder).weituoStateView.setText(R.string.part_of_transation);
                ((MsgViewHolder) holder).weituoStateView.setTextColor(context.getResources().getColor(R.color._01FCDA));
            }


            ((MsgViewHolder) holder).moneyNumView.setText(weiHisModel.getPrice().stripTrailingZeros().toPlainString());


            try {
                if (weiHisModel.getTraded_amount().compareTo(BigDecimal.ZERO) == 0) {
                    ((MsgViewHolder) holder).numView.setText("0");
                } else {
                    ((MsgViewHolder) holder).numView.setText(weiHisModel.getTraded_amount().stripTrailingZeros().toPlainString());
                }


                if (weiHisModel.getTurnover().compareTo(BigDecimal.ZERO) == 0) {
                    ((MsgViewHolder) holder).totalMoneyView.setText("0");
                } else {
                    ((MsgViewHolder) holder).totalMoneyView.setText(weiHisModel.getTurnover().stripTrailingZeros().toPlainString());
                }
            }catch (Exception e){
                e.printStackTrace();
            }






//            TradeListResponse.TradeListModel tradeListModel = listVal.get(position);
//
////            Log.e("accountsign","model="+tradeListModel.toString());
//
//            ((MsgViewHolder) holder).titleView.setText(tradeListModel.getAmount().stripTrailingZeros().toPlainString() + tradeListModel.getSymbol());
//
//            try {
//                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//                // 格式化时间 2018-07-17
//                Date date = format.parse(tradeListModel.getCreate_at());
//                ((MsgViewHolder) holder).timeView.setText(format.format(date));
//            }catch (Exception e){
//                e.printStackTrace();
//                ((MsgViewHolder) holder).timeView.setText("");
//            }
//
//
//            ((MsgViewHolder) holder).receiveView.setText(context.getResources().getString(R.string.msg_receive_sign) + tradeListModel.getTo_address());
//
//
//            if(tradeListModel.getType().equals("3")){
//
//                if(tradeListModel.getPay_status() != null){
//                    switch (tradeListModel.getPay_status()){
//                        case "waiting":
//                            ((MsgViewHolder) holder).txtZzStatus.setText(context.getResources().getString(R.string.send_trade_state_ing));
////                    ((MsgViewHolder) holder).txtZzStatus.setText("转账中");
//                            ((MsgViewHolder) holder).txtZzStatus.setTextColor(context.getColor(R.color.lightBlue));
//                            break;
//                        case "pending":
//                            ((MsgViewHolder) holder).txtZzStatus.setText(context.getResources().getString(R.string.send_trade_state_ing));
////                    ((MsgViewHolder) holder).txtZzStatus.setText("转账中");
//                            ((MsgViewHolder) holder).txtZzStatus.setTextColor(context.getColor(R.color.lightBlue));
//                            break;
//                        case "confirm":
//                            ((MsgViewHolder) holder).txtZzStatus.setText(context.getResources().getString(R.string.send_trade_state_succ));
////                    ((MsgViewHolder) holder).txtZzStatus.setText("转账成功");
//                            ((MsgViewHolder) holder).txtZzStatus.setTextColor(context.getColor(R.color.lightBlue));
//                            break;
//                        case "failed":
//                            ((MsgViewHolder) holder).txtZzStatus.setText(context.getResources().getString(R.string.send_trade_state_fail));
////                    ((MsgViewHolder) holder).txtZzStatus.setText("转账失败");
//                            ((MsgViewHolder) holder).txtZzStatus.setTextColor(context.getColor(R.color.colorRed));
//                            break;
//                    }
//                }else {
//                    ((MsgViewHolder) holder).txtZzStatus.setText(context.getResources().getString(R.string.send_trade_state_ing));
////                    ((MsgViewHolder) holder).txtZzStatus.setText("转账中");
//                    ((MsgViewHolder) holder).txtZzStatus.setTextColor(context.getColor(R.color.lightBlue));
//                }
//
//            }else {
//                switch (tradeListModel.getStatus()){
//                    case "pending":
//                        ((MsgViewHolder) holder).txtZzStatus.setText(context.getResources().getString(R.string.send_trade_state_ing));
////                    ((MsgViewHolder) holder).txtZzStatus.setText("转账中");
//                        ((MsgViewHolder) holder).txtZzStatus.setTextColor(context.getColor(R.color.lightBlue));
//                        break;
//                    case "confirm":
//                        ((MsgViewHolder) holder).txtZzStatus.setText(context.getResources().getString(R.string.send_trade_state_succ));
////                    ((MsgViewHolder) holder).txtZzStatus.setText("转账成功");
//                        ((MsgViewHolder) holder).txtZzStatus.setTextColor(context.getColor(R.color.lightBlue));
//                        break;
//                    case "failed":
//                        ((MsgViewHolder) holder).txtZzStatus.setText(context.getResources().getString(R.string.send_trade_state_fail));
////                    ((MsgViewHolder) holder).txtZzStatus.setText("转账失败");
//                        ((MsgViewHolder) holder).txtZzStatus.setTextColor(context.getColor(R.color.colorRed));
//                        break;
//                }
//            }


//            if(position == (listVal.size()-1) ){
//                ((MsgViewHolder) holder).lineView.setVisibility(View.INVISIBLE);
//            }else {
//                ((MsgViewHolder) holder).lineView.setVisibility(View.VISIBLE);
//            }

//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onItemClick.itemClick(new TradeListResponse.TradeListModel());
//                }
//            });
        }
    }

    @Override
    public int getItemCount() {
        return listVal.size();
    }


    public void addData(List<WeiTuoHisResponse.WeiHisModel> datas) {
        listVal.clear();
        listVal.addAll(datas);
    }

    public void addMoreData(List<WeiTuoHisResponse.WeiHisModel> datas) {
        listVal.addAll(datas);
        notifyDataSetChanged();
    }


    public static class MsgViewHolder extends RecyclerView.ViewHolder {
        private TextView buyOrSalView;
        private TextView moneyTypeView;
        private TextView createTimeView;
        private TextView weituoStateView;
        private TextView moneyNumView;
        private TextView numView;
        private TextView totalMoneyView;

        private TextView priceTypeOneView;
        private TextView priceTypeTwoView;
        private TextView priceTypeThreeView;

        public MsgViewHolder(View itemView) {
            super(itemView);

            buyOrSalView = (TextView) itemView.findViewById(R.id.his_money_buy_or_sall_view);
            moneyTypeView = (TextView) itemView.findViewById(R.id.his_money_type_view);
            createTimeView = (TextView) itemView.findViewById(R.id.weituo_item_time);
            weituoStateView = (TextView) itemView.findViewById(R.id.weituo_item_state);
            moneyNumView = (TextView) itemView.findViewById(R.id.his_money_val_view);
            numView = (TextView) itemView.findViewById(R.id.his_num_val_view);
            totalMoneyView = (TextView) itemView.findViewById(R.id.his_total_money_val_view);

            priceTypeOneView = (TextView) itemView.findViewById(R.id.commone_pricet_type_one);
            priceTypeTwoView = (TextView) itemView.findViewById(R.id.commone_pricet_type_two);
            priceTypeThreeView = (TextView) itemView.findViewById(R.id.commone_pricet_type_three);
        }
    }

    public interface OnItemClick {
        void itemClick(TradeListResponse.TradeListModel tradeListModel);
    }
}
