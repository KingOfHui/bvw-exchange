package com.darknet.bvw.chat.contact.viewholder.footer;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.darknet.bvw.chat.contact.ContactViewModel;
import com.darknet.bvw.chat.contact.UserListAdapter;
import com.darknet.bvw.chat.contact.model.FooterValue;

public abstract class FooterViewHolder<T extends FooterValue> extends RecyclerView.ViewHolder {
    protected Fragment fragment;
    protected UserListAdapter adapter;
    protected ContactViewModel contactViewModel;

    public FooterViewHolder(Fragment fragment, UserListAdapter adapter, View itemView) {
        super(itemView);
        this.fragment = fragment;
        this.adapter = adapter;
        contactViewModel = ViewModelProviders.of(fragment).get(ContactViewModel.class);
    }


    public abstract void onBind(T t);

}
