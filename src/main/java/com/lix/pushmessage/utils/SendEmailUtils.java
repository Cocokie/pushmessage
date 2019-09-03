package com.lix.pushmessage.utils;

import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(MailProperties.class)
public class SendEmailUtils extends JavaMailSenderImpl implements JavaMailSender {

    //保存多个用户名和密码的队列
    private ArrayList<String> usernameList;
    private ArrayList<String> passwordList;
    //轮询标识
    private int currentMailId = 0;

    private final MailProperties properties;

    public SendEmailUtils(MailProperties properties) {
        this.properties = properties;

        // 初始化账号
        if (usernameList == null)
            usernameList = new ArrayList<String>();
        String[] userNames = this.properties.getUsername().split(",");
        if (userNames != null) {
            for (String user : userNames) {
                usernameList.add(user);
            }
        }

        // 初始化密码
        if (passwordList == null)
            passwordList = new ArrayList<String>();
        String[] passwords = this.properties.getPassword().split(",");
        if (passwords != null) {
            for (String pw : passwords) {
                passwordList.add(pw);
            }
        }
    }

    @Override
    protected void doSend(MimeMessage[] mimeMessage, Object[] object) throws MailException {
        System.out.println("使用的账号" + usernameList.get(currentMailId));
        super.setUsername(usernameList.get(currentMailId));
        super.setPassword(passwordList.get(currentMailId));

        // 设置编码和各种参数
        super.setHost(this.properties.getHost());
        super.setDefaultEncoding(this.properties.getDefaultEncoding().name());
        super.setJavaMailProperties(asProperties(this.properties.getProperties()));

        super.doSend(mimeMessage, object);
        // 轮询
        changeId();
    }

    private Properties asProperties(Map<String, String> source) {
        Properties properties = new Properties();
        properties.putAll(source);
        return properties;
    }

    @Override
    public String getUsername() {
        //System.out.println(currentMailId);
        return usernameList.get(currentMailId);
    }

    public void changeId() {
        currentMailId = (currentMailId + 1) % usernameList.size();

    }
}