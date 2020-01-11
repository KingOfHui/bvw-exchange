package com.darknet.bvw.chat.contact.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.darknet.bvw.chat.R;
import com.darknet.bvw.chat.contact.UserListAdapter;
import com.darknet.bvw.chat.contact.model.UIUserInfo;
import com.darknet.bvw.chat.user.UserViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserViewHolder extends RecyclerView.ViewHolder {
    protected Fragment fragment;
    protected UserListAdapter adapter;
    @BindView(R.id.portraitImageView)
    ImageView portraitImageView;
    @BindView(R.id.nameTextView)
    TextView nameTextView;
    @BindView(R.id.categoryTextView)
    TextView categoryTextView;

    protected UIUserInfo userInfo;

    public UserViewHolder(Fragment fragment, UserListAdapter adapter, View itemView) {
        super(itemView);
        this.fragment = fragment;
        this.adapter = adapter;
        ButterKnife.bind(this, itemView);
    }

    public void onBind(UIUserInfo userInfo) {
        this.userInfo = userInfo;
        if (userInfo.isShowCategory()) {
            categoryTextView.setVisibility(View.VISIBLE);
            categoryTextView.setText(userInfo.getCategory());
        } else {
            categoryTextView.setVisibility(View.GONE);
        }
        UserViewModel userViewModel = ViewModelProviders.of(fragment).get(UserViewModel.class);
        nameTextView.setText(userViewModel.getUserDisplayName(userInfo.getUserInfo()));
        GlideApp.with(fragment).load(userInfo.getUserInfo().portrait).error(R.mipmap.default_header).into(portraitImageView);
    }

    public UIUserInfo getBindContact() {
        return userInfo;
    }
}
