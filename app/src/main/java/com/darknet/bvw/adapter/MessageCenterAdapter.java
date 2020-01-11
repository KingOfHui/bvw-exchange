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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageCenterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    List<TradeListResponse.TradeListModel> listVal = new ArrayList<>();

    public MessageCenterAdapter(Context context) {
        this.context = context;
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_msg, parent, false);
        MsgViewHolder viewHolder = new MsgViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MsgViewHolder) {
            TradeListResponse.TradeListModel tradeListModel = listVal.get(position);
            ((MsgViewHolder) holder).titleView.setText(tradeListModel.getAmount().stripTrailingZeros().toPlainString() + tradeListModel.getSymbol());

//            try {
//                ((MsgViewHolder) holder).timeView.setText(tradeListModel.getCreate_at().substring(0,9));
//            }catch (Exception e){
//                e.printStackTrace();
//            }


            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                // 格式化时间 2018-07-17
                Date date = format.parse(tradeListModel.getCreate_at());
                ((MsgViewHolder) holder).timeView.setText(format.format(date));
            }catch (Exception e){
                e.printStackTrace();
                ((MsgViewHolder) holder).timeView.setText("");
            }

//            String addressVals = tradeListModel.getTo_address();
//
//            try {
//
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//
//            StringBuilder sb = new StringBuilder();
//            if (addressVals != null) {
//                sb.append(addressVals.substring(0, 8));
//                sb.append("...");
//                sb.append(addressVals.substring((addressVals.length() - 5), addressVals.length()));
//            }

            ((MsgViewHolder) holder).receiveView.setText(context.getResources().getString(R.string.msg_receive_sign) + tradeListModel.getTo_address());

            if(position == (listVal.size()-1) ){
                ((MsgViewHolder) holder).lineView.setVisibility(View.INVISIBLE);
            }else {
                ((MsgViewHolder) holder).lineView.setVisibility(View.VISIBLE);
            }


            if(tradeListModel.getType().equals("3")){

                if(tradeListModel.getPay_status() != null){
                    switch (tradeListModel.getPay_status()){
                        case "waiting":
                            ((MsgViewHolder) holder).txtZzStatus.setText(context.getResources().getString(R.string.send_trade_state_ing));
//                    ((MsgViewHolder) holder).txtZzStatus.setText("转账中");
                            ((MsgViewHolder) holder).txtZzStatus.setTextColor(context.getColor(R.color.lightBlue));
                            break;
                        case "pending":
                            ((MsgViewHolder) holder).txtZzStatus.setText(context.getResources().getString(R.string.send_trade_state_ing));
//                    ((MsgViewHolder) holder).txtZzStatus.setText("转账中");
                            ((MsgViewHolder) holder).txtZzStatus.setTextColor(context.getColor(R.color.lightBlue));
                            break;
                        case "confirm":
//                    ((MsgViewHolder) holder).txtZzStatus.setText("转账成功");
                            ((MsgViewHolder) holder).txtZzStatus.setText(context.getResources().getString(R.string.send_trade_state_succ));

                            ((MsgViewHolder) holder).txtZzStatus.setTextColor(context.getColor(R.color.lightBlue));
                            break;
                        case "failed":
//                    ((MsgViewHolder) holder).txtZzStatus.setText("转账失败");
                            ((MsgViewHolder) holder).txtZzStatus.setText(context.getResources().getString(R.string.send_trade_state_fail));

                            ((MsgViewHolder) holder).txtZzStatus.setTextColor(context.getColor(R.color.colorRed));
                            break;
                    }
                }else {
                    ((MsgViewHolder) holder).txtZzStatus.setText(context.getResources().getString(R.string.send_trade_state_ing));
                    ((MsgViewHolder) holder).txtZzStatus.setTextColor(context.getColor(R.color.lightBlue));
                }

            }else {
                switch (tradeListModel.getStatus()){
                    case "waiting":
                        ((MsgViewHolder) holder).txtZzStatus.setText(context.getResources().getString(R.string.send_trade_state_ing));
//                    ((MsgViewHolder) holder).txtZzStatus.setText("转账中");
                        ((MsgViewHolder) holder).txtZzStatus.setTextColor(context.getColor(R.color.lightBlue));
                        break;
                    case "pending":
                        ((MsgViewHolder) holder).txtZzStatus.setText(context.getResources().getString(R.string.send_trade_state_ing));
//                    ((MsgViewHolder) holder).txtZzStatus.setText("转账中");
                        ((MsgViewHolder) holder).txtZzStatus.setTextColor(context.getColor(R.color.lightBlue));
                        break;
                    case "confirm":
//                    ((MsgViewHolder) holder).txtZzStatus.setText("转账成功");
                        ((MsgViewHolder) holder).txtZzStatus.setText(context.getResources().getString(R.string.send_trade_state_succ));

                        ((MsgViewHolder) holder).txtZzStatus.setTextColor(context.getColor(R.color.lightBlue));
                        break;
                    case "failed":
//                    ((MsgViewHolder) holder).txtZzStatus.setText("转账失败");
                        ((MsgViewHolder) holder).txtZzStatus.setText(context.getResources().getString(R.string.send_trade_state_fail));

                        ((MsgViewHolder) holder).txtZzStatus.setTextColor(context.getColor(R.color.colorRed));
                        break;
                }
            }


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.itemClick(tradeListModel);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return listVal.size();
    }


    public void addData(List<TradeListResponse.TradeListModel> datas) {
        listVal.clear();
        listVal.addAll(datas);
    }


    public void addMoreData(List<TradeListResponse.TradeListModel> datas) {
        listVal.addAll(datas);
        notifyDataSetChanged();
    }

    public static class MsgViewHolder extends RecyclerView.ViewHolder {
        private TextView titleView;
        private TextView timeView;
        private TextView receiveView;
        private TextView txtZzStatus;
        private View lineView;

        public MsgViewHolder(View itemView) {
            super(itemView);
            titleView = (TextView) itemView.findViewById(R.id.msg_title_view);
            timeView = (TextView) itemView.findViewById(R.id.msg_time_view);
            receiveView = (TextView) itemView.findViewById(R.id.msg_receive_view);
            txtZzStatus = (TextView) itemView.findViewById(R.id.txt_zz_status);
            lineView = (View)itemView.findViewById(R.id.msg_receive_line_view);
        }
    }


    public interface OnItemClick{
        void itemClick(TradeListResponse.TradeListModel tradeListModel);
    }

}
