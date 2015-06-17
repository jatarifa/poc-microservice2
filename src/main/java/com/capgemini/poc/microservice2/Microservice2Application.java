package com.capgemini.poc.microservice2;

import static java.lang.System.getenv;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
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
        SpringApplication.run(new Object[]{
				Microservice2Application.class,
				EmailConfiguration.class,
				MetricsConfiguration.class
        }, args);
    }
	
	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange("microservices-exchange");
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queueName);
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
    public ConnectionFactory connectionFactory() {
        final URI ampqUrl;
        try {
            ampqUrl = new URI(getEnvOrThrow("CLOUDAMQP_URL"));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        final CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setUsername(ampqUrl.getUserInfo().split(":")[0]);
        factory.setPassword(ampqUrl.getUserInfo().split(":")[1]);
        factory.setHost(ampqUrl.getHost());
        factory.setPort(ampqUrl.getPort());
        factory.setVirtualHost(ampqUrl.getPath().substring(1));

        return factory;
    }
    
    private static String getEnvOrThrow(String name) {
        String env = getenv(name);
        if (env == null) {
        	env = System.getProperty(name);
        	if(env == null)
        		throw new IllegalStateException("Environment variable [" + name + "] is not set.");
        }
        return env;
    }
}
