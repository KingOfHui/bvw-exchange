package com.darknet.bvw.chat.conversationlist.notification;

import com.darknet.bvw.chat.annotation.LayoutRes;
import com.darknet.bvw.chat.annotation.StatusNotificationType;
import com.darknet.bvw.chat.conversationlist.notification.viewholder.ConnectionNotificationViewHolder;
import com.darknet.bvw.chat.conversationlist.notification.viewholder.PCOlineNotificationViewHolder;
import com.darknet.bvw.chat.conversationlist.notification.viewholder.StatusNotificationViewHolder;

import java.util.HashMap;
import java.util.Map;

public class StatusNotificationManager {
    private static StatusNotificationManager instance;
    private Map<Class<? extends StatusNotification>, Class<? extends StatusNotificationViewHolder>> notificationViewHolders;

    public synchronized static StatusNotificationManager getInstance() {
        if (instance == null) {
            instance = new StatusNotificationManager();
        }
        return instance;
    }

    private StatusNotificationManager() {
        init();
    }

    private void init() {
        notificationViewHolders = new HashMap<>();
        registerNotificationViewHolder(PCOlineNotificationViewHolder.class);
        registerNotificationViewHolder(ConnectionNotificationViewHolder.class);
    }

    public void registerNotificationViewHolder(Class<? extends StatusNotificationViewHolder> holderClass) {
        StatusNotificationType notificationType = holderClass.getAnnotation(StatusNotificationType.class);
        LayoutRes layoutRes = holderClass.getAnnotation(LayoutRes.class);
        if (notificationType == null || layoutRes == null) {
            throw new IllegalArgumentException("missing annotation");
        }
        notificationViewHolders.put(notificationType.value(), holderClass);
    }

    public Class<? extends StatusNotificationViewHolder> getNotificationViewHolder(StatusNotification notification) {
        return notificationViewHolders.get(notification.getClass());
    }
}
