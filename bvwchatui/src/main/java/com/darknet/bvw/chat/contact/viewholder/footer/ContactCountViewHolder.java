package com.darknet.bvw.chat.contact.viewholder.footer;

import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.darknet.bvw.chat.R;
import com.darknet.bvw.chat.annotation.LayoutRes;
import com.darknet.bvw.chat.contact.UserListAdapter;
import com.darknet.bvw.chat.contact.model.ContactCountFooterValue;

import butterknife.BindView;
import butterknife.ButterKnife;

@LayoutRes(resId = R.layout.contact_item_footer)
public class ContactCountViewHolder extends FooterViewHolder<ContactCountFooterValue> {
    @BindView(R.id.countTextView)
    TextView countTextView;
    private UserListAdapter adapter;

    public ContactCountViewHolder(Fragment fragment, UserListAdapter adapter, View itemView) {
        super(fragment, adapter, itemView);
        this.adapter = adapter;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBind(ContactCountFooterValue contactCountFooterValue) {
        int count = adapter.getContactCount();
        if (count == 0) {
            countTextView.setText("没有联系人");
        } else {
            countTextView.setText(count + "位联系人");
        }
    }
}
