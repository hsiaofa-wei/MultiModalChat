package com.LVChat.ai.tools.embedding;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 城市信息向量化处理组件
 * 在应用启动时将城市数据文件转换为向量并存储到向量数据库
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CityEmbedding {

    /**
     * 向量存储服务，用于持久化文档向量
     */
    private final VectorStore vectorStore;

    /**
     * 城市数据文件资源路径（classpath:citys.txt）
     */
    @Value("classpath:citys.txt")
    private Resource resource;

    /**
     * 应用启动时初始化方法
     * 1. 读取城市数据文件
     * 2. 拆分文本为小块文档
     * 3. 将拆分后的文档向量化并存储
     */
    @PostConstruct
    public void init() throws Exception {
        // 1. 创建文本读取器并加载文件内容
        TextReader textReader = new TextReader(this.resource);
        textReader.getCustomMetadata().put("filename", "citys.txt"); // 添加文件来源元数据

        // 2. 将文件内容拆分为小块文档
        List<Document> documentList = textReader.get();
        //参数分别是：默认分块大小、最小分块字符数、最小向量化长度（太小的忽略）、最大分块数量、不保留分隔符（\n啥的）
        TextSplitter textSplitter = new TokenTextSplitter(200, 100, 5, 10000, false);
        List<Document> splitDocuments = textSplitter.apply(documentList);

        // 3. 将处理后的文档向量化并存入向量存储
        this.vectorStore.add(splitDocuments);
        log.info("数据写入向量库成功，数据条数：{}", splitDocuments.size());
    }
}