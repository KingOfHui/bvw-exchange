package com.darknet.bvw.chat.contact.pick.viewholder;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.darknet.bvw.chat.R;
import com.darknet.bvw.chat.annotation.LayoutRes;
import com.darknet.bvw.chat.contact.UserListAdapter;
import com.darknet.bvw.chat.contact.model.GroupValue;
import com.darknet.bvw.chat.contact.viewholder.header.HeaderViewHolder;


@SuppressWarnings("unused")
@LayoutRes(resId = R.layout.contact_header_group)
public class PickGroupViewHolder extends HeaderViewHolder<GroupValue> {

    public PickGroupViewHolder(Fragment fragment, UserListAdapter adapter, View itemView) {
        super(fragment, adapter, itemView);
    }

    @Override
    public void onBind(GroupValue groupValue) {

    }
}
