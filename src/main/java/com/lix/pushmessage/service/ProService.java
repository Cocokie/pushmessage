package com.lix.pushmessage.service;


import com.lix.pushmessage.config.MyRabbitMqConfig;
import com.lix.pushmessage.domain.NewMessage;
import com.lix.pushmessage.domain.WarningMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProService {

    @Autowired
    MyRabbitMqConfig myRabbitMqConfig;
    @Autowired
    RabbitTemplate rabbitTemplate;

    public String sendNewMessage(NewMessage message) {
        rabbitTemplate.convertAndSend(myRabbitMqConfig.getExchange(), "newMessage", message);
        return "成功";
    }

    public String sendWarningMessage(WarningMessage message) {
        rabbitTemplate.convertAndSend(myRabbitMqConfig.getExchange(), "warningMessage", message);
        return "成功";
    }
   /* @Autowired
    JdbcTemplate jdbc;
    @Autowired
    RabbitTemplate rabbitTemplate;
    public void sendMessage(){

    }*/
}
