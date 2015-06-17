package com.capgemini.poc.microservice2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfiguration 
{
    @Value("${mail.host}")
    private String host;

    @Value("${mail.port}")
    private int port;

    @Value("${mail.username}")
    private String username;
    
    @Value("${mail.password}")
    private String password;
    
    @Value("${mail.auth}")
    private String auth;
    
    @Value("${mail.debug}")
    private String debug;
    
    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        javaMailSender.getJavaMailProperties().setProperty("mail.smtp.socketFactory.port", Integer.toString(port));
        javaMailSender.getJavaMailProperties().setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailSender.getJavaMailProperties().setProperty("mail.smtp.auth", auth);
        javaMailSender.getJavaMailProperties().setProperty("mail.debug", debug);
        
        return javaMailSender;
    }
}
