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
				String msg = "Buenos dias, se ha solicitado una clave nueva : " + ev.getData().get("password") + ",\n" +
							 "con indice de clave : " + ev.getData().get("key") + "\n";
				
				email.sendEmail(ev.getData().get("email").toString(), from, "Cambio de password", msg);
			}
		}
	}
}
