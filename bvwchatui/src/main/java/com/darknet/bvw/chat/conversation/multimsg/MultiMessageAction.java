package com.darknet.bvw.chat.conversation.multimsg;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;

import com.darknet.bvw.chat.conversation.message.model.UiMessage;

import java.util.List;

import cn.wildfirechat.model.Conversation;

public abstract class MultiMessageAction {
    protected Conversation conversation;
    protected FragmentActivity activity;

    public MultiMessageAction() {
    }

    public final void onBind(FragmentActivity activity, Conversation conversation) {
        this.activity = activity;
        this.conversation = conversation;

    }

    public abstract void onClick(List<UiMessage> messages);

    public int priority() {
        return 0;
    }

    public boolean confirm() {
        return false;
    }

    public boolean filter(Conversation conversation) {
        return false;
    }

    public abstract int iconResId();

    public abstract String title(Context context);

    public String confirmPrompt() {
        return "";
    }
}
