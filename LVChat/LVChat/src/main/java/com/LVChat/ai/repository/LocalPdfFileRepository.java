package com.LVChat.ai.repository;

import jakarta.annotation.PostConstruct; // Spring提供的注解，在Bean初始化后执行方法
import jakarta.annotation.PreDestroy;   // Spring提供的注解，在Bean销毁前执行方法
import lombok.RequiredArgsConstructor;     // Lombok注解，自动生成所有参数的构造函数
import lombok.extern.slf4j.Slf4j;       // Lombok注解，自动生成log实例
import org.springframework.ai.vectorstore.SimpleVectorStore; // Spring AI的简单向量存储实现
import org.springframework.ai.vectorstore.VectorStore;       // 向量存储接口
import org.springframework.core.io.FileSystemResource;      // 文件系统资源实现
import org.springframework.core.io.Resource;                // 资源接口
import org.springframework.stereotype.Component;             // 标识为Spring组件
import org.springframework.web.multipart.MultipartFile;     // 多部分文件上传接口

import java.io.*;                        // Java IO相关类
import java.nio.charset.StandardCharsets; // 标准字符编码
import java.nio.file.Files;              // 文件操作工具类
import java.time.LocalDateTime;          // 日期时间类
import java.util.Objects;                // 对象工具类
import java.util.Properties;             // 属性集合类

/**
 * 本地PDF文件仓库实现类
 * 实现了文件的持久化存储、检索以及向量数据库的持久化功能
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LocalPdfFileRepository implements FileRepository {

    // 注入的向量存储器，用于存储和检索PDF文档的向量表示
    private final VectorStore vectorStore;

    // 会话ID与文件名的对应关系映射，方便根据会话ID重新加载对应的文件
    private final Properties chatFiles = new Properties();

    /**
     * 保存文件到本地，并建立会话ID与文件名的映射关系
     *
     * @param chatId 会话ID，用于标识一次对话
     * @param resource 需要保存的文件资源
     * @return 保存成功返回true，否则返回false
     */
    @Override
    public boolean save(String chatId, Resource resource) {

        // 获取文件名并保存到本地磁盘
        String filename = resource.getFilename();
        
        // 检查文件是否已存在，不存在则进行复制
        File target = new File(Objects.requireNonNull(filename));
        if (!target.exists()) {
            try {
                // 将资源输入流复制到目标文件路径
                Files.copy(resource.getInputStream(), target.toPath());
            } catch (IOException e) {
                // 记录错误日志并返回失败状态
                log.error("Failed to save PDF resource.", e);
                return false;
            }
        }
        
        // 建立会话ID与文件名的映射关系并保存
        chatFiles.put(chatId, filename);
        return true;
    }

    /**
     * 根据会话ID获取对应的文件资源
     *
     * @param chatId 会话ID
     * @return 对应的文件资源对象
     */
    @Override
    public Resource getFile(String chatId) {
        // 从属性映射中获取文件名并创建文件系统资源对象
        return new FileSystemResource(chatFiles.getProperty(chatId));
    }

    /**
     * 初始化方法，在Bean创建完成后自动调用
     * 加载之前保存的会话-文件映射关系和向量数据
     */
    @PostConstruct
    private void init() {
        // 1.加载会话-文件映射关系
        FileSystemResource pdfResource = new FileSystemResource("chat-pdf.properties");
        if (pdfResource.exists()) {
            try {
                // 使用UTF-8编码加载属性文件
                chatFiles.load(new BufferedReader(new InputStreamReader(pdfResource.getInputStream(), StandardCharsets.UTF_8)));
            } catch (IOException e) {
                // 包装异常并抛出运行时异常
                throw new RuntimeException(e);
            }
        }
        
        // 2.加载向量存储数据
        FileSystemResource vectorResource = new FileSystemResource("chat-pdf.json");
        if (vectorResource.exists()) {
            // 将通用向量存储转换为简单向量存储以执行加载操作
            SimpleVectorStore simpleVectorStore = (SimpleVectorStore) vectorStore;
            simpleVectorStore.load(vectorResource);
        }
    }

    /**
     * 销毁前持久化方法，在Bean销毁前自动调用
     * 将当前的会话-文件映射关系和向量数据持久化到磁盘
     */
    @PreDestroy
    private void persistent() {
        try {
            // 保存会话-文件映射关系到属性文件，添加当前时间作为注释
            chatFiles.store(new FileWriter("chat-pdf.properties"), LocalDateTime.now().toString());
            
            // 保存向量存储数据到JSON文件
            SimpleVectorStore simpleVectorStore = (SimpleVectorStore) vectorStore;
            simpleVectorStore.save(new File("chat-pdf.json"));
        } catch (IOException e) {
            // 包装异常并抛出运行时异常
            throw new RuntimeException(e);
        }
    }
}