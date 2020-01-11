package com.darknet.bvw.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.darknet.bvw.R;
import com.darknet.bvw.model.LeftBean;

import java.util.List;

/**
 * 作者：created by albert on 2020-01-08 11:46
 * 邮箱：lvzhongdi@icloud.com
 *
 * @param
 **/
public class TokenCoinsAdapter extends ArrayAdapter<LeftBean> {

    private LayoutInflater mInflater;
    private List<LeftBean> mList;


    public TokenCoinsAdapter(@NonNull Context context, @NonNull List<LeftBean> values) {
        super(context, R.layout.item_text, values);
        mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mList = values;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_text, parent, false);
            holder = new Holder();
            holder.textView = convertView.findViewById(R.id.item_text);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.textView.setTextColor(mList.get(position).getColor());
        holder.textView.setText(mList.get(position).getCoins());

        return convertView;
    }

    private static class Holder {
        public TextView textView;
    }
}
