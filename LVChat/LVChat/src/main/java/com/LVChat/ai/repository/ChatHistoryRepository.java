package com.LVChat.ai.repository;

import java.util.List;

public interface ChatHistoryRepository {
    /**
     * 获取会话id列表
     * @param type 类型 如：chat service pdf
     * @return 会话id列表（集合）
     */
    List<String> getChatIds(String type);
}
