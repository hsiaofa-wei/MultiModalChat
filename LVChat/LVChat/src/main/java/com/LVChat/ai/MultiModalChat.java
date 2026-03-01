package com.LVChat.ai;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.LVChat.ai.mapper")
public class MultiModalChat {

    public static void main(String[] args) {
        SpringApplication.run(MultiModalChat.class, args);
    }

}
