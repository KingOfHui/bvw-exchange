package com.darknet.bvw.chat.search;

import com.darknet.bvw.chat.search.module.ChannelSearchModule;
import com.darknet.bvw.chat.search.module.ContactSearchModule;
import com.darknet.bvw.chat.search.module.ConversationSearchModule;
import com.darknet.bvw.chat.search.module.GroupSearchViewModule;

import java.util.List;

public class SearchPortalActivity extends SearchActivity {
    @Override
    protected void initSearchModule(List<SearchableModule> modules) {

        SearchableModule module = new ContactSearchModule();
        modules.add(module);

        module = new GroupSearchViewModule();
        modules.add(module);

        module = new ConversationSearchModule();
        modules.add(module);
        modules.add(new ChannelSearchModule());
    }
}
