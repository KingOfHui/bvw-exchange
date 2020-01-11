package com.darknet.bvw.chat.channel;

import com.darknet.bvw.chat.search.SearchActivity;
import com.darknet.bvw.chat.search.SearchableModule;
import com.darknet.bvw.chat.search.module.ChannelSearchModule;

import java.util.List;


public class SearchChannelActivity extends SearchActivity {
    @Override
    protected void initSearchModule(List<SearchableModule> modules) {
        modules.add(new ChannelSearchModule());
    }
}
