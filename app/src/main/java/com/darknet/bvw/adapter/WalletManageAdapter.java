package com.darknet.bvw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.darknet.bvw.R;

import java.util.ArrayList;
import java.util.List;

public class WalletManageAdapter extends RecyclerView.Adapter<WalletManageAdapter.ViewHolder> {

    private Context context;
    private List<String> list;

    public WalletManageAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_wallet_manage, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    public void setData(List<String> newData) {
        if (this.list == null) {
            this.list = new ArrayList<>();
            this.list = newData;
        } else {
            this.list.clear();
            notifyDataSetChanged();
            addData(newData);
        }
        notifyDataSetChanged();
    }

    public void addData(List<String> addList) {
        if (this.list == null) {
            list = new ArrayList<>();
        }
        if (addList != null) {
            for (String str : addList) {
                list.add(str);
            }
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(View itemView) {
            super(itemView);

        }
    }
}
