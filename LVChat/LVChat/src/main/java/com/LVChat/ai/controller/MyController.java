package com.LVChat.ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    // 注入chatClient
    private final ChatClient chatClient;

    // 通过构造函数注入chatClient
    public MyController(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build(); // 通过build方法创建chatClient
    }
    @GetMapping("/ai")
    String generation(String userInput) {
        return this.chatClient.prompt() // 创建一个prompt
                .user(userInput)// 传入用户提问问题
                .call() //同步请求 会等待ai全部输出完毕才会返回结果
                .content();// 获取ai的输出结果
    }
}
