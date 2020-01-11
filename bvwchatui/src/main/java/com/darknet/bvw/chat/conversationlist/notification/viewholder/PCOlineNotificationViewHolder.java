package com.darknet.bvw.chat.conversationlist.notification.viewholder;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.darknet.bvw.chat.R;
import com.darknet.bvw.chat.annotation.LayoutRes;
import com.darknet.bvw.chat.annotation.StatusNotificationType;
import com.darknet.bvw.chat.conversationlist.notification.PCOnlineNotification;
import com.darknet.bvw.chat.conversationlist.notification.StatusNotification;

@LayoutRes(resId = R.layout.conversationlist_item_notification_pc_online)
@StatusNotificationType(PCOnlineNotification.class)
public class PCOlineNotificationViewHolder extends StatusNotificationViewHolder {
    public PCOlineNotificationViewHolder(Fragment fragment) {
        super(fragment);
    }

    @Override
    public void onBind(View view, StatusNotification notification) {

    }
}
