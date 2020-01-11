package com.darknet.bvw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.darknet.bvw.R;
import com.darknet.bvw.model.response.TradeListResponse;

import java.util.ArrayList;
import java.util.List;

public class TradeListAdapter extends BaseAdapter {

    private Context context;

    List<TradeListResponse.TradeListModel> listVal = new ArrayList<>();

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public TradeListAdapter(Context context) {
        this.context = context;
    }

    public void addData(List<TradeListResponse.TradeListModel> items){
        listVal.clear();
        listVal.addAll(items);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return listVal.size();
    }

    @Override
    public Object getItem(int i) {
        return listVal.get(i);
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
            view = LayoutInflater.from(context).inflate(R.layout.item_trade_list, null);
            vh.addressView = (TextView) view.findViewById(R.id.trade_list_address_view);
            vh.moneyView = (TextView) view.findViewById(R.id.trade_list_money_view);
            vh.timeView = (TextView) view.findViewById(R.id.trade_list_time);
            vh.lineView = (View)view.findViewById(R.id.trade_list_line_view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }


        final TradeListResponse.TradeListModel tradeListModel = listVal.get(i);

        String addressVals = tradeListModel.getTo_address();
        StringBuilder sb = new StringBuilder();
        if(addressVals!=null){
            sb.append(addressVals.substring(0,5));
            sb.append("...");
            sb.append(addressVals.substring((addressVals.length()-5),addressVals.length()));
        }

        String tempAddrss = sb.toString();

        if(i == (listVal.size()-1) ){
            vh.lineView.setVisibility(View.INVISIBLE);
        }else {
            vh.lineView.setVisibility(View.VISIBLE);
        }

        vh.addressView.setText(tempAddrss);
        vh.moneyView.setText(tradeListModel.getSend_wrapper()+tradeListModel.getAmount().stripTrailingZeros().toPlainString()+" "+tradeListModel.getSymbol());
        vh.timeView.setText(tradeListModel.getCreate_at());

        return view;
    }


    public interface OnItemClick{
        void itemClick(TradeListResponse.TradeListModel tradeListModel);
    }


    public final class ViewHolder {
        public TextView addressView;
        public TextView moneyView;
        public TextView timeView;
        public View lineView;
    }

}
