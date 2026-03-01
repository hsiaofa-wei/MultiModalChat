package com.LVChat.ai.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "ai")
public class AiProperties {
    private List<String> sensitiveWords;
    
    public List<String> getSensitiveWords() {
        return sensitiveWords;
    }
    
    public void setSensitiveWords(List<String> sensitiveWords) {
        this.sensitiveWords = sensitiveWords;
    }
}
