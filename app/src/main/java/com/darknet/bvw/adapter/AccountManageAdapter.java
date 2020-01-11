package com.darknet.bvw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.darknet.bvw.R;
import com.darknet.bvw.db.Entity.ETHWalletModel;

import java.util.ArrayList;
import java.util.List;

public class AccountManageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_ITEM_SEARCH = 0;//搜索

    public static final int TYPE_ITEM_LIST = 1;//账户列表
    //
    public static final int TYPE_ITEM_OTHER = 2;//其他操作

    private List<ETHWalletModel> list;

    private Context context;

    public AccountManageAdapter(Context context, List<ETHWalletModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM_SEARCH) {
            return new SearchViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.lay_search, parent, false));
        } else if (viewType == TYPE_ITEM_LIST) {
            return new AccountListHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_account, parent, false));
        } else {
            return new OtherHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.lay_other, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        holder.itemView.setTag(position);
        if (holder instanceof SearchViewHolder) {

        } else if (holder instanceof AccountListHolder) {

        } else {

        }
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size() + 2;
        } else {
            return 2;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_ITEM_SEARCH;
        } else if (position == list.size() + 1) {
            return TYPE_ITEM_OTHER;
        } else {
            return TYPE_ITEM_LIST;
        }
    }

    public void addData(List<ETHWalletModel> data) {
        if (list == null) {
            list = new ArrayList<>();
        }
//        if (data != null) {
//            for (String str : data) {
//                list.add(str);
//            }
//        }
        notifyDataSetChanged();
    }

    public void setmData(List<ETHWalletModel> data) {
        if (this.list != null) {
            this.list = data;
        }
        notifyDataSetChanged();
    }


    public class SearchViewHolder extends RecyclerView.ViewHolder {
        public EditText editSearch;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            editSearch = itemView.findViewById(R.id.editSearch);
        }
    }

    public class AccountListHolder extends RecyclerView.ViewHolder {
        public AccountListHolder(@NonNull View view) {
            super(view);
        }
    }

    public class OtherHolder extends RecyclerView.ViewHolder {
        public OtherHolder(@NonNull View view) {
            super(view);
        }
    }
}
