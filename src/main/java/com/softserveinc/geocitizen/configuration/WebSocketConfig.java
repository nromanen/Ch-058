package com.softserveinc.geocitizen.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
		stompEndpointRegistry.addEndpoint("/api/chat").setAllowedOrigins("*").withSockJS();
	}

	@Override
	public boolean configureMessageConverters(List<MessageConverter> list) {
		return true;
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry messageBrokerRegistry) {
		messageBrokerRegistry.setApplicationDestinationPrefixes("/app");
		messageBrokerRegistry.enableSimpleBroker("/api/topic", "/api/checkTopic");
	}
}
