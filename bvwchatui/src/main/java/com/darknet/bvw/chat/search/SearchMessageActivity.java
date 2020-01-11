package com.darknet.bvw.chat.search;

import com.darknet.bvw.chat.search.module.ConversationMessageSearchModule;

import java.util.List;

import cn.wildfirechat.model.Conversation;

//import cn.wildfire.chat.kit.search.module.ConversationMessageSearchModule;

public class SearchMessageActivity extends SearchActivity {
    private Conversation conversation;

    @Override
    protected void beforeViews() {
        conversation = getIntent().getParcelableExtra("conversation");
    }

    @Override
    protected void initSearchModule(List<SearchableModule> modules) {
        modules.add(new ConversationMessageSearchModule(conversation));
    }
}
