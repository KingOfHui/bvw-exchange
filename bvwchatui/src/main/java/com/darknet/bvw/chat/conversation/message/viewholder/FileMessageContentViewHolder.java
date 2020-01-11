package com.darknet.bvw.chat.conversation.message.viewholder;

import android.content.ComponentName;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.darknet.bvw.R;
import com.darknet.bvw.chat.annotation.EnableContextMenu;
import com.darknet.bvw.chat.annotation.LayoutRes;
import com.darknet.bvw.chat.annotation.MessageContentType;
import com.darknet.bvw.chat.conversation.message.model.UiMessage;
import com.darknet.bvw.chat.third.utils.UIUtils;
import com.darknet.bvw.chat.utils.FileUtils;
import com.darknet.bvw.chat.widget.BubbleImageView;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import cn.wildfirechat.message.FileMessageContent;
import cn.wildfirechat.message.core.MessageStatus;

@MessageContentType(FileMessageContent.class)
@LayoutRes(resId = R.layout.conversation_item_file_send)
@EnableContextMenu
public class FileMessageContentViewHolder extends MediaMessageContentViewHolder {

    @BindView(R.id.imageView)
    BubbleImageView imageView;
    private FileMessageContent fileMessageContent;

    public FileMessageContentViewHolder(FragmentActivity context, RecyclerView.Adapter adapter, View itemView) {
        super(context, adapter, itemView);
    }

    @Override
    public void onBind(UiMessage message) {
        super.onBind(message);
        fileMessageContent = (FileMessageContent) message.message.content;
        if (message.message.status == MessageStatus.Sending || message.isDownloading) {
            imageView.setPercent(message.progress);
            imageView.setProgressVisible(true);
            imageView.showShadow(false);
        } else {
            imageView.setProgressVisible(false);
            imageView.showShadow(false);
        }
        Glide.with(context).load(R.mipmap.ic_file)
                .apply(new RequestOptions().override(UIUtils.dip2Px(150), UIUtils.dip2Px(150)).centerCrop()).into(imageView);
    }

    @OnClick(R.id.imageView)
    public void onClick(View view) {
        if (message.isDownloading) {
            return;
        }
        File file = messageViewModel.mediaMessageContentFile(message);
        if (file == null) {
            return;
        }

        if (file.exists()) {
            Intent intent = FileUtils.getViewIntent(context, file);
            ComponentName cn = intent.resolveActivity(context.getPackageManager());
            if (cn == null) {
                Toast.makeText(context, "找不到能打开此文件的应用", Toast.LENGTH_SHORT).show();
                return;
            }
            context.startActivity(intent);
        } else {
            messageViewModel.downloadMedia(message, file);
        }
    }
}
