package com.darknet.bvw.chat.conversation.message.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.darknet.bvw.R;
import com.darknet.bvw.chat.annotation.LayoutRes;
import com.darknet.bvw.chat.annotation.MessageContentType;
import com.darknet.bvw.chat.conversation.message.model.UiMessage;

import butterknife.BindView;
import cn.wildfirechat.message.notification.RecallMessageContent;

@MessageContentType(RecallMessageContent.class)
@LayoutRes(resId = R.layout.conversation_item_notification)
public class RecallMessageContentViewHolderSimple extends SimpleNotificationMessageContentViewHolder {
    @BindView(R.id.notificationTextView)
    TextView notificationTextView;

    public RecallMessageContentViewHolderSimple(FragmentActivity activity, RecyclerView.Adapter adapter, View itemView) {
        super(activity, adapter, itemView);
    }

    @Override
    protected void onBind(UiMessage message) {
        notificationTextView.setText(message.message.digest());
    }
}
