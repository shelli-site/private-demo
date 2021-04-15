package com.example.annotation;

import com.example.annotation.config.SocketServer;
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
        //起socket服务
        SocketServer server = new SocketServer();
        server.startSocketServer(8911);
        log.info("http://127.0.0.1:{}", env.getProperty("server.port"));
    }

}
