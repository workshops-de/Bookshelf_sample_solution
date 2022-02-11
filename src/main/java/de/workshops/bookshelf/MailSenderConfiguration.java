package de.workshops.bookshelf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;



@Configuration
public class MailSenderConfiguration {

    @Profile("test")
    @Bean
    JavaMailSender testJavaMailSender() {
        var javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("test.example.com");
        return javaMailSender;
    }

    @Profile("prod")
    @Bean
    JavaMailSender prodJavaMailSender() {
        var javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("prod.example.com");
        return javaMailSender;
    }
}
