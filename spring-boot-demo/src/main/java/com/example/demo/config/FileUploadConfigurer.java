package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Created By ShenXi
 * @Created On 2019/3/25 21:33
 * @Description :
 * @ClassName : MyWebAppConfigurer
 */
@Configuration
public class FileUploadConfigurer implements WebMvcConfigurer {
    @Autowired
    Environment env;

    /**
     * @Param: [registry]
     * @return: void
     * @Date: On 2019/3/30 19:15
     * @Author: ShenXi
     * @Description
     **/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(env.getProperty("file.path"))
                .addResourceLocations(env.getProperty("file.pathLocations"));
        //super.addResourceHandlers(registry);
    }

}
