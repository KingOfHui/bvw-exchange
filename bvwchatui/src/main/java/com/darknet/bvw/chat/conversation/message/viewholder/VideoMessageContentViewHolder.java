package com.darknet.bvw.chat.conversation.message.viewholder;

import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.darknet.bvw.R;
import com.darknet.bvw.chat.annotation.EnableContextMenu;
import com.darknet.bvw.chat.annotation.LayoutRes;
import com.darknet.bvw.chat.annotation.MessageContentType;
import com.darknet.bvw.chat.conversation.message.model.UiMessage;
import com.darknet.bvw.chat.widget.BubbleImageView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.wildfirechat.message.VideoMessageContent;

@MessageContentType(VideoMessageContent.class)
@LayoutRes(resId = R.layout.conversation_item_video_send)
@EnableContextMenu
public class VideoMessageContentViewHolder extends MediaMessageContentViewHolder {
    @BindView(R.id.imageView)
    BubbleImageView imageView;
    @BindView(R.id.playImageView)
    ImageView playImageView;

    public VideoMessageContentViewHolder(FragmentActivity context, RecyclerView.Adapter adapter, View itemView) {
        super(context, adapter, itemView);
    }

    @Override
    public void onBind(UiMessage message) {
        VideoMessageContent fileMessage = (VideoMessageContent) message.message.content;
        if (fileMessage.getThumbnail() != null && fileMessage.getThumbnail().getWidth() > 0) {
            imageView.setImageBitmap(fileMessage.getThumbnail());
            playImageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setImageResource(R.mipmap.img_video_default);
            playImageView.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.videoContentLayout)
    void play() {
        previewMM();
    }

    @Override
    public boolean contextMenuItemFilter(UiMessage uiMessage, String tag) {
        if (MessageContextMenuItemTags.TAG_FORWARD.equals(tag)) {
            return true;
        } else {
            return super.contextMenuItemFilter(uiMessage, tag);
        }
    }
}
