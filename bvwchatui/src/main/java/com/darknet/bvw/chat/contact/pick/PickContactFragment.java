package com.darknet.bvw.chat.contact.pick;

import androidx.lifecycle.ViewModelProviders;

import com.darknet.bvw.chat.contact.ContactViewModel;

public class PickContactFragment extends PickUserFragment {
    @Override
    protected void setupPickFromUsers() {
        ContactViewModel contactViewModel = ViewModelProviders.of(getActivity()).get(ContactViewModel.class);
        contactViewModel.contactListLiveData().observe(this, userInfos -> {
            showContent();
            pickUserViewModel.setUsers(userInfos);
            userListAdapter.setUsers(userInfos);
        });
    }
}
