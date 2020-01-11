package com.darknet.bvw.chat.conversation.message.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.darknet.bvw.R;
import com.darknet.bvw.chat.annotation.EnableContextMenu;
import com.darknet.bvw.chat.annotation.MessageContentType;
import com.darknet.bvw.chat.annotation.ReceiveLayoutRes;
import com.darknet.bvw.chat.annotation.SendLayoutRes;
import com.darknet.bvw.chat.conversation.message.model.UiMessage;

import butterknife.BindView;
import cn.wildfirechat.message.UnknownMessageContent;

@MessageContentType(UnknownMessageContent.class)
@SendLayoutRes(resId = R.layout.conversation_item_unknown_send)
@ReceiveLayoutRes(resId = R.layout.conversation_item_unknown_receive)
@EnableContextMenu
public class UnkownMessageContentViewHolder extends NormalMessageContentViewHolder {
    @BindView(R.id.contentTextView)
    TextView contentTextView;

    public UnkownMessageContentViewHolder(FragmentActivity context, RecyclerView.Adapter adapter, View itemView) {
        super(context, adapter, itemView);
    }

    @Override
    public void onBind(UiMessage message) {
        contentTextView.setText("unknown message");
    }
}
