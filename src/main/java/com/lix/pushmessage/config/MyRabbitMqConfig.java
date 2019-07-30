package com.lix.pushmessage.config;


import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.xml.ws.handler.MessageContext;


@Configuration
@PropertySource(value = "classpath:rabbitconfig.properties")
@ConfigurationProperties(prefix = "rabbitmq")
public class MyRabbitMqConfig {
    private String exchange;
    private String queueNewMessage;
    private String queueWaringMessage;
    private String queueEstateMessage;


    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();

    }

    public String getQueueEstateMessage() {
        return queueEstateMessage;
    }

    public void setQueueEstateMessage(String queueEstateMessage) {
        this.queueEstateMessage = queueEstateMessage;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getQueueNewMessage() {
        return queueNewMessage;
    }

    public void setQueueNewMessage(String queueNewMessage) {
        this.queueNewMessage = queueNewMessage;
    }

    public String getQueueWaringMessage() {
        return queueWaringMessage;
    }

    public void setQueueWaringMessage(String queueWaringMessage) {
        this.queueWaringMessage = queueWaringMessage;
    }
}
