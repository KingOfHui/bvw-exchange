package com.darknet.bvw.chat.conversation.mention;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.darknet.bvw.R;
import com.darknet.bvw.chat.annotation.LayoutRes;
import com.darknet.bvw.chat.contact.UserListAdapter;
import com.darknet.bvw.chat.contact.model.HeaderValue;
import com.darknet.bvw.chat.contact.viewholder.header.HeaderViewHolder;

@LayoutRes(resId = R.layout.conversation_header_mention_all)
public class MentionAllHeaderViewHolder extends HeaderViewHolder<HeaderValue> {
    public MentionAllHeaderViewHolder(Fragment fragment, UserListAdapter adapter, View itemView) {
        super(fragment, adapter, itemView);
    }

    @Override
    public void onBind(HeaderValue value) {

    }
}
