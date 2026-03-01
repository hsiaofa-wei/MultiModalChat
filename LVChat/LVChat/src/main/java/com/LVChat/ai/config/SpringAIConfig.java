package com.LVChat.ai.config;

import com.LVChat.ai.constants.Constant;
import com.LVChat.ai.tools.CourseTools;
import com.LVChat.ai.tools.WeatherTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration //这个注解是由springboot框架提供 只要在一个普通的java类上加了这个注解
// 那么表示当前类是一个配置累
//配置类作用 当项目启动的时候会自动加载配置类中的配置信息
public class SpringAIConfig {
    @Bean
    public ChatClient pdfChatClient(
            OpenAiChatModel model,
            Advisor simpleLoggerAdvisor,
            Advisor messageChatMemoryAdvisor,
            Advisor safeGuardAdvisor,
            Advisor questionAnswerAdvisor,
            VectorStore vectorStore) {
        return ChatClient.builder(model)
                .defaultSystem("请根据提供的上下文回答问题，不要自己猜测。")
                .defaultAdvisors(
                        simpleLoggerAdvisor,
                        messageChatMemoryAdvisor,
                        safeGuardAdvisor,
                        questionAnswerAdvisor
                )
                .build();
    }
    @Bean
    public Advisor questionAnswerAdvisor(VectorStore vectorStore) {
        return QuestionAnswerAdvisor.builder(vectorStore)// 创建一个QuestionAnswerAdvisor对象
                .searchRequest( SearchRequest.builder() // 向量检索的请求参数
                        .similarityThreshold(0.5d) // 相似度阈值
                        .topK(2) // 返回的文档片段数量
                        .build())
                .build();
    }
    @Bean
    public VectorStore vectorStore(OpenAiEmbeddingModel embeddingModel) {
        return SimpleVectorStore.builder(embeddingModel).build();
    }

    /**
     * 创建并返回一个ChatClient的serviceChatClient  Spring Bean实例。
     *
     * @param builder 用于构建ChatClient实例的构建者对象
     * @return 构建好的ChatClient实例
     */
    @Bean
    public ChatClient serviceChatClient(
            OpenAiChatModel model,
            Advisor simpleLoggerAdvisor,
            Advisor messageChatMemoryAdvisor,
            Advisor safeGuardAdvisor,
            CourseTools courseTools,
            WeatherTools weatherTools) {
        return ChatClient.builder(model)
                .defaultSystem(Constant.CUSTOMER_SERVICE_SYSTEM)
                .defaultAdvisors(
                        simpleLoggerAdvisor,
                        messageChatMemoryAdvisor,
                        safeGuardAdvisor)
                .defaultTools(courseTools, weatherTools)
                .build();
    }
//    @Bean
//    public ChatClient serviceChatClient(ChatClient.Builder builder,
//                                 Advisor simpleLoggerAdvisor,
//                                 Advisor messageChatMemoryAdvisor,
//                                 Advisor safeGuardAdvisor,
//                                 WeatherTools weatherTools
//    ) {
//        // 验证工具是否存在
//        System.out.println("WeatherTools bean created: " + (weatherTools != null));
//        return builder
//                .defaultSystem(Constant.SYSTEM_ROLE) // 设置默认的系统角色
//                .defaultAdvisors(simpleLoggerAdvisor, safeGuardAdvisor,messageChatMemoryAdvisor) // 设置默认的Advisor
//                .defaultTools(weatherTools) // 设置默认的Tool
//                .build();
//    }
    /**
     * 创建并返回一个chatClient的spring bean实例对象
     * @param builder
     * @param simpleLoggerAdvisor
     * @param messageChatMemoryAdvisor
     * @return
     */
    @Bean
    public ChatClient gameChatClient(ChatClient.Builder builder,
                                     Advisor simpleLoggerAdvisor,
                                     Advisor messageChatMemoryAdvisor) {
        return builder
                .defaultSystem(Constant.HONG_HONG_SYSTEM) // 设置默认的系统角色
                .defaultAdvisors(simpleLoggerAdvisor, messageChatMemoryAdvisor) // 设置默认的Advisor
                .build();
    }
    @Bean
    public ChatClient multiModalChatClient(OpenAiChatModel model,
                                           Advisor simpleLoggerAdvisor,
                                           Advisor messageChatMemoryAdvisor,
                                           Advisor safeGuardAdvisor){
        return ChatClient.builder(model) // 创建ChatClient工厂实例
                .defaultOptions(ChatOptions.builder().model("qwen-omni-turbo").build())
                .defaultSystem("请以友好、乐于助人和愉快的方式解答用户的各种问题。")
                .defaultAdvisors(simpleLoggerAdvisor, messageChatMemoryAdvisor, safeGuardAdvisor) // 设置默认的Advisor
                .build(); // 构建ChatClient实例

    }
    /**
     * 创建并返回一个chatClient的spring bean实例对象
     *
     * @param builder chatClient的builder对象 用于构架chatClient对象
     * @return 返回构建好的chatClient对象
     */
    @Bean //这个注解是由spring 框架提供 加在配置类中的方法上
    // 表示当前方法返回的对象会交给spring容器管理
    public ChatClient chatClient(ChatClient.Builder builder
            , Advisor simpleLoggerAdvisor,
                                 Advisor messageChatMemoryAdvisor,
                                 Advisor safeGuardAdvisor,
                                 WeatherTools weatherTools) {
        return builder
                .defaultAdvisors(
                        simpleLoggerAdvisor, //设置日志Advisor
                        messageChatMemoryAdvisor//设置回话记录Advisor
                        , safeGuardAdvisor
                )//设置Advisors
                .defaultTools(weatherTools)
                .defaultSystem(Constant.SYSTEM_ROLE)//设置系统角色
                .build();
    }

    /**
     * 创建并返回一个SimpleLoggerAdvisor的spring bean实例对象  日志Advisor
     *
     * @return
     */
    @Bean
    public Advisor simpleLoggerAdvisor() {
        return SimpleLoggerAdvisor.builder().build();
    }

    /**
     * 创建并返回一个ChatMemory的spring bean实例对象 chatMemory 实例对象
     *
     * @return
     */
    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder().build();
    }

    /**
     * 创建并返回一个MessageChatMemoryAdvisor的spring bean实例对象 添加回话记忆Advisor
     *
     * @return
     */
    @Bean
    public Advisor messageChatMemoryAdvisor(ChatMemory chatMemory) {
        return MessageChatMemoryAdvisor.builder(chatMemory)
                .build();
    }
    @Bean
    public Advisor safeGuardAdvisor() {
        // 敏感词列表（示例数据，建议实际使用时从配置文件或数据库读取）
        List<String> sensitiveWords = List.of("小可爱", "凡凡", "敏感词1");
        // 创建安全防护Advisor，参数依次为：敏感词库、违规提示语、advisor处理优先级，数字越小越优先
        return new SafeGuardAdvisor(
                sensitiveWords,
                "敏感词提示：请勿输入敏感词！",
                Advisor.DEFAULT_CHAT_MEMORY_PRECEDENCE_ORDER
        );
    }
}
