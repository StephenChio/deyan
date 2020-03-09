package com.OneTech.common.config.webConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 配置网页图片映射
 */
@Configuration
public class WebResourceConfig implements WebMvcConfigurer {
    @Value("${localUrl}")
    public String url;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations("file://"+url+"img/");
        registry.addResourceHandler("/video/**").addResourceLocations("file://"+url+"video/");
    }
}
