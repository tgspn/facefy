package com.facefy.facefy_api.services;

import java.util.List;

import com.facefy.facefy_api.exceptions.BadRequestException;
import com.facefy.facefy_api.exceptions.NotFoundException;
import com.facefy.facefy_api.models.CustomerEvent;
import com.facefy.facefy_api.models.Event;

public interface EventService {

	boolean buy(String customerId, String eventId) 
			throws NotFoundException, BadRequestException;
	List<Event> getAll();	
	CustomerEvent get(String eventId) throws NotFoundException;
	List<Event> getEventsFromCustomer(String customerId) throws NotFoundException;
	
}
