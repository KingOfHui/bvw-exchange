package com.darknet.bvw.chat.group.manage;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.darknet.bvw.R;
import com.darknet.bvw.chat.annotation.LayoutRes;
import com.darknet.bvw.chat.contact.UserListAdapter;
import com.darknet.bvw.chat.contact.model.FooterValue;
import com.darknet.bvw.chat.contact.viewholder.footer.FooterViewHolder;


@LayoutRes(resId = R.layout.group_manage_item_add_manager)
public class AddGroupManagerViewHolder extends FooterViewHolder<FooterValue> {

    public AddGroupManagerViewHolder(Fragment fragment, UserListAdapter adapter, View itemView) {
        super(fragment, adapter, itemView);
    }

    @Override
    public void onBind(FooterValue footerValue) {
        // do nothing
    }
}
