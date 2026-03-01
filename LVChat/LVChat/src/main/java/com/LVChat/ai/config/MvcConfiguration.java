package com.LVChat.ai.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class MvcConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
      registry.addMapping("/**")// 允许跨域访问的接口
              .allowedOrigins("*")// 允许跨域访问的源
              .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")// 允许跨域访问的方法
              .allowedHeaders("*")// 允许跨域访问的请求头
              .exposedHeaders("Content-Disposition");// 允许跨域访问的响应头
    }
}
