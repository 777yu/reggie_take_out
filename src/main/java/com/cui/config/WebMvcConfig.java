//package com.cui.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
//@Slf4j
//@Configuration
//public class WebMvcConfig extends WebMvcConfigurationSupport {
//    @Override
//    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//        // 自己定义了静态资源的路径，本来应该到classpath等路径下面进行寻找
//        log.info("开始进行静态资源映射...");
//        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
//    }
//}
