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
import com.darknet.bvw.model.response.SuanLiResponse;

import java.util.ArrayList;
import java.util.List;

public class SuanLiListItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    List<SuanLiResponse.SunLiItemModel> listVal = new ArrayList<>();

    public SuanLiListItemAdapter(Context context) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.suanli_list_item, parent, false);
        FenLieViewHolder viewHolder = new FenLieViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof FenLieViewHolder) {
            SuanLiResponse.SunLiItemModel tradeListModel = listVal.get(position);

            ((FenLieViewHolder) holder).timeView.setText(tradeListModel.getDate()+" ");
            ((FenLieViewHolder) holder).oneView.setText(tradeListModel.getHoldPower().stripTrailingZeros().toPlainString()+"kH/S");
            ((FenLieViewHolder) holder).twoView.setText(tradeListModel.getAdditionTimePower().stripTrailingZeros().toPlainString()+"kH/S");
            ((FenLieViewHolder) holder).threeView.setText(tradeListModel.getSignPower().stripTrailingZeros().toPlainString()+"kH/S");
            ((FenLieViewHolder) holder).fourView.setText(tradeListModel.getLevelPower().stripTrailingZeros().toPlainString()+"kH/S");
            ((FenLieViewHolder) holder).fiveView.setText(tradeListModel.getInviterPower().stripTrailingZeros().toPlainString()+"kH/S");
            ((FenLieViewHolder) holder).sixView.setText(tradeListModel.getBidPower().stripTrailingZeros().toPlainString()+"kH/S");
            ((FenLieViewHolder) holder).bomOneView.setText(tradeListModel.getDayPower().stripTrailingZeros().toPlainString() +"kH/S");
            ((FenLieViewHolder) holder).bomTwoView.setText(tradeListModel.getDayBonus().stripTrailingZeros().toPlainString() +"BTW");
        }

    }

    @Override
    public int getItemCount() {
        return listVal.size();
    }


    public void addData(List<SuanLiResponse.SunLiItemModel> datas) {
        listVal.clear();
        listVal.addAll(datas);
    }

    public void addMoreData(List<SuanLiResponse.SunLiItemModel> datas) {
        listVal.addAll(datas);
        notifyDataSetChanged();
    }


    public static class FenLieViewHolder extends RecyclerView.ViewHolder {
        private TextView timeView;
        private TextView oneView;
        private TextView twoView;
        private TextView threeView;
        private TextView fourView;
        private TextView fiveView;
        private TextView sixView;
        private TextView bomOneView;
        private TextView bomTwoView;

        public FenLieViewHolder(View itemView) {
            super(itemView);

            timeView = (TextView) itemView.findViewById(R.id.sunli_item_time_view);
            oneView = (TextView) itemView.findViewById(R.id.suanli_item_one_view);
            twoView = (TextView) itemView.findViewById(R.id.suanli_item_two_view);
            threeView = (TextView) itemView.findViewById(R.id.suanli_item_three_view);
            fourView = (TextView) itemView.findViewById(R.id.suanli_item_four_view);
            fiveView = (TextView) itemView.findViewById(R.id.suanli_item_five_view);
            sixView = (TextView) itemView.findViewById(R.id.suanli_item_six_view);
            bomOneView = (TextView) itemView.findViewById(R.id.suanli_item_bom_one_view);
            bomTwoView = (TextView) itemView.findViewById(R.id.suanli_item_bom_two_view);
        }
    }

//    public interface OnItemClick{
//        void itemClick(TradeListResponse.TradeListModel tradeListModel);
//    }
}
