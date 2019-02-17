package com.facefy.facefy_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.facefy.facefy_api.exceptions.NotFoundException;
import com.facefy.facefy_api.models.Event;
import com.facefy.facefy_api.services.EventService;

public class EventController {

	@Autowired
	EventService eventService;
	
	@RequestMapping(value = "/event", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Iterable<Event> getAll() {
		return eventService.getAll();
	}

	@RequestMapping(value = "/event/{eventId}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Event get(@PathVariable String eventId) throws NotFoundException {
		return eventService.get(eventId);
	}

	@RequestMapping(value = "/event/customer/{customerId}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Iterable<Event> getEventsFromCustomer(@PathVariable String customerId) 
			throws NotFoundException {
		return eventService.getEventsFromCustomer(customerId);
	}
	
	
}
