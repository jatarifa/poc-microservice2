package com.capgemini.poc.microservice2.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Event 
{
	private String event_id;
	private Map<String, Object> data = new HashMap<String, Object>();

	public Event() {}
	
	public Event(String event_id) {
		this.event_id = event_id;
	}
	
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	public void addData(String key, Object value) {
		this.data.put(key, value);
	}
	
	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}
	
	public String getEvent_id() {
		return event_id;
	}
	
	public Map<String, Object> getData() {
		return data;
	}
	
	public String toJSON() throws Exception
	{
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(this);
	}
	
	public static Event fromJSON(String json) throws Exception
	{
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, Event.class);
	}
}
