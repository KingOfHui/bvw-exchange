package com.darknet.bvw.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.darknet.bvw.R;
import com.darknet.bvw.view.TypefaceTextView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private Context context;
    private List<String> list;

    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;

    public NoteAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ONE) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        } else if (viewType == TYPE_TWO) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_note2, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }
        return null;
    }


    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return TYPE_ONE;
        } else {
            return TYPE_TWO;
        }
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1573300624867&di=108bb85fcdd7790e4c8e70b65c816777&imgtype=0&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fitbbs%2F1412%2F12%2Fc7%2F577115_1418395060436_mthumb.jpg";
        Uri uri = Uri.parse(url);
        holder.imgPhoto.setImageURI(uri);
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
        SimpleDraweeView imgPhoto, imgHead;
        TypefaceTextView txtZanNum, txtTitle, txtName;

        public ViewHolder(View itemView) {
            super(itemView);
            imgHead = itemView.findViewById(R.id.imgHead);
            imgPhoto = itemView.findViewById(R.id.imgPhoto);
            txtName = itemView.findViewById(R.id.txtName);
            txtZanNum = itemView.findViewById(R.id.txtZanNum);
            txtTitle = itemView.findViewById(R.id.txtTitle);
        }
    }
}
