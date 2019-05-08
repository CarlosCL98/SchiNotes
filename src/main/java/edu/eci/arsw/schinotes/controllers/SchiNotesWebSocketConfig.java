package edu.eci.arsw.schinotes.controllers;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 *
 * @author Daniel Rosales
 * @author Carlos Medina
 */
@Configuration
@EnableWebSocketMessageBroker
public class SchiNotesWebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        /*config.enableStompBrokerRelay("/topic/").setRelayHost("moose.rmq.cloudamqp.com").setRelayPort(61613)
                .setClientLogin("cayumjwz").setClientPasscode("GBsaLlE828vd2w8LruiQ7IzSMbnlZwBO")
                .setSystemLogin("cayumjwz").setSystemPasscode("GBsaLlE828vd2w8LruiQ7IzSMbnlZwBO")
                .setVirtualHost("cayumjwz");*/

        config.setApplicationDestinationPrefixes("/app");

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stompendpoint").setAllowedOrigins("*").withSockJS();   
    }
}