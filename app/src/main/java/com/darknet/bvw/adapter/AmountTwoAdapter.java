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
import com.darknet.bvw.model.ExchangeZcResponse;
import com.darknet.bvw.util.ArithmeticUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AmountTwoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    List<ExchangeZcResponse.EZcModel> listVal = new ArrayList<>();

    public AmountTwoAdapter(Context context) {
        this.context = context;
    }


    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_amount, parent, false);
        MsgViewHolder viewHolder = new MsgViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MsgViewHolder) {
            ExchangeZcResponse.EZcModel tradeListModel = listVal.get(position);

            ((MsgViewHolder) holder).moneyType.setText(tradeListModel.getSymbol());

            String aaa =  ArithmeticUtils.multiply(tradeListModel.getBalance().toPlainString(),"1").toPlainString();

//            ((MsgViewHolder) holder).canUseView.setText(tradeListModel.getBalance().stripTrailingZeros().setScale(5, BigDecimal.ROUND_DOWN).doubleValue()+"");
            ((MsgViewHolder) holder).canUseView.setText(aaa);

            ((MsgViewHolder) holder).dongjieView.setText(tradeListModel.getFrozen_balance().doubleValue()+"");
            ((MsgViewHolder) holder).zheSuanView.setText(tradeListModel.getFrozen_balance().add(tradeListModel.getBalance()).multiply(tradeListModel.getUsd_rate())
                    .setScale(2,BigDecimal.ROUND_DOWN).toPlainString());

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


    public void addData(List<ExchangeZcResponse.EZcModel> datas) {
        listVal.clear();
        listVal.addAll(datas);
    }

    public void addMoreData(List<ExchangeZcResponse.EZcModel> datas) {
        listVal.addAll(datas);
        notifyDataSetChanged();
    }


    public static class MsgViewHolder extends RecyclerView.ViewHolder {
        private TextView moneyType;
        private TextView canUseView;
        private TextView dongjieView;
        private TextView zheSuanView;

        public MsgViewHolder(View itemView) {
            super(itemView);
            moneyType = (TextView) itemView.findViewById(R.id.money_type_item_view);
            canUseView = (TextView) itemView.findViewById(R.id.money_type_item_can_use_view);
            dongjieView = (TextView) itemView.findViewById(R.id.money_type_item_dongjie_view);
            zheSuanView = (TextView) itemView.findViewById(R.id.money_type_item_zhesuan_view);
        }
    }

    public interface OnItemClick{
        void itemClick(ExchangeZcResponse.EZcModel zcModel);
    }
}
