package com.mittp.autotest.config;

import com.mittp.autotest.websocket.WebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


/**
 * @author zhouyang
 * 2019-07-03 13:53
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(handler(),"/webSocketServer");
    }

    @Bean
    public WebSocketHandler handler(){
        return  new WebSocketHandler();
    }
}
