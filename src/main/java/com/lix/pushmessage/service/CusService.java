package com.lix.pushmessage.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lix.pushmessage.domain.NewMessage;
import com.lix.pushmessage.domain.WarningMessage;
import com.lix.pushmessage.utils.EmailSender;
import com.lix.pushmessage.utils.MyHttpClient;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;

@Service
public class CusService {
    @Value("${khsb.mail.from}")
    String from;
    @Value("${khsb.mail.newMessage.subject}")
    String newMessageSubject;
    @Autowired
    EmailSender emailSender;
    @Value("${khsb.mail.warningMessage.subject}")
    String warningMessageSubject;

    @RabbitListener(queues = "pushmessage.newMessage")
    @Transactional
    public void newMessageConsume(Message message, Channel channel) {
        String s = new String(message.getBody());
        NewMessage newMessage = JSON.parseObject(s, NewMessage.class);
        MyHttpClient m = new MyHttpClient();
        String target = newMessage.getTarget();
        JSONArray emailTo = m.getEmailTo(target);
        Iterator<Object> iterator = emailTo.iterator();
        // emailSender.sendEmail(newMessage.messageContent("test"), "574025890@qq.com", newMessageSubject);
        while (iterator.hasNext()) {
            JSONObject next = (JSONObject) iterator.next();
            String name = next.getString("name");
            // String phone = next.getString("phone");
            String email = next.getString("email");
            emailSender.sendEmail(newMessage.messageContent(name), email, newMessageSubject);
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(System.currentTimeMillis()) + " : " + newMessage);
    }

    @RabbitListener(queues = "pushmessage.warningMessage")
    public void warningMessageConsume(Message message, Channel channel) {
        String s = new String(message.getBody());
        WarningMessage warningageMessage = JSON.parseObject(s, WarningMessage.class);
        MyHttpClient m = new MyHttpClient();
        String target = warningageMessage.getTarget();
        JSONArray emailTo = m.getEmailTo(target);
        Iterator<Object> iterator = emailTo.iterator();
        while (iterator.hasNext()) {
            JSONObject next = (JSONObject) iterator.next();
            String name = next.getString("name");
            // String phone = next.getString("phone");
            String email = next.getString("email");
            emailSender.sendEmail(warningageMessage.messageContent(name), email, warningMessageSubject);
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(System.currentTimeMillis()) + " : " + warningageMessage);
    }
}
