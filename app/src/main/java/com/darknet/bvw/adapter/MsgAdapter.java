package com.darknet.bvw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.darknet.bvw.R;
import com.darknet.bvw.model.response.TradeListResponse;

import java.util.ArrayList;
import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    List<TradeListResponse.TradeListModel> listVal = new ArrayList<>();

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public MsgAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_trade_list, parent, false);
        MsgViewHolder viewHolder = new MsgViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof MsgViewHolder){

            TradeListResponse.TradeListModel tradeListModel = listVal.get(position);

            String addressVals = tradeListModel.getTo_address();
            StringBuilder sb = new StringBuilder();
            if(addressVals!=null){
                sb.append(addressVals.substring(0,5));
                sb.append("...");
                sb.append(addressVals.substring((addressVals.length()-5),addressVals.length()));
            }

            String tempAddrss = sb.toString().toUpperCase();

            ((MsgViewHolder)holder).addressView.setText(tempAddrss);
            ((MsgViewHolder)holder).moneyView.setText(tradeListModel.getSend_wrapper()+tradeListModel.getAmount()+tradeListModel.getSymbol());
            ((MsgViewHolder)holder).timeView.setText(tradeListModel.getCreate_at());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.itemClick(tradeListModel);
                }
            });

//
        }

    }

    @Override
    public int getItemCount() {
        return listVal.size();
    }


    public void addData(List<TradeListResponse.TradeListModel> addList) {
        listVal.clear();
        listVal.addAll(addList);
        this.notifyDataSetChanged();
    }

    public static class MsgViewHolder extends RecyclerView.ViewHolder {

        private TextView addressView;
        private TextView moneyView;
        private TextView timeView;

        public MsgViewHolder(View itemView) {
            super(itemView);
            addressView = (TextView) itemView.findViewById(R.id.trade_list_address_view);
            moneyView = (TextView) itemView.findViewById(R.id.trade_list_money_view);
            timeView = (TextView) itemView.findViewById(R.id.trade_list_time);
        }
    }

    public interface OnItemClick{
        void itemClick(TradeListResponse.TradeListModel tradeListModel);
    }

}
