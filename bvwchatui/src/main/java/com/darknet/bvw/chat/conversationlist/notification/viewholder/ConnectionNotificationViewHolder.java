package com.darknet.bvw.chat.conversationlist.notification.viewholder;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.darknet.bvw.chat.R;
import com.darknet.bvw.chat.annotation.LayoutRes;
import com.darknet.bvw.chat.annotation.StatusNotificationType;
import com.darknet.bvw.chat.conversationlist.notification.ConnectionStatusNotification;
import com.darknet.bvw.chat.conversationlist.notification.StatusNotification;

import butterknife.BindView;
import butterknife.OnClick;

@LayoutRes(resId = R.layout.conversationlist_item_notification_connection_status)
@StatusNotificationType(ConnectionStatusNotification.class)
public class ConnectionNotificationViewHolder extends StatusNotificationViewHolder {
    public ConnectionNotificationViewHolder(Fragment fragment) {
        super(fragment);
    }

    @BindView(R.id.statusTextView)
    TextView statusTextView;

    @Override
    public void onBind(View view, StatusNotification notification) {
        String status = ((ConnectionStatusNotification) notification).getValue();
        statusTextView.setText(status);
    }

    @OnClick(R.id.statusTextView)
    public void onClick() {
        Toast.makeText(fragment.getContext(), "status on Click", Toast.LENGTH_SHORT).show();
    }
}
