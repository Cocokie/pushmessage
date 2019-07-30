package com.lix.pushmessage.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailSender {
    @Autowired
    JavaMailSender mailSender;
    @Value("${khsb.mail.from}")
    String from;

    public void sendEmail(String content, String to, String subject) {
        MimeMessage message = mailSender.createMimeMessage();
        // String content = "欢迎进入<a href=\"http://www.baidu.com\">百度首页</a>";
        int k =3;
        while (k >0) {
            try {
                MimeMessageHelper helper = new MimeMessageHelper(message, true);   //true表示需要创建一个multipart message
                helper.setFrom(from);
                helper.setTo(to);
                helper.setSubject(subject);
                helper.setText(content, true);
                mailSender.send(message);
                System.out.println("html邮件发送成功：" + from + " ------> " + to + "    subject:" + subject + "     content:" + content);
                break;
            } catch (Exception e) {
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                k--;
                System.out.println("发送html邮件时发生异常！" + e.getMessage() + "异常消息：" + from + " ------> " + to + "    subject:" + subject + "     content:" + content);
            }
        }
    }
}
