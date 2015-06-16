package com.capgemini.poc.microservice2.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Receiver 
{
	private Log log = LogFactory.getLog(Receiver.class);
	
	public void receiveMessage(String message) 
	{
		log.debug("Received <" + message + ">");
	}
}
