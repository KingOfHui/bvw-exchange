package com.darknet.bvw.chat.contact.pick;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TypefaceTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.darknet.bvw.chat.R;
import com.darknet.bvw.chat.contact.UserListAdapter;
import com.darknet.bvw.chat.contact.model.UIUserInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchAndPickUserFragment extends Fragment implements UserListAdapter.OnUserClickListener {
    private CheckableUserListAdapter contactAdapter;
    private PickUserViewModel pickUserViewModel;
    private PickUserFragment pickUserFragment;

    @BindView(R.id.usersRecyclerView)
    RecyclerView contactRecyclerView;
    @BindView(R.id.tipTypefaceTextView)
    TypefaceTextView tipTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_search_fragment, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    public void setPickUserFragment(PickUserFragment pickUserFragment) {
        this.pickUserFragment = pickUserFragment;
    }


    @OnClick(R.id.tipTextView)
    void onTipTextViewClick() {
        pickUserFragment.hideSearchContactFragment();
    }

    private void init() {
        pickUserViewModel = ViewModelProviders.of(getActivity()).get(PickUserViewModel.class);
        contactAdapter = new CheckableUserListAdapter(this);
        contactAdapter.setOnUserClickListener(this);
        contactRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        contactRecyclerView.setAdapter(contactAdapter);
    }

    public void search(String keyword) {
        if (TextUtils.isEmpty(keyword)) {
            return;
        }
        List<UIUserInfo> result = pickUserViewModel.searchUser(keyword);
        if (result == null || result.isEmpty()) {
            contactRecyclerView.setVisibility(View.GONE);
            tipTextView.setVisibility(View.VISIBLE);
        } else {
            contactRecyclerView.setVisibility(View.VISIBLE);
            tipTextView.setVisibility(View.GONE);
        }
        contactAdapter.setUsers(result);
        contactAdapter.notifyDataSetChanged();
    }

    public void rest() {
        tipTextView.setVisibility(View.VISIBLE);
        contactRecyclerView.setVisibility(View.GONE);
        contactAdapter.setUsers(null);
    }

    @Override
    public void onUserClick(UIUserInfo userInfo) {
        if (userInfo.isCheckable()) {
            pickUserViewModel.checkUser(userInfo, !userInfo.isChecked());
            // the checked status has already changed by checkContact method
            contactAdapter.updateUserStatus(userInfo);
        }
    }
}
