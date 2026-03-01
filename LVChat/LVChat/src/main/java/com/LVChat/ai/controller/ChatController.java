package com.LVChat.ai.controller;

import cn.hutool.core.date.DateUtil;
import com.LVChat.ai.repository.ChatHistoryRepository;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.content.Media;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor //这个注解是由lombok提供的
@RestController
@RequestMapping("/ai")
public class ChatController {
    //    @Autowired//这个注解是由spring框架提供 加在变量上 表示根据变量的类型从spring容器中获取对象
//    private ChatClient chatClient;
//    @Resource //这个注解是由jdk 提供的依赖注入  他会根据变量名从spring容器中获取对象
//    private ChatClient chatClient;
//    @Resource //这个注解是由jdk 提供的依赖注入  他会根据变量名从spring容器中获取对象
//    private ChatClient chatClient2;
    private final ChatClient chatClient;
    private final ChatClient multiModalChatClient;
    private final ChatHistoryRepository chatHistoryRepository;
    private final VectorStore vectorStore;
    /**
     * 与聊天客户端进行交互，发送用户问题并获取响应内容。
     *
     * @param question 用户输入的问题内容
     * @return 聊天客户端返回的响应内容
     */
    @RequestMapping(value = "/embedding", produces = "text/html;charset=UTF-8")
    public String embedding(@RequestParam(defaultValue = "讲个笑话") String prompt,
                                  String chatId) {
        var qaAdvisor = QuestionAnswerAdvisor.builder(vectorStore)
                .searchRequest(SearchRequest.builder().query(prompt).topK(2).build())
                .build();

        return chatClient.prompt()
                .user(prompt) // 传入user提示词
                .system(s -> s.param("now", DateUtil.now()))
                .advisors(a ->
                        a.param(ChatMemory.CONVERSATION_ID, chatId)
                                .advisors(qaAdvisor))
                .call()   // 流式调用
                .content(); // 返回响应内容
    }

    /**
     * 搜索向量数据库
     *
     * @param query 搜索关键字
     */
    @PostMapping("/search")
    public List<Document> search(@RequestParam("query") String prompt) {
        return this.vectorStore.similaritySearch(SearchRequest.builder()
                .query(prompt) // 设置查询条件
                .topK(3) // 设置最多返回的文档数量
                .build());
    }

    /**
     * 聊天
     *
     * @param prompt
     * @return
     */
    @RequestMapping(value = "/chat", produces = "text/html;charset=utf-8")
    public Flux<String> chat(@RequestParam("prompt") String prompt,
                             @RequestParam("chatId")String chatId,
                             @RequestParam(value = "files", required = false) List<MultipartFile>  files) {
       if (files==null||files.isEmpty()){
           return textChat(prompt,chatId);
       }else {
           return multiModalChat(prompt,chatId,files);
       }

    }

    /**
     * 多模态聊天
     * @param prompt
     * @param chatId
     * @param files
     * @return
     */
    private Flux<String> multiModalChat(String prompt, String chatId, List<MultipartFile> files) {
        List<Media> medias = files.stream().map(
                file -> new Media(
                        MimeType.valueOf(
                                Objects.requireNonNull(file.getContentType())//获取文件类型
                        )
                        , file.getResource()//获取文件资源
                )
        ).toList();
        return multiModalChatClient.prompt()
                .user(p->p.text(prompt).media( medias.toArray(Media[]::new)))
                .advisors(a->a.param(ChatMemory.CONVERSATION_ID,chatId))
                .stream()// 开启流式输出 就是像打字机一样一个字一个字的输出
                .content();// 获取ai的输出结果
    }

    /**
     * 普通文本聊天
     * @param prompt 提问
     * @param chatId 会话id
     * @return
     */
    private Flux<String> textChat(String prompt, String chatId) {
        return chatClient.prompt()
                .user(prompt)// 传入用户提问问题
                .advisors(a->a.param(ChatMemory.CONVERSATION_ID,chatId))
                .stream()// 开启流式输出 就是像打字机一样一个字一个字的输出
                .content();// 获取ai的输出结果
    }


//    @RequestMapping(value = "/chat", produces = "text/html;charset=utf-8")
//    public Flux<String> chat(@RequestParam(defaultValue = "给我讲个笑话") String prompt,String chatId) {
//        chatHistoryRepository.save("chat",chatId);
//        return chatClient.prompt()
////                .system(s -> {
////                    s.param("date", DateUtil.now());
////                })
//                .user(prompt)// 传入用户提问问题
//                .advisors(a->a.param(ChatMemory.CONVERSATION_ID,chatId))
//                .stream()// 开启流式输出 就是像打字机一样一个字一个字的输出
//                .content();// 获取ai的输出结果
//
//    }
//    /**
//     * 聊天
//     * @param prompt
//     * @return
//     */
//    @RequestMapping("/chat")
//    public String chat(@RequestParam(defaultValue = "给我讲个笑话") String prompt) {
//        return chatClient.prompt()
//                .user(prompt)// 传入用户提问问题
//                .call()//同步请求 会等待ai全部输出完毕才会返回结果
//                .content();// 获取ai的输出结果
//
//    }
}
