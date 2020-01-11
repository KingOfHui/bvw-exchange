package com.darknet.bvw.chat.conversation.message.viewholder;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.darknet.bvw.R;
import com.darknet.bvw.chat.GlideApp;
import com.darknet.bvw.chat.annotation.EnableContextMenu;
import com.darknet.bvw.chat.annotation.MessageContentType;
import com.darknet.bvw.chat.annotation.ReceiveLayoutRes;
import com.darknet.bvw.chat.annotation.SendLayoutRes;
import com.darknet.bvw.chat.conversation.message.model.UiMessage;
import com.darknet.bvw.chat.third.utils.UIUtils;

import butterknife.BindView;
import cn.wildfirechat.message.StickerMessageContent;

@MessageContentType(StickerMessageContent.class)
@SendLayoutRes(resId = R.layout.conversation_item_sticker_send)
@ReceiveLayoutRes(resId = R.layout.conversation_item_sticker_receive)
@EnableContextMenu
public class StickerMessageContentViewHolder extends NormalMessageContentViewHolder {
    private String path;
    @BindView(R.id.stickerImageView)
    ImageView imageView;

    public StickerMessageContentViewHolder(FragmentActivity context, RecyclerView.Adapter adapter, View itemView) {
        super(context, adapter, itemView);
    }

    @Override
    public void onBind(UiMessage message) {
        StickerMessageContent stickerMessage = (StickerMessageContent) message.message.content;
        imageView.getLayoutParams().width = UIUtils.dip2Px(stickerMessage.width > 150 ? 150 : stickerMessage.width);
        imageView.getLayoutParams().height = UIUtils.dip2Px(stickerMessage.height > 150 ? 150 : stickerMessage.height);

        if (!TextUtils.isEmpty(stickerMessage.localPath)) {
            if (stickerMessage.localPath.equals(path)) {
                return;
            }
            GlideApp.with(context).load(stickerMessage.localPath)
                    .into(imageView);
            path = stickerMessage.localPath;
        } else {
            CircularProgressDrawable progressDrawable = new CircularProgressDrawable(context);
            progressDrawable.setStyle(CircularProgressDrawable.DEFAULT);
            progressDrawable.start();
            GlideApp.with(context)
                    .load(stickerMessage.remoteUrl)
                    .placeholder(progressDrawable)
                    .into(imageView);
        }
    }

    // 其实也没啥用，有itemView了，可以直接setXXXListener了, 只是简化了，不用调用setXXXListener
    // 更复杂的，比如设置播放进度条的滑动等，在通过itemView设置相关listener吧
    public void onClick(View view) {
        // TODO
    }

}
