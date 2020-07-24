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
import com.darknet.bvw.model.response.FenLieOrderResponse;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class FenLieOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    List<FenLieOrderResponse.FLOrderItemModel> listVal = new ArrayList<>();

    public FenLieOrderAdapter(Context context) {
        this.context = context;
    }


//    private OnItemClick onItemClick;
//
//    public void setOnItemClick(OnItemClick onItemClick) {
//        this.onItemClick = onItemClick;
//    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dingdan_list_item, parent, false);
        FenLieViewHolder viewHolder = new FenLieViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof FenLieViewHolder) {
            FenLieOrderResponse.FLOrderItemModel flOrderItemModel = listVal.get(position);

            if (flOrderItemModel.getSymbol() == null || flOrderItemModel.getSymbol().trim().length() == 0) {
                ((FenLieViewHolder) holder).itemOneView.setText("");
            } else {
                ((FenLieViewHolder) holder).itemOneView.setText(flOrderItemModel.getSymbol());
            }


//            ((FenLieViewHolder) holder).itemTwoView.setText(flOrderItemModel.getBack_rate());

            NumberFormat numberFormat = NumberFormat.getInstance();
            // 设置精确到小数点后2位
            numberFormat.setMaximumFractionDigits(0);
            float tempFloat = Float.parseFloat(flOrderItemModel.getBack_rate());
            String result = numberFormat.format(tempFloat * 100);
            ((FenLieViewHolder) holder).itemTwoView.setText(result+"%");

            BigDecimal a = new BigDecimal(flOrderItemModel.getBack_rate());
            BigDecimal b = new BigDecimal("1");
            int compareResult = a.compareTo(b);

            if (compareResult == -1) {
                ((FenLieViewHolder) holder).itemSignView.setText("lose");
                ((FenLieViewHolder) holder).itemSignView.setTextColor(context.getResources().getColor(R.color.white));
                ((FenLieViewHolder) holder).itemSignView.setBackgroundResource(R.drawable.red_bg);
            }else {
                ((FenLieViewHolder) holder).itemSignView.setText("win");
                ((FenLieViewHolder) holder).itemSignView.setBackgroundResource(R.drawable.green_bg);
            }

//            if (flOrderItemModel.getBack_rate().equals("1.12")) {
//                ((FenLieViewHolder) holder).itemSignView.setText("wind");
//                ((FenLieViewHolder) holder).itemSignView.setBackgroundResource(R.drawable.green_bg);
//            } else {
//                ((FenLieViewHolder) holder).itemSignView.setText("lose");
//                ((FenLieViewHolder) holder).itemSignView.setBackgroundResource(R.drawable.red_bg);
//            }


            //分裂比例 如 start_bvw = end_bvw 就取 start_bvw 如果不相等就展示区间 start_bvw ~ end_bvw
            ((FenLieViewHolder) holder).itemMiddleOneView.setText("1：" + new BigDecimal(flOrderItemModel.getStart_bvw()).stripTrailingZeros().setScale(0, BigDecimal.ROUND_DOWN).toPlainString());

            ((FenLieViewHolder) holder).itemMiddleThreeView.setText(flOrderItemModel.getSymbol_amount().stripTrailingZeros().setScale(1, BigDecimal.ROUND_DOWN).toPlainString()+"");

            ((FenLieViewHolder) holder).itemMiddleTwoView.setText(flOrderItemModel.getBack_bvw().stripTrailingZeros().setScale(0, BigDecimal.ROUND_DOWN).toPlainString()+"");
            ((FenLieViewHolder) holder).itemMiddleFourType.setText("（"+flOrderItemModel.getSymbol()+"）");

//            ((FenLieViewHolder) holder).itemMiddleTwoView.setText(tradeListModel.getAmount().stripTrailingZeros().toPlainString() + tradeListModel.getSymbol());
//
//            ((FenLieViewHolder) holder).itemMiddleThreeView.setText(tradeListModel.getAmount().stripTrailingZeros().toPlainString() + tradeListModel.getSymbol());
//            ((FenLieViewHolder) holder).itemMiddleFourView.setText(tradeListModel.getAmount().stripTrailingZeros().toPlainString() + tradeListModel.getSymbol());

            ((FenLieViewHolder) holder).itemBomOneView.setText(flOrderItemModel.getCreate_at());

        }
    }

    @Override
    public int getItemCount() {
        return listVal.size();
    }


    public void addData(List<FenLieOrderResponse.FLOrderItemModel> datas) {
        listVal.clear();
        listVal.addAll(datas);
    }

    public void addMoreData(List<FenLieOrderResponse.FLOrderItemModel> datas) {
        listVal.addAll(datas);
        notifyDataSetChanged();
    }


    public static class FenLieViewHolder extends RecyclerView.ViewHolder {
        private TextView itemOneView;
        private TextView itemTwoView;
        private TextView itemSignView;

        private TextView itemMiddleOneView;
        private TextView itemMiddleTwoView;
        private TextView itemMiddleThreeView;
        private TextView itemMiddleFourView;

        private TextView itemMiddleFourType;
        private TextView itemBomOneView;


        public FenLieViewHolder(View itemView) {
            super(itemView);
            itemOneView = (TextView) itemView.findViewById(R.id.dingdan_item_one);
            itemTwoView = (TextView) itemView.findViewById(R.id.dingdan_item_two);
            itemSignView = (TextView) itemView.findViewById(R.id.dingdan_list_item_sign);

            itemMiddleOneView = (TextView) itemView.findViewById(R.id.dingdan_middle_item_one);
            itemMiddleTwoView = (TextView) itemView.findViewById(R.id.dingdan_middle_item_two);
            itemMiddleThreeView = (TextView) itemView.findViewById(R.id.dingdan_middle_item_three);
            itemMiddleFourView = (TextView) itemView.findViewById(R.id.dingdan_middle_item_four);
            itemMiddleFourType = (TextView)itemView.findViewById(R.id.dingdan_middle_item_four_type);
            itemBomOneView = (TextView) itemView.findViewById(R.id.dingdan_bom_item_one);
        }
    }

//    public interface OnItemClick{
//        void itemClick(TradeListResponse.TradeListModel tradeListModel);
//    }
}
