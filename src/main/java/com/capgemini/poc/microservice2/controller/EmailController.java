package com.capgemini.poc.microservice2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.poc.microservice2.services.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController 
{
	@Autowired
	private EmailService emailService;
	
	@RequestMapping("/send")
	public @ResponseStatus(HttpStatus.OK) void sendEmail(@RequestParam(required = true) String to, 
														 @RequestParam(required = true) String asunto, 
														 @RequestParam(required = true) String contenido)
	{
		emailService.sendEmail(to, asunto, contenido);
	}
}
