package com.facefy.facefy_api.models;

import java.util.List;

public class CustomerEvent {

	Event event;
	List<Customer> customers;
	
	public CustomerEvent(Event event, List<Customer> customers) {
		this.event = event;
		this.customers = customers;
	}
	
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public List<Customer> getCustomers() {
		return customers;
	}
	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	
	
}
