package com.ecobank.app.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws") // 엔드포인트 URL
		        .setAllowedOrigins("http://localhost:8080") // 
		        .withSockJS(); //WebSockek을 지원하지 않는 브라우저에서도 기능을 가능하게
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic"); //메시지를 받을 때 경로를 설정 
		config.setApplicationDestinationPrefixes("/app"); // 메시지를 보낼(publish) 경로를 설정
		config.setUserDestinationPrefix("/user"); // 특정 사용자에게 메시지 전송시 사용할 경로
	}
	
}
