package com.darknet.bvw.chat.contact.newfriend;

import com.darknet.bvw.chat.search.SearchActivity;
import com.darknet.bvw.chat.search.SearchableModule;

import java.util.List;


public class SearchUserActivity extends SearchActivity {

    @Override
    protected void beforeViews() {
    }

    @Override
    protected void initSearchModule(List<SearchableModule> modules) {
        modules.add(new UserSearchModule());
    }
}
