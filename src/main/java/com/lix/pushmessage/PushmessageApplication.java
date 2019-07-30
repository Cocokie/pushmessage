package com.lix.pushmessage;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class PushmessageApplication {

    public static void main(String[] args) {
        SpringApplication.run(PushmessageApplication.class, args);
    }

}
