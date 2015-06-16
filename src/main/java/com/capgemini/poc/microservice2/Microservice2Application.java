package com.capgemini.poc.microservice2;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.capgemini.poc.microservice2.controller.Receiver;

@SpringBootApplication
public class Microservice2Application {

	final static String queueName = "microservices_arch";
	
    public static void main(String[] args) {
        SpringApplication.run(Microservice2Application.class, args);
    }
	
	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver) 
	{
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}
	
	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);
		container.setMessageListener(listenerAdapter);
		return container;
	}

    @Bean
    Receiver receiver() {
        return new Receiver();
    }
}
