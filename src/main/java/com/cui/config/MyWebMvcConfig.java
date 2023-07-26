package com.cui.config;


import com.cui.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Slf4j
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 自己定义了静态资源的路径，本来应该到classpath等路径下面进行寻找
        log.info("开始进行静态资源映射...");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 创建消息转换对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        // 设置对象转换器底层使用JACKJSON将java对象转化为json
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        // 将上面的消息转换器对象追加到mvc框架转换集合中
        converters.add(0,messageConverter);
    }
}
