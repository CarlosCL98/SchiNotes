package edu.eci.arsw.schinotes.controllers;

import org.springframework.beans.factory.annotation.Value;
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

    @Value("${broker.cloudamqp.relayHost}")
    private String relayHost;

    @Value("${broker.relayPort}")
    private int relayPort;

    @Value("${broker.cloudamqp.user}")
    private String user;

    @Value("${broker.cloudamqp.password}")
    private String password;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableStompBrokerRelay("/topic/").setRelayHost(relayHost).setRelayPort(relayPort).
                setClientLogin(user).
                setClientPasscode(password).
                setSystemLogin(user).
                setSystemPasscode(password).
                setVirtualHost(user);
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stompendpoint").setAllowedOrigins("*").withSockJS();
    }
}