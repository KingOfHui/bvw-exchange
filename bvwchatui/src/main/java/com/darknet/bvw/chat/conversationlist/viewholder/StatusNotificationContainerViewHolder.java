package com.darknet.bvw.chat.conversationlist.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.darknet.bvw.R;
import com.darknet.bvw.chat.annotation.LayoutRes;
import com.darknet.bvw.chat.conversationlist.notification.StatusNotification;
import com.darknet.bvw.chat.conversationlist.notification.StatusNotificationManager;
import com.darknet.bvw.chat.conversationlist.notification.viewholder.StatusNotificationViewHolder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatusNotificationContainerViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.notificationContainerLayout)
    LinearLayout containerLayout;

    public StatusNotificationContainerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void onBind(Fragment fragment, View itemView, List<StatusNotification> statusNotifications) {
        LayoutInflater layoutInflater = LayoutInflater.from(fragment.getContext());

        containerLayout.removeAllViews();
        StatusNotificationViewHolder statusNotificationViewHolder;
        View view;
        for (StatusNotification notification : statusNotifications) {
            try {
                Class<? extends StatusNotificationViewHolder> holderClass = StatusNotificationManager.getInstance().getNotificationViewHolder(notification);
                Constructor constructor = holderClass.getConstructor(Fragment.class);
                statusNotificationViewHolder = (StatusNotificationViewHolder) constructor.newInstance(fragment);
                LayoutRes layoutRes = holderClass.getAnnotation(LayoutRes.class);
                view = layoutInflater.inflate(layoutRes.resId(), (ViewGroup) itemView, false);
                ButterKnife.bind(statusNotificationViewHolder, view);

                statusNotificationViewHolder.onBind(view, notification);
                containerLayout.addView(view);
                // TODO add divider
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
