package com.OneTech.device.interceptor.config;

import com.OneTech.device.interceptor.handler.SpringInterceptorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Configuration
public class SpringInterceptorConfig implements WebMvcConfigurer {

    @Bean
    public HandlerInterceptorAdapter springInterceptorHandler(){
        return new SpringInterceptorHandler();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(springInterceptorHandler())
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
    }
}
