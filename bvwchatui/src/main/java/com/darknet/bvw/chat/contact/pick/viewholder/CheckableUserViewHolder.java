package com.darknet.bvw.chat.contact.pick.viewholder;

import android.view.View;
import android.widget.CheckBox;

import androidx.fragment.app.Fragment;

import com.darknet.bvw.chat.R;
import com.darknet.bvw.chat.contact.model.UIUserInfo;
import com.darknet.bvw.chat.contact.pick.CheckableUserListAdapter;
import com.darknet.bvw.chat.contact.viewholder.UserViewHolder;

import butterknife.BindView;

public class CheckableUserViewHolder extends UserViewHolder {
    @BindView(R.id.checkbox)
    CheckBox checkBox;

    public CheckableUserViewHolder(Fragment fragment, CheckableUserListAdapter adapter, View itemView) {
        super(fragment, adapter, itemView);
    }

    @Override
    public void onBind(UIUserInfo userInfo) {
        super.onBind(userInfo);

        checkBox.setVisibility(View.VISIBLE);
        if (!userInfo.isCheckable()) {
            checkBox.setEnabled(false);
            checkBox.setChecked(true);
        } else {
            checkBox.setEnabled(true);
            checkBox.setChecked(userInfo.isChecked());
        }
        checkBox.setEnabled(userInfo.isCheckable());
    }
}
