package com.capgemini.poc.microservice2.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.capgemini.poc.microservice2.model.Event;
import com.capgemini.poc.microservice2.services.EmailService;

@Component
public class Receiver 
{
	private Log log = LogFactory.getLog(Receiver.class);
	
	@Autowired
	private EmailService email;
	
    @Value("${mail.from}")
    private String from;
    
	public void receiveMessage(Object message) throws Exception
	{
		if(message instanceof String)
		{
			log.debug("Received <" + message + ">");
			
			Event ev = Event.fromJSON((String)message);
			if(ev.getEvent_id().equalsIgnoreCase("passwordGeneration"))
			{
				String to = (String) ev.getData().get("email");
				String subject = (String) ev.getData().get("subject");
				String content = (String) ev.getData().get("content");
				email.sendEmail(to, from, subject, content);
			}
		}
	}
}
