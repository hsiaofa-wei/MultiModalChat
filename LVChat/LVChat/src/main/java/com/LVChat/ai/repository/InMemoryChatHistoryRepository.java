package com.LVChat.ai.repository;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component //这个注解是由spring框架提供的
public class InMemoryChatHistoryRepository implements ChatHistoryRepository{
    // 存储聊天记录
    private  final Map<String, List<String>> chatHistory = new HashMap<>();
    
    /**
     * 获取聊天记录
     * @param type
     * @return
     */
    @Override
    public List<String> getChatIds(String type) {
//        //根据业务类型获取会话id列表
//        List<String> chatIds = chatHistory.get(type);
//        if (chatIds == null){
//            return List.of();
//        }
//        return chatIds;
        return chatHistory.getOrDefault(type, List.of());
    }

}
