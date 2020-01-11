package com.darknet.bvw.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.darknet.bvw.R;
import com.darknet.bvw.model.MoneyModel;
import com.darknet.bvw.view.TypefaceTextView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ZcAdapterTwo extends BaseAdapter {

    private Context context;

    private List<MoneyModel> list = new ArrayList<>();

//
//    ItemViewListener itemViewListener;
//
//    public void setItemViewListener(ItemViewListener itemViewListener) {
//        this.itemViewListener = itemViewListener;
//    }

    public ZcAdapterTwo(Context context) {
        this.context = context;
    }


//    public void addItemData(MoneyTypeResponse.MoneyTypeModel friendListModel){
//        listVals.add(friendListModel);
//        notifyDataSetInvalidated();
//    }

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

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder vh;

        if (view == null) {
            vh = new ZcAdapterTwo.ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_money_layout, null);
            vh.header = (SimpleDraweeView) view.findViewById(R.id.money_item_header);
            vh.numView = (TextView) view.findViewById(R.id.money_item_num_val);
            vh.nameView = (TypefaceTextView) view.findViewById(R.id.money_item_name);
            vh.moneyValView = (TypefaceTextView) view.findViewById(R.id.money_item_val);
            vh.lineOneView = (View) view.findViewById(R.id.money_item_line_view);
            vh.lineTwoView = (View) view.findViewById(R.id.money_item_line_two_view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        MoneyModel moneyModel = list.get(i);

        vh.nameView.setText(moneyModel.getSymbol());

        if (!TextUtils.isEmpty(moneyModel.getIcon())) {
            Uri uri = Uri.parse(moneyModel.getIcon());
            vh.header.setImageURI(uri);
        }

        if (TextUtils.isEmpty(moneyModel.getValue_qty()) || moneyModel.getValue_qty().equals("0") || moneyModel.getValue_qty().equals("0.000000")) {

            vh.numView.setText("0.000000");
            vh.moneyValView.setText("~");
        } else {
            vh.numView.setText(moneyModel.getValue_qty());
//                double priceVal = Double.parseDouble(moneyModel.getPrice());
//                double numVal = Double.parseDouble(moneyModel.getQty());
//                double totalMoney = priceVal * numVal;


            if(new BigDecimal(moneyModel.getValue_usd().toString()).compareTo(BigDecimal.ZERO) == 0){
                vh.moneyValView.setText("~");
            }else {
                vh.moneyValView.setText("≈$" + moneyModel.getValue_usd());
            }

        }

        if((list.size()-1) == i){
            vh.lineOneView.setVisibility(View.INVISIBLE);
            vh.lineTwoView.setVisibility(View.INVISIBLE);
        }else {
            vh.lineOneView.setVisibility(View.VISIBLE);
            vh.lineTwoView.setVisibility(View.INVISIBLE);
        }


        return view;
    }


    public final class ViewHolder {
        public SimpleDraweeView header;
        public TextView numView;
        public TypefaceTextView nameView;
        public TypefaceTextView moneyValView;
        public View lineOneView;
        public View lineTwoView;


    }


}
