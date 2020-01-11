package com.darknet.bvw.chat.contact;


import android.content.Intent;
import android.view.View;

import com.darknet.bvw.chat.channel.ChannelListActivity;
import com.darknet.bvw.chat.contact.model.ContactCountFooterValue;
import com.darknet.bvw.chat.contact.model.FriendRequestValue;
import com.darknet.bvw.chat.contact.model.GroupValue;
import com.darknet.bvw.chat.contact.model.HeaderValue;
import com.darknet.bvw.chat.contact.model.UIUserInfo;
import com.darknet.bvw.chat.contact.newfriend.FriendRequestListActivity;
import com.darknet.bvw.chat.contact.viewholder.footer.ContactCountViewHolder;
import com.darknet.bvw.chat.contact.viewholder.header.ChannelViewHolder;
import com.darknet.bvw.chat.contact.viewholder.header.FriendRequestViewHolder;
import com.darknet.bvw.chat.contact.viewholder.header.GroupViewHolder;
import com.darknet.bvw.chat.group.GroupListActivity;
import com.darknet.bvw.chat.user.UserInfoActivity;
import com.darknet.bvw.chat.widget.QuickIndexBar;
import com.darknet.bvw.main.MainActivity;

//import cn.wildfire.chat.app.main.MainActivity;
//import cn.wildfire.chat.kit.channel.ChannelListActivity;
//import cn.wildfire.chat.kit.contact.model.ContactCountFooterValue;
//import cn.wildfire.chat.kit.contact.model.FriendRequestValue;
//import cn.wildfire.chat.kit.contact.model.GroupValue;
//import cn.wildfire.chat.kit.contact.model.HeaderValue;
//import cn.wildfire.chat.kit.contact.model.UIUserInfo;
//import cn.wildfire.chat.kit.contact.newfriend.FriendRequestListActivity;
//import cn.wildfire.chat.kit.contact.viewholder.footer.ContactCountViewHolder;
//import cn.wildfire.chat.kit.contact.viewholder.header.ChannelViewHolder;
//import cn.wildfire.chat.kit.contact.viewholder.header.FriendRequestViewHolder;
//import cn.wildfire.chat.kit.contact.viewholder.header.GroupViewHolder;
//import cn.wildfire.chat.kit.group.GroupListActivity;
//import cn.wildfire.chat.kit.user.UserInfoActivity;
//import cn.wildfire.chat.kit.widget.QuickIndexBar;

public class ContactListFragment extends BaseUserListFragment implements QuickIndexBar.OnLetterUpdateListener {

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (userListAdapter != null && isVisibleToUser) {
            contactViewModel.reloadContact();
            contactViewModel.reloadFriendRequestStatus();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        contactViewModel.reloadContact();
        contactViewModel.reloadFriendRequestStatus();
    }

    @Override
    protected void afterViews(View view) {
        super.afterViews(view);
        contactViewModel.contactListLiveData().observe(this, userInfos -> {
            showContent();
            userListAdapter.setUsers(userInfos);
        });
        contactViewModel.friendRequestUpdatedLiveData().observe(getActivity(), integer -> userListAdapter.updateHeader(0, new FriendRequestValue(integer)));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void initHeaderViewHolders() {
        addHeaderViewHolder(FriendRequestViewHolder.class, new FriendRequestValue(contactViewModel.getUnreadFriendRequestCount()));
        addHeaderViewHolder(GroupViewHolder.class, new GroupValue());
        addHeaderViewHolder(ChannelViewHolder.class, new HeaderValue());
    }

    @Override
    public void initFooterViewHolders() {
        addFooterViewHolder(ContactCountViewHolder.class, new ContactCountFooterValue());
    }

    @Override
    public void onUserClick(UIUserInfo userInfo) {
        Intent intent = new Intent(getActivity(), UserInfoActivity.class);
        intent.putExtra("userInfo", userInfo.getUserInfo());
        startActivity(intent);
    }

    @Override
    public void onHeaderClick(int index) {
        switch (index) {
            case 0:
                ((MainActivity) getActivity()).hideUnreadFriendRequestBadgeView();
                showFriendRequest();
                break;
            case 1:
                showGroupList();
                break;
            case 2:
                showChannelList();
                break;
            default:
                break;
        }
    }

    private void showFriendRequest() {
        FriendRequestValue value = new FriendRequestValue(0);
        userListAdapter.updateHeader(0, value);

        contactViewModel.clearUnreadFriendRequestStatus();
        Intent intent = new Intent(getActivity(), FriendRequestListActivity.class);
        startActivity(intent);
    }

    private void showGroupList() {
        Intent intent = new Intent(getActivity(), GroupListActivity.class);
        startActivity(intent);
    }

    private void showChannelList() {
        Intent intent = new Intent(getActivity(), ChannelListActivity.class);
        startActivity(intent);
    }
}
