package com.darknet.bvw.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.darknet.bvw.R;
import com.darknet.bvw.model.response.PeopleListResponse.PeopleModel;
import com.darknet.bvw.view.TypefaceTextView;

import java.util.ArrayList;
import java.util.List;

public class ContactPeopleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<PeopleModel> list = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public ContactPeopleAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_people_list, parent, false);
        PeoViewHolder viewHolder = new PeoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof PeoViewHolder) {
            PeopleModel peopleModel = list.get(position);
            if (TextUtils.isEmpty(peopleModel.getRemark())) {
                ((PeoViewHolder) holder).nameView.setText(context.getResources().getString(R.string.add_contack_people_item_name));
            } else {
                ((PeoViewHolder) holder).nameView.setText(peopleModel.getRemark());
            }

            String addressVals = peopleModel.getAddress();
            StringBuilder sb = new StringBuilder();
            if (addressVals != null) {
                if (addressVals.length() > 12) {
                    sb.append(addressVals.substring(0, 5));
                    sb.append("...");
                    sb.append(addressVals.substring((addressVals.length() - 5), addressVals.length()));
                    ((PeoViewHolder) holder).addRessView.setText(sb.toString());
                } else {
                    ((PeoViewHolder) holder).addRessView.setText(addressVals);
                }
            } else {
                ((PeoViewHolder) holder).addRessView.setText("");
            }

            if (position == (list.size() - 1)) {
                ((PeoViewHolder) holder).lineView.setVisibility(View.INVISIBLE);
            } else {
                ((PeoViewHolder) holder).lineView.setVisibility(View.VISIBLE);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(peopleModel);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class PeoViewHolder extends RecyclerView.ViewHolder {

        private TypefaceTextView nameView;
        private TypefaceTextView addRessView;
        private View lineView;

        public PeoViewHolder(View itemView) {
            super(itemView);
            nameView = (TypefaceTextView) itemView.findViewById(R.id.item_people_name);
            addRessView = (TypefaceTextView) itemView.findViewById(R.id.item_people_address);
            lineView = (View) itemView.findViewById(R.id.item_people_line_view);
        }
    }


    public void addData(List<PeopleModel> vals) {
        list.clear();
        list.addAll(vals);
    }


    public interface OnItemClickListener {
        void onItemClick(PeopleModel peopleModel);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {   //实现点击
        this.onItemClickListener = onItemClickListener;
    }
}
