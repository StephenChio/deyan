package com.OneTech.device.websocket.config;

import com.OneTech.common.constants.WebSocketConstants;
import com.OneTech.device.websocket.interceptor.SpringWebSocketHandlerInterceptor;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.OneTech.device.websocket.handler.SpringWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
@Configuration
@EnableWebSocket
public class SpringWebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(), WebSocketConstants.WEBSOCKET_URL)
                .addInterceptors(new SpringWebSocketHandlerInterceptor()).setAllowedOrigins("*");

        registry.addHandler(webSocketHandler(), WebSocketConstants.JOCKJS_URL).setAllowedOrigins(WebSocketConstants.ALLOWED_ORIGIN)
                .addInterceptors(new SpringWebSocketHandlerInterceptor()).withSockJS();
    }

    @Bean
    public TextWebSocketHandler webSocketHandler() {

        return new SpringWebSocketHandler();
    }

}
