package com.iLVChat.ai;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.iLVChat.ai.mapper")
public class iLVChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(iLVChatApplication.class, args);
    }

}
