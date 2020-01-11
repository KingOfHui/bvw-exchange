package com.darknet.bvw.chat.contact.viewholder.header;

import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.darknet.bvw.chat.R;
import com.darknet.bvw.chat.annotation.LayoutRes;
import com.darknet.bvw.chat.contact.UserListAdapter;
import com.darknet.bvw.chat.contact.model.FriendRequestValue;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.wildfirechat.remote.ChatManager;

@SuppressWarnings("unused")
@LayoutRes(resId = R.layout.contact_header_friend)
public class FriendRequestViewHolder extends HeaderViewHolder<FriendRequestValue> {
    @BindView(R.id.unreadFriendRequestCountTextView)
    TextView unreadRequestCountTextView;
    private FriendRequestValue value;


    public FriendRequestViewHolder(Fragment fragment, UserListAdapter adapter, View itemView) {
        super(fragment, adapter, itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBind(FriendRequestValue value) {
        this.value = value;
        int count = ChatManager.Instance().getUnreadFriendRequestStatus();
        if (count > 0) {
            unreadRequestCountTextView.setVisibility(View.VISIBLE);
            unreadRequestCountTextView.setText("" + count);
        } else {
            unreadRequestCountTextView.setVisibility(View.GONE);
        }
    }
}
