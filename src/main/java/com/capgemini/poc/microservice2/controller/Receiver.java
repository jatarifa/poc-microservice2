package com.capgemini.poc.microservice2.controller;

public class Receiver 
{
	public void receiveMessage(String message) 
	{
		System.out.println("Received <" + message + ">");
	}
}
