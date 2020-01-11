package com.darknet.bvw.chat.conversation.multimsg;

import android.content.Context;

import androidx.lifecycle.ViewModelProvider;

import com.darknet.bvw.R;
import com.darknet.bvw.chat.conversation.message.model.UiMessage;
import com.darknet.bvw.chat.viewmodel.MessageViewModel;

import java.util.List;

public class DeleteMultiMessageAction extends MultiMessageAction {

    @Override
    public void onClick(List<UiMessage> messages) {
        MessageViewModel messageViewModel = new ViewModelProvider(activity).get(MessageViewModel.class);
        for (UiMessage message : messages) {
            messageViewModel.deleteMessage(message.message);
        }
    }

    @Override
    public int iconResId() {
        return R.mipmap.ic_delete;
    }

    @Override
    public String title(Context context) {
        return "删除";
    }

    @Override
    public boolean confirm() {
        return true;
    }

    @Override
    public String confirmPrompt() {
        return "确认删除?";
    }
}
