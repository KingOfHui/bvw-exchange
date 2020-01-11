package com.darknet.bvw.chat.group;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.darknet.bvw.chat.contact.model.UIUserInfo;
import com.darknet.bvw.chat.contact.pick.PickContactFragment;
import com.darknet.bvw.chat.contact.pick.PickUserViewModel;

import java.util.ArrayList;
import java.util.List;

import cn.wildfirechat.model.GroupInfo;

public class AddGroupMemberFragment extends PickContactFragment {
    private GroupInfo groupInfo;

    public static AddGroupMemberFragment newInstance(GroupInfo groupInfo) {
        Bundle args = new Bundle();
        args.putParcelable("groupInfo", groupInfo);
        AddGroupMemberFragment fragment = new AddGroupMemberFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        groupInfo = getArguments().getParcelable("groupInfo");
    }


    @Override
    protected void setupPickFromUsers() {
        super.setupPickFromUsers();
        PickUserViewModel pickUserViewModel = ViewModelProviders.of(getActivity()).get(PickUserViewModel.class);

        GroupViewModel groupViewModel = ViewModelProviders.of(this).get(GroupViewModel.class);

        groupViewModel.getGroupMemberUIUserInfosLiveData(groupInfo.target, false).observe(this, uiUserInfos -> {
            if (uiUserInfos == null || uiUserInfos.isEmpty()) {
                return;
            }
            List<String> memberIds = new ArrayList<>(uiUserInfos.size());
            for (UIUserInfo uiUserInfo : uiUserInfos) {
                memberIds.add(uiUserInfo.getUserInfo().uid);
            }
            pickUserViewModel.setUncheckableIds(memberIds);
            userListAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void initHeaderViewHolders() {
        // do nothing
    }
}
