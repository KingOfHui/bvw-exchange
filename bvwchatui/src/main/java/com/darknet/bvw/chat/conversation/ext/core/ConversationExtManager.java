package com.darknet.bvw.chat.conversation.ext.core;

import com.darknet.bvw.chat.conversation.ext.ExampleAudioInputExt;
import com.darknet.bvw.chat.conversation.ext.FileExt;
import com.darknet.bvw.chat.conversation.ext.ImageExt;
import com.darknet.bvw.chat.conversation.ext.LocationExt;
import com.darknet.bvw.chat.conversation.ext.ShootExt;
import com.darknet.bvw.chat.conversation.ext.VoipExt;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import cn.wildfirechat.model.Conversation;

public class ConversationExtManager {
    private static ConversationExtManager instance;
    private List<ConversationExt> conversationExts;

    private ConversationExtManager() {
        conversationExts = new ArrayList<>();
        init();
    }

    public static synchronized ConversationExtManager getInstance() {
        if (instance == null) {
            instance = new ConversationExtManager();
        }
        return instance;
    }

    private void init() {
        registerExt(ImageExt.class);
        registerExt(VoipExt.class);
        registerExt(ShootExt.class);
        registerExt(FileExt.class);
        registerExt(LocationExt.class);
        registerExt(ExampleAudioInputExt.class);
    }

    public void registerExt(Class<? extends ConversationExt> clazz) {
        Constructor constructor;
        try {
            constructor = clazz.getConstructor();
            ConversationExt ext = (ConversationExt) constructor.newInstance();
            conversationExts.add(ext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unregisterExt(Class<? extends ConversationExt> clazz) {
        // TODO
    }

    public List<ConversationExt> getConversationExts(Conversation conversation) {
        List<ConversationExt> currentExts = new ArrayList<>();
        for (ConversationExt ext : this.conversationExts) {
            if (!ext.filter(conversation)) {
                currentExts.add(ext);
            }
        }
        return currentExts;
    }
}
