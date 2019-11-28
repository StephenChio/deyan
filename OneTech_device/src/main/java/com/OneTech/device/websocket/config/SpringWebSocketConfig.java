package com.OneTech.device.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.OneTech.device.websocket.handler.SpringWebSocketHandler;
import com.OneTech.device.websocket.interceptor.SpringWebSocketHandlerInterceptor;
@Configuration
@EnableWebSocket
public class SpringWebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(),"/websocket/socketServer")
                .addInterceptors(new SpringWebSocketHandlerInterceptor()).setAllowedOrigins("*");

        registry.addHandler(webSocketHandler(), "/sockjs/socketServer").setAllowedOrigins("http://localhost:28180")
               .addInterceptors(new SpringWebSocketHandlerInterceptor()).withSockJS();
    }

    @Bean
    public TextWebSocketHandler webSocketHandler(){

        return new SpringWebSocketHandler();
    }

}
