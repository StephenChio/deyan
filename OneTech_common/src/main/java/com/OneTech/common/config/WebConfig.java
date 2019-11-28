package com.OneTech.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 配置网页图片映射
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        java.net.URL url = this.getClass().getResource("/");
        registry.addResourceHandler("/img/**").addResourceLocations(url+"img/");
        registry.addResourceHandler("/video/**").addResourceLocations(url+"video/");
    }
}
