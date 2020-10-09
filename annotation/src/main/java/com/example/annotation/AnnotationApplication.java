package com.example.annotation;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
@MapperScan({"com.example.annotation.mapper"})
@Slf4j
public class AnnotationApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(AnnotationApplication.class, args);
        Environment env = application.getEnvironment();
        log.info("http://127.0.0.1:{}", env.getProperty("server.port"));
    }

}
