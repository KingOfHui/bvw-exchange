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

import java.util.ArrayList;
import java.util.List;

public class JiangLiItemTwoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    List<String> listVal = new ArrayList<>();

    public JiangLiItemTwoAdapter(Context context) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_jiangli_content, parent, false);
        JiangLieViewHolder viewHolder = new JiangLieViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof JiangLieViewHolder){
            int positionss = position % listVal.size();
           String tempVal = listVal.get(positionss);
            ((JiangLieViewHolder) holder).positionView.setText(tempVal);
        }

//        if (holder instanceof MsgViewHolder) {
//            TradeListResponse.TradeListModel tradeListModel = listVal.get(position);
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
//            switch (tradeListModel.getStatus()){
//                case "pending":
//                    ((MsgViewHolder) holder).txtZzStatus.setText(context.getResources().getString(R.string.send_trade_state_ing));
////                    ((MsgViewHolder) holder).txtZzStatus.setText("转账中");
//                    ((MsgViewHolder) holder).txtZzStatus.setTextColor(context.getColor(R.color.lightBlue));
//                    break;
//                case "confirm":
//                    ((MsgViewHolder) holder).txtZzStatus.setText(context.getResources().getString(R.string.send_trade_state_succ));
////                    ((MsgViewHolder) holder).txtZzStatus.setText("转账成功");
//                    ((MsgViewHolder) holder).txtZzStatus.setTextColor(context.getColor(R.color.lightBlue));
//                    break;
//                case "failed":
//                    ((MsgViewHolder) holder).txtZzStatus.setText(context.getResources().getString(R.string.send_trade_state_fail));
////                    ((MsgViewHolder) holder).txtZzStatus.setText("转账失败");
//                    ((MsgViewHolder) holder).txtZzStatus.setTextColor(context.getColor(R.color.colorRed));
//                    break;
//            }
//
//            if(position == (listVal.size()-1) ){
//                ((MsgViewHolder) holder).lineView.setVisibility(View.INVISIBLE);
//            }else {
//                ((MsgViewHolder) holder).lineView.setVisibility(View.VISIBLE);
//            }
//
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onItemClick.itemClick(tradeListModel);
//                }
//            });
//        }
//


    }

    @Override
    public int getItemCount() {
//        return listVal.size();
        if (listVal.size() > 5) {
////            展示无限数据
            return Integer.MAX_VALUE;
        } else {
            return listVal.size();
        }
    }


    public void addData(List<String> datas) {
        listVal.clear();
        listVal.addAll(datas);
        notifyDataSetChanged();
    }

    public void addMoreData(List<String> datas) {
        listVal.addAll(datas);
        notifyDataSetChanged();
    }


    public static class JiangLieViewHolder extends RecyclerView.ViewHolder {
        private TextView positionView;
//        private TextView timeView;
//        private TextView receiveView;
//        private TextView txtZzStatus;
//        private View lineView;

        public JiangLieViewHolder(View itemView) {
            super(itemView);
            positionView = (TextView) itemView.findViewById(R.id.jiangli_item_position_view);
//            timeView = (TextView) itemView.findViewById(R.id.msg_time_view);
//            receiveView = (TextView) itemView.findViewById(R.id.msg_receive_view);
//            txtZzStatus = (TextView) itemView.findViewById(R.id.txt_zz_status);
//            lineView = (View) itemView.findViewById(R.id.msg_receive_line_view);
        }
    }

//    public interface OnItemClick {
//        void itemClick(TradeListResponse.TradeListModel tradeListModel);
//    }
}
