package com.darknet.bvw.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.darknet.bvw.R;
import com.darknet.bvw.model.MoneyModel;
import com.darknet.bvw.view.TypefaceTextView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.darknet.bvw.util.language.LocalManageUtil.getSystemLocale;

public class ZCAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<MoneyModel> list = new ArrayList<>();

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    //    public ZCAdapter(Context context, List<MoneyModel> list) {
//        this.context = context;
//        this.list = list;
//    }

    public ZCAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_money_layout, null);
        MoneyViewHolder viewHolder = new MoneyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MoneyViewHolder) {
            MoneyModel moneyModel = list.get(position);
//        ((ViewHolder)holder).header;
            ((MoneyViewHolder) holder).nameView.setText(moneyModel.getSymbol());

            if (!TextUtils.isEmpty(moneyModel.getIcon())) {
                Uri uri = Uri.parse(moneyModel.getIcon());
                ((MoneyViewHolder) holder).header.setImageURI(uri);
            }

            if (TextUtils.isEmpty(moneyModel.getValue_qty()) || moneyModel.getValue_qty().equals("0") || moneyModel.getValue_qty().equals("0.000000")) {

                ((MoneyViewHolder) holder).numView.setText("0.000000");
                ((MoneyViewHolder) holder).moneyValView.setText("~");
            } else {
                ((MoneyViewHolder) holder).numView.setText(moneyModel.getValue_qty());
//                double priceVal = Double.parseDouble(moneyModel.getPrice());
//                double numVal = Double.parseDouble(moneyModel.getQty());
//                double totalMoney = priceVal * numVal;


                if(new BigDecimal(moneyModel.getValue_usd().toString()).compareTo(BigDecimal.ZERO) == 0){
                    ((MoneyViewHolder) holder).moneyValView.setText("~");
                }else {
                    ((MoneyViewHolder) holder).moneyValView.setText("≈$" + moneyModel.getValue_usd());
                }

                //中英状态注销
//                int lanType = SPUtil.getInstance(context).getSelectLanguage();
//                if(lanType == 0){
//                    if(new BigDecimal(moneyModel.getValue_cny().toString()).compareTo(BigDecimal.ZERO) == 0){
//                        ((MoneyViewHolder) holder).moneyValView.setText("~");
//                    }else {
//                        ((MoneyViewHolder) holder).moneyValView.setText("≈￥" + moneyModel.getValue_cny());
//                    }
//                }else if(lanType == 1){
//
//                    if(new BigDecimal(moneyModel.getValue_usd().toString()).compareTo(BigDecimal.ZERO) == 0){
//                        ((MoneyViewHolder) holder).moneyValView.setText("~");
//                    }else {
//                        ((MoneyViewHolder) holder).moneyValView.setText("≈$" + moneyModel.getValue_usd());
//                    }
//                }else if(lanType == 2){
//                    try {
//
//                        String language = getStystemLanguage();
//
//                        if ("zh".equals(language)) {
//                            if(new BigDecimal(moneyModel.getValue_cny().toString()).compareTo(BigDecimal.ZERO) == 0){
//                                ((MoneyViewHolder) holder).moneyValView.setText("~");
//                            }else {
//                                ((MoneyViewHolder) holder).moneyValView.setText("≈￥" + moneyModel.getValue_cny());
//                            }
//                        } else if ("en".equals(language)) {
//                            if(new BigDecimal(moneyModel.getValue_usd().toString()).compareTo(BigDecimal.ZERO) == 0){
//                                ((MoneyViewHolder) holder).moneyValView.setText("~");
//                            }else {
//                                ((MoneyViewHolder) holder).moneyValView.setText("≈$" + moneyModel.getValue_usd());
//                            }
//                        } else {
//                            if(new BigDecimal(moneyModel.getValue_usd().toString()).compareTo(BigDecimal.ZERO) == 0){
//                                ((MoneyViewHolder) holder).moneyValView.setText("~");
//                            }else {
//                                ((MoneyViewHolder) holder).moneyValView.setText("≈$" + moneyModel.getValue_usd());
//                            }
//                        }
//                    }catch (Exception e){
//                        e.printStackTrace();
//                        if(new BigDecimal(moneyModel.getValue_usd().toString()).compareTo(BigDecimal.ZERO) == 0){
//                            ((MoneyViewHolder) holder).moneyValView.setText("~");
//                        }else {
//                            ((MoneyViewHolder) holder).moneyValView.setText("≈$" + moneyModel.getValue_usd());
//                        }
//                    }
//                }



            }

            if((list.size()-1) == position){
                ((MoneyViewHolder) holder).lineOneView.setVisibility(View.INVISIBLE);
                ((MoneyViewHolder) holder).lineTwoView.setVisibility(View.INVISIBLE);
            }else {
                ((MoneyViewHolder) holder).lineOneView.setVisibility(View.VISIBLE);
                ((MoneyViewHolder) holder).lineTwoView.setVisibility(View.INVISIBLE);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.itemClick(moneyModel);
                }
            });
        }
    }


    private String getStystemLanguage() {
        String language = getSystemLocale(context).getLanguage();
        return language;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<MoneyModel> newData) {
        list.clear();

        list.addAll(newData);
        this.notifyDataSetChanged();
//        if (this.list == null) {
//            this.list = new ArrayList<>();
//            this.list = newData;
//            notifyDataSetChanged();
//        } else {
////            this.list.clear();
////            notifyDataSetChanged();
//            addData(newData);
//        }
    }

//    public void addData(List<MoneyModel> addList) {
//        if (this.list == null) {
//            list = new ArrayList<>();
//        }
////        list.clear();
//        if (addList != null) {
//            for (MoneyModel str : addList) {
//                list.add(str);
//            }
//        }
//        notifyDataSetChanged();
//    }

    public static class MoneyViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView header;
        private TextView numView;
        private TypefaceTextView nameView;
        private TypefaceTextView moneyValView;
        private View lineOneView;
        private View lineTwoView;


        public MoneyViewHolder(View itemView) {
            super(itemView);
            header = (SimpleDraweeView) itemView.findViewById(R.id.money_item_header);
            numView = (TextView) itemView.findViewById(R.id.money_item_num_val);
            nameView = (TypefaceTextView) itemView.findViewById(R.id.money_item_name);
            moneyValView = (TypefaceTextView) itemView.findViewById(R.id.money_item_val);
            lineOneView= (View) itemView.findViewById(R.id.money_item_line_view);
            lineTwoView= (View) itemView.findViewById(R.id.money_item_line_two_view);
        }
    }


    public interface OnItemClick {
        void itemClick(MoneyModel moneyModel);
    }

}
