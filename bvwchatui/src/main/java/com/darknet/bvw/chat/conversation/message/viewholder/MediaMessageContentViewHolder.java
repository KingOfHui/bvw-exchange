package com.darknet.bvw.chat.conversation.message.viewholder;

import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.darknet.bvw.chat.conversation.ConversationMessageAdapter;
import com.darknet.bvw.chat.conversation.message.model.UiMessage;
import com.darknet.bvw.chat.mm.MMPreviewActivity;
import com.darknet.bvw.chat.mm.MediaEntry;

import java.util.ArrayList;
import java.util.List;

import cn.wildfirechat.message.ImageMessageContent;
import cn.wildfirechat.message.MediaMessageContent;
import cn.wildfirechat.message.VideoMessageContent;
import cn.wildfirechat.message.core.MessageContentType;
import cn.wildfirechat.message.core.MessageDirection;

public class MediaMessageContentViewHolder extends NormalMessageContentViewHolder {

    public MediaMessageContentViewHolder(FragmentActivity activity, RecyclerView.Adapter adapter, View itemView) {
        super(activity, adapter, itemView);
    }

    @Override
    protected void onBind(UiMessage message) {
        if (message.message.direction == MessageDirection.Receive) {
            if (message.isDownloading) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        } else {
            // todo
        }
    }

    protected void previewMM() {
        List<UiMessage> messages = ((ConversationMessageAdapter) adapter).getMessages();
        List<MediaEntry> entries = new ArrayList<>();
        UiMessage msg;

        int current = 0;
        int index = 0;
        for (int i = 0; i < messages.size(); i++) {
            msg = messages.get(i);
            if (msg.message.content.getType() != MessageContentType.ContentType_Image
                    && msg.message.content.getType() != MessageContentType.ContentType_Video) {
                continue;
            }
            MediaEntry entry = new MediaEntry();
            if (msg.message.content.getType() == MessageContentType.ContentType_Image) {
                entry.setType(MediaEntry.TYPE_IMAGE);
                entry.setThumbnail(((ImageMessageContent) msg.message.content).getThumbnail());

            } else {
                entry.setType(MediaEntry.TYPE_VIDEO);
                entry.setThumbnail(((VideoMessageContent) msg.message.content).getThumbnail());
            }
            entry.setMediaUrl(((MediaMessageContent) msg.message.content).remoteUrl);
            entry.setMediaLocalPath(((MediaMessageContent) msg.message.content).localPath);
            entries.add(entry);

            if (message.message.messageId == msg.message.messageId) {
                current = index;
            }
            index++;
        }
        if (entries.isEmpty()) {
            return;
        }
        MMPreviewActivity.startActivity(context, entries, current);
    }
}
