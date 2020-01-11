package com.darknet.bvw.chat.group.manage;

import com.darknet.bvw.R;
import com.darknet.bvw.chat.WfcBaseActivity;

import cn.wildfirechat.model.GroupInfo;

public class GroupManagerListActivity extends WfcBaseActivity {

    @Override
    protected void afterViews() {
        GroupInfo groupInfo = getIntent().getParcelableExtra("groupInfo");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerFrameLayout, GroupManagerListFragment.newInstance(groupInfo))
                .commit();
    }

    @Override
    protected int contentLayout() {
        return R.layout.fragment_container_activity;
    }
}