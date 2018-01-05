package com.shrralis.ssdemo1.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry.addEndpoint("/chat").setAllowedOrigins("*").withSockJS();
    }

    public void configureWebSocketTransport(WebSocketTransportRegistration webSocketTransportRegistration) {

    }

    public void configureClientInboundChannel(ChannelRegistration channelRegistration) {

    }

    public void configureClientOutboundChannel(ChannelRegistration channelRegistration) {

    }

    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> list) {

    }

    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> list) {

    }

    public boolean configureMessageConverters(List<MessageConverter> list) {
        return true;
    }

    public void configureMessageBroker(MessageBrokerRegistry messageBrokerRegistry) {
        messageBrokerRegistry.setApplicationDestinationPrefixes("/app");
        messageBrokerRegistry.enableSimpleBroker("/topic", "/checkTopic");
    }
}
