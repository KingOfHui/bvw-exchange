package com.darknet.bvw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.darknet.bvw.R;
import com.darknet.bvw.model.response.MoneyTypeResponse;
import com.darknet.bvw.view.TypefaceTextView;

import java.util.ArrayList;
import java.util.List;

public class MoneyTypeAdapter extends BaseAdapter {

    private Context context;

    private List<MoneyTypeResponse.MoneyTypeModel> listVals = new ArrayList<>();


    ItemViewListener itemViewListener;

    public void setItemViewListener(ItemViewListener itemViewListener) {
        this.itemViewListener = itemViewListener;
    }

    public MoneyTypeAdapter(Context context) {
        this.context = context;
    }


//    public void addItemData(MoneyTypeResponse.MoneyTypeModel friendListModel){
//        listVals.add(friendListModel);
//        notifyDataSetInvalidated();
//    }

    public void addData(List<MoneyTypeResponse.MoneyTypeModel> vals) {
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
            view = LayoutInflater.from(context).inflate(R.layout.money_type_item, null);
            vh.nameView = (TypefaceTextView) view.findViewById(R.id.money_type_name);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        final MoneyTypeResponse.MoneyTypeModel moneyTypeModel = listVals.get(i);


        vh.nameView.setText(moneyTypeModel.getSymbol());

//        vh.nameView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                itemViewListener.itemClick(moneyTypeModel);
//            }
//        });
        return view;
    }


    public final class ViewHolder {
        public TypefaceTextView nameView;
    }

    public interface ItemViewListener {
        void itemClick(MoneyTypeResponse.MoneyTypeModel friendListModel);
    }


}
