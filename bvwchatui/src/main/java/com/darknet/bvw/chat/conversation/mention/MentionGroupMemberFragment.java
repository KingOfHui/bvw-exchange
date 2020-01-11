package com.darknet.bvw.chat.conversation.mention;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.darknet.bvw.chat.contact.BaseUserListFragment;
import com.darknet.bvw.chat.contact.UserListAdapter;
import com.darknet.bvw.chat.contact.model.HeaderValue;
import com.darknet.bvw.chat.contact.model.UIUserInfo;
import com.darknet.bvw.chat.group.GroupViewModel;

import cn.wildfirechat.model.GroupInfo;

public class MentionGroupMemberFragment extends BaseUserListFragment {
    private GroupInfo groupInfo;

    public static MentionGroupMemberFragment newInstance(GroupInfo groupInfo) {
        Bundle args = new Bundle();
        args.putParcelable("groupInfo", groupInfo);
        MentionGroupMemberFragment fragment = new MentionGroupMemberFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        groupInfo = getArguments().getParcelable("groupInfo");
        showQuickIndexBar(true);
    }

    @Override
    protected void afterViews(View view) {
        super.afterViews(view);
        GroupViewModel groupViewModel = ViewModelProviders.of(getActivity()).get(GroupViewModel.class);
        groupViewModel.getGroupMemberUIUserInfosLiveData(groupInfo.target, false).observe(this, uiUserInfos -> {
            showContent();
            userListAdapter.setUsers(uiUserInfos);
        });
    }

    @Override
    public UserListAdapter onCreateUserListAdapter() {
        return new UserListAdapter(this);
    }

    @Override
    public void initHeaderViewHolders() {
        addHeaderViewHolder(MentionAllHeaderViewHolder.class, new HeaderValue());
    }

    @Override
    public void onHeaderClick(int index) {
        Intent intent = new Intent();
        intent.putExtra("mentionAll", true);
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }

    @Override
    public void onUserClick(UIUserInfo userInfo) {
        Intent intent = new Intent();
        intent.putExtra("userId", userInfo.getUserInfo().uid);
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }
}
