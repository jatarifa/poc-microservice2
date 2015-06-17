package com.capgemini.poc.microservice2.services;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailService
{
	private Logger log = Logger.getLogger(EmailService.class);
	
	@Autowired
    private JavaMailSender mailSender;
	
	public void sendEmail(String email_to, String email_from, String asunto, String contenido)
	{
		try
		{
			final MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(email_from);
			helper.setSubject(asunto);
			helper.setTo(email_to);
			helper.setText(contenido, true);

			sendMessageAsync(message);
		}
		catch(Exception e) 
		{
			log.error("No se ha podido enviar el mensaje.", e);
		}				
	}		
	
	protected void sendMessageAsync(final MimeMessage message)
	{
		new Thread()
		{
			@Override
			public void run() 
			{
				try
				{
					mailSender.send(message);
					log.debug("Mensaje enviado : " + message.getSentDate());
				}
				catch(Exception e) 
				{
					log.error("No se ha podido enviar el mensaje.", e);
				}								
			}
		}.start();
	}
}