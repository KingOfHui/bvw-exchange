package com.darknet.bvw.chat.conversation;

import android.content.Intent;

import androidx.lifecycle.ViewModelProviders;

import com.afollestad.materialdialogs.MaterialDialog;
import com.darknet.bvw.R;
import com.darknet.bvw.chat.contact.model.UIUserInfo;
import com.darknet.bvw.chat.contact.pick.PickConversationTargetActivity;
import com.darknet.bvw.chat.group.GroupViewModel;
import com.darknet.bvw.chat.third.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import cn.wildfirechat.model.Conversation;
import cn.wildfirechat.model.GroupInfo;

public class CreateConversationActivity extends PickConversationTargetActivity {
    private GroupViewModel groupViewModel;

    @Override
    protected void afterViews() {
        super.afterViews();
        groupViewModel = ViewModelProviders.of(this).get(GroupViewModel.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onContactPicked(List<UIUserInfo> newlyCheckedUserInfos) {
        if (newlyCheckedUserInfos.size() == 1) {

            Intent intent = new Intent(this, ConversationActivity.class);
            Conversation conversation = new Conversation(Conversation.ConversationType.Single, newlyCheckedUserInfos.get(0).getUserInfo().uid);
            intent.putExtra("conversation", conversation);
            startActivity(intent);
            finish();
        } else {
            MaterialDialog dialog = new MaterialDialog.Builder(this)
                    .content("创建中...")
                    .progress(true, 100)
                    .build();
            dialog.show();

            List<UIUserInfo> userInfos = new ArrayList<>();
            userInfos.addAll(newlyCheckedUserInfos);
            groupViewModel.createGroup(this, userInfos).observe(this, result -> {
                dialog.dismiss();
                if (result.isSuccess()) {
                    UIUtils.showToast(UIUtils.getString(R.string.create_group_success));
                    Intent intent = new Intent(CreateConversationActivity.this, ConversationActivity.class);
                    Conversation conversation = new Conversation(Conversation.ConversationType.Group, result.getResult(), 0);
                    intent.putExtra("conversation", conversation);
                    startActivity(intent);
                } else {
                    UIUtils.showToast(UIUtils.getString(R.string.create_group_fail));
                }
                finish();
            });
        }

    }

    @Override
    public void onGroupPicked(List<GroupInfo> groupInfos) {
        Intent intent = new Intent(this, ConversationActivity.class);
        Conversation conversation = new Conversation(Conversation.ConversationType.Group, groupInfos.get(0).target);
        intent.putExtra("conversation", conversation);
        startActivity(intent);
        finish();
    }
}
