package com.cui;


import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
@ServletComponentScan
@MapperScan("com.cui.mapper")
public class ReggieApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ReggieApplication.class, args);
        log.info("项目启动成功......");
        System.out.println("aaaaaaa"+run.getBean(MybatisPlusInterceptor.class));
    }
}
