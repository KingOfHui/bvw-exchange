package com.darknet.bvw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.darknet.bvw.R;
import com.darknet.bvw.model.response.LuckResponse;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class LuckyItemAdapter extends BaseAdapter {

    private Context context;

    private List<LuckResponse.LuckModel> listVals = new ArrayList<>();

//
//    ItemViewListener itemViewListener;
//
//    public void setItemViewListener(ItemViewListener itemViewListener) {
//        this.itemViewListener = itemViewListener;
//    }

    public LuckyItemAdapter(Context context) {
        this.context = context;
    }


//    public void addItemData(MoneyTypeResponse.MoneyTypeModel friendListModel){
//        listVals.add(friendListModel);
//        notifyDataSetInvalidated();
//    }

    public void addData(List<LuckResponse.LuckModel> vals) {
        listVals.clear();
        listVals.addAll(vals);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listVals.size();
    }

    @Override
    public Object getItem(int i) {
        return listVals.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder vh;

        if (view == null) {
            vh = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_jiangli_content, null);
            vh.positionView = (TextView) view.findViewById(R.id.jiangli_item_position_view);
            vh.addrssView = (TextView) view.findViewById(R.id.jiangli_item_address_view);
            vh.btcView = (TextView) view.findViewById(R.id.jiangli_item_btc_view);
            vh.biliView = (TextView) view.findViewById(R.id.jiangli_item_bili_view);

            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

//        int positionss = i % listVals.size();
        LuckResponse.LuckModel tempVal = listVals.get(i);

        String addressVals = tempVal.getAddress();
        StringBuilder sb = new StringBuilder();
        if (addressVals != null) {
            sb.append(addressVals.substring(0, 5));
            sb.append("...");
            sb.append(addressVals.substring((addressVals.length() - 5), addressVals.length()));
        }

        vh.positionView.setText((i+1)+"");
        vh.addrssView.setText(sb.toString()+"");
        vh.btcView.setText(tempVal.getBonus().stripTrailingZeros().toPlainString() +"BTC");

        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(0);
        String leftResult = numberFormat.format( Float.parseFloat(tempVal.getBonus_rate()) * 100);

        vh.biliView.setText(new BigDecimal(leftResult).stripTrailingZeros().setScale(2, BigDecimal.ROUND_DOWN).doubleValue()+"%");


//        final MoneyTypeResponse.MoneyTypeModel moneyTypeModel = listVals.get(i);
//        vh.nameView.setText(moneyTypeModel.getSymbol());
//        vh.nameView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                itemViewListener.itemClick(moneyTypeModel);
//            }
//        });
        return view;
    }


    public final class ViewHolder {
        public TextView positionView;
        public TextView addrssView;
        public TextView btcView;
        public TextView biliView;
    }

//    public interface ItemViewListener {
//        void itemClick(MoneyTypeResponse.MoneyTypeModel friendListModel);
//    }


}
