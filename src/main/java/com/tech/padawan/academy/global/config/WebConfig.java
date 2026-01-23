package com.tech.padawan.academy.global.config;

import com.tech.padawan.academy.global.AcademyConfiguration;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private String MEDIA_URL;

    public WebConfig(AcademyConfiguration configuration){
        this.MEDIA_URL = configuration.media_path();
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry
                .addResourceHandler("/arquivos/**")
                .addResourceLocations("file:" + MEDIA_URL + "/IMAGE/");
    }
}
