package com.facefy.facefy_api.services;

import java.util.List;

import com.facefy.facefy_api.exceptions.BadRequestException;
import com.facefy.facefy_api.exceptions.NotFoundException;
import com.facefy.facefy_api.models.Customer;
import com.facefy.facefy_api.models.Event;

public interface EventService {

	void buy(String customerId, Event event, String value) 
			throws NotFoundException, BadRequestException;
	
	List<Event> getAll();	
	List<Event> getEventsFromCustomer(String customerId) throws NotFoundException;
	List<Customer> getCustomersByEvent(String eventId) throws NotFoundException;
}
