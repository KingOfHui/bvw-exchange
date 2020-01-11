package com.darknet.bvw.chat.conversation.message.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.darknet.bvw.R;
import com.darknet.bvw.chat.conversation.ConversationMessageAdapter;
import com.darknet.bvw.chat.conversation.message.model.UiMessage;
import com.darknet.bvw.chat.third.utils.TimeUtils;
import com.darknet.bvw.chat.viewmodel.MessageViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.wildfirechat.message.Message;

public abstract class MessageContentViewHolder extends RecyclerView.ViewHolder {
    protected FragmentActivity context;
    protected View itemView;
    protected UiMessage message;
    protected int position;
    protected RecyclerView.Adapter adapter;
    protected MessageViewModel messageViewModel;

    @BindView(R.id.timeTextView)
    TextView timeTextView;


    public MessageContentViewHolder(FragmentActivity activity, RecyclerView.Adapter adapter, View itemView) {
        super(itemView);
        this.context = activity;
        this.itemView = itemView;
        this.adapter = adapter;
        messageViewModel = ViewModelProviders.of(activity).get(MessageViewModel.class);
        ButterKnife.bind(this, itemView);
    }

    public void onBind(UiMessage message, int position) {
        setMessageTime(message.message, position);
    }

    /**
     * @param uiMessage
     * @param tag
     * @return 返回true，将从context menu中排除
     */

    public abstract boolean contextMenuItemFilter(UiMessage uiMessage, String tag);

    public void onViewRecycled() {
        // you can do some clean up here
    }

    protected void setMessageTime(Message item, int position) {
        long msgTime = item.serverTime;
        if (position > 0) {
            Message preMsg = ((ConversationMessageAdapter) adapter).getItem(position - 1).message;
            long preMsgTime = preMsg.serverTime;
            if (msgTime - preMsgTime > (5 * 60 * 1000)) {
                timeTextView.setVisibility(View.VISIBLE);
                timeTextView.setText(TimeUtils.getMsgFormatTime(msgTime));
            } else {
                timeTextView.setVisibility(View.GONE);
            }
        } else {
            timeTextView.setVisibility(View.VISIBLE);
            timeTextView.setText(TimeUtils.getMsgFormatTime(msgTime));
        }
    }

}
