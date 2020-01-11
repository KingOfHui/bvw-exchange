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

import java.util.ArrayList;
import java.util.List;

public class TradeListTwoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    List<TradeListResponse.TradeListModel> listVal = new ArrayList<>();

    public TradeListTwoAdapter(Context context) {
        this.context = context;
    }


    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_trade_list, parent, false);
        TradeViewHolder viewHolder = new TradeViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof TradeViewHolder) {
            TradeListResponse.TradeListModel tradeListModel = listVal.get(position);

            String addressVals = tradeListModel.getTo_address();
            StringBuilder sb = new StringBuilder();
            if(addressVals!=null){
                sb.append(addressVals.substring(0,5));
                sb.append("...");
                sb.append(addressVals.substring((addressVals.length()-5),addressVals.length()));
            }

            String tempAddrss = sb.toString();

            if(position == (listVal.size()-1) ){
                ((TradeViewHolder) holder).lineView.setVisibility(View.INVISIBLE);
            }else {
                ((TradeViewHolder) holder).lineView.setVisibility(View.VISIBLE);
            }

            ((TradeViewHolder) holder).addressView.setText(tempAddrss);
            ((TradeViewHolder) holder).moneyView.setText(tradeListModel.getSend_wrapper()+tradeListModel.getAmount().stripTrailingZeros().toPlainString()+" "+tradeListModel.getSymbol());
            ((TradeViewHolder) holder).timeView.setText(tradeListModel.getCreate_at());


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


    public static class TradeViewHolder extends RecyclerView.ViewHolder {
        private TextView addressView;
        private TextView moneyView;
        private TextView timeView;
        private View lineView;

        public TradeViewHolder(View itemView) {
            super(itemView);

            addressView = (TextView) itemView.findViewById(R.id.trade_list_address_view);
            moneyView = (TextView) itemView.findViewById(R.id.trade_list_money_view);
            timeView = (TextView) itemView.findViewById(R.id.trade_list_time);
            lineView = (View)itemView.findViewById(R.id.trade_list_line_view);
        }
    }

    public interface OnItemClick{
        void itemClick(TradeListResponse.TradeListModel tradeListModel);
    }
}
