package com.lix.pushmessage;

import com.lix.pushmessage.config.MyRabbitMqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest

public class PushmessageApplicationTests {
    @Autowired
    RabbitAdmin rabbitAdmin;
    @Autowired
    MyRabbitMqConfig myRabbitMqConfig;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    JavaMailSender mailSender;

    @Test
    public void sendEmail() {
        MimeMessage message = mailSender.createMimeMessage();
        String from = "info@qidianbd.com";
        String to = "1254759600@qq.com";
       // String to = "yt078912@163.com";
        String subject = "test";
        String content = "欢迎进入<a href=\"http://www.baidu.com\">百度首页</a>";
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);   //true表示需要创建一个multipart message
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
            System.out.println("html邮件发送成功");
        } catch (MessagingException e) {
            System.out.println("发送html邮件时发生异常！" + e);
        }
    }

    @Test
    public void contextLoads() {
        //rabbitAdmin.declareExchange(new DirectExchange(myRabbitMqConfig.getExchange()));
        /*rabbitAdmin.declareQueue(new Queue(myRabbitMqConfig.getQueueNewMessage()));
        rabbitAdmin.declareQueue(new Queue(myRabbitMqConfig.getQueueWaringMessage()));*/
        rabbitAdmin.declareQueue(new Queue(myRabbitMqConfig.getQueueEstateMessage()));
        rabbitAdmin.declareBinding(new Binding(myRabbitMqConfig.getQueueEstateMessage(), Binding.DestinationType.QUEUE,myRabbitMqConfig.getExchange(),"estateMessage",null));
        //rabbitAdmin.declareBinding(new Binding(myRabbitMqConfig.getQueueNewMessage(),Binding.DestinationType.QUEUE,myRabbitMqConfig.getExchange(),"newMessage",null));
        //rabbitAdmin.declareBinding(new Binding(myRabbitMqConfig.getQueueWaringMessage(), Binding.DestinationType.QUEUE, myRabbitMqConfig.getExchange(), "warningMessage", null));
    }

    @Test
    public void send() {
        Map m = new HashMap();
        m.put("li", "xin");
        m.put("cao", "f");
        rabbitTemplate.convertAndSend("exchange.pushmessage", "", m);
    }

    @Test
    public void recive() {

        Object o = rabbitTemplate.receiveAndConvert(myRabbitMqConfig.getQueueNewMessage());
        System.out.println(o.toString());
    }
}
