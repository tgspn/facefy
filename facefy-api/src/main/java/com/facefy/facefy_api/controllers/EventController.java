package com.facefy.facefy_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.facefy.facefy_api.exceptions.BadRequestException;
import com.facefy.facefy_api.exceptions.NotFoundException;
import com.facefy.facefy_api.models.CustomerEvent;
import com.facefy.facefy_api.models.Event;
import com.facefy.facefy_api.services.EventService;

@RestController
public class EventController {

	@Autowired
	EventService eventService;
	
	@RequestMapping(value = "/event", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Iterable<Event> getAll() {
		return eventService.getAll();
	}
	
	@RequestMapping(value = "/event/{eventId}", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public boolean buy(@RequestHeader(name = "User-Id", defaultValue = "", 
			required = true) String customerId, @PathVariable String eventId) 
					throws NotFoundException, BadRequestException {
		return eventService.buy(customerId, eventId);
	}

	@RequestMapping(value = "/event/{eventId}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public CustomerEvent get(@PathVariable String eventId) throws NotFoundException {
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
