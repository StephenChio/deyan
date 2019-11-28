package com.OneTech.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 配置网页图片映射
 */
@Configuration
@Profile(value="prod")
public class prodWebResourceConfig implements WebMvcConfigurer {
    @Value("${localUrl}")
    public String url;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations(url+"img/");
        registry.addResourceHandler("/video/**").addResourceLocations(url+"video/");
    }
}
