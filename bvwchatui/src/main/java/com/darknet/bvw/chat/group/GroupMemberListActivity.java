package com.darknet.bvw.chat.group;

import com.darknet.bvw.R;
import com.darknet.bvw.chat.WfcBaseActivity;

import cn.wildfirechat.model.GroupInfo;

public class GroupMemberListActivity extends WfcBaseActivity {
    private GroupInfo groupInfo;

    @Override
    protected void afterViews() {
        groupInfo = getIntent().getParcelableExtra("groupInfo");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerFrameLayout, GroupMemberListFragment.newInstance(groupInfo))
                .commit();
    }

    @Override
    protected int contentLayout() {
        return R.layout.fragment_container_activity;
    }
}
