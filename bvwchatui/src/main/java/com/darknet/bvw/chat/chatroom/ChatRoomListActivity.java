package com.darknet.bvw.chat.chatroom;

import com.darknet.bvw.R;
import com.darknet.bvw.chat.WfcBaseActivity;

public class ChatRoomListActivity extends WfcBaseActivity {

    @Override
    protected void afterViews() {
        getSupportFragmentManager().
                beginTransaction()
                .replace(R.id.containerFrameLayout, new ChatRoomListFragment())
                .commit();
    }

    @Override
    protected int contentLayout() {
        return R.layout.fragment_container_activity;
    }
}
