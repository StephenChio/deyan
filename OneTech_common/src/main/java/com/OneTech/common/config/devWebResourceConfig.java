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
@Profile(value="dev")
public class devWebResourceConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        java.net.URL url = this.getClass().getResource("/");
        registry.addResourceHandler("/img/**").addResourceLocations(url+"img/");
        registry.addResourceHandler("/video/**").addResourceLocations(url+"video/");
    }
}
