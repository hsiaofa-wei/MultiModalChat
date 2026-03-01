package com.LVChat.ai.entity.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ai.chat.messages.Message;

@NoArgsConstructor
@Data
public class MessageVO {
    private String role; // 角色
    private String content;// 内容

    public MessageVO(Message message) {
        this.role = switch (message.getMessageType()) {
            case USER -> "user";  // 用户
            case ASSISTANT -> "assistant";// 助手
            case SYSTEM -> "system";// 系统
            default -> "";
        };
        this.content = message.getText();
    }
}