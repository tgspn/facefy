package com.facefy.facefy_api.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.facefy.facefy_api.exceptions.NotFoundException;
import com.facefy.facefy_api.models.Customer;
import com.facefy.facefy_api.models.Event;
import com.facefy.facefy_api.repositories.CustomerRepository;
import com.facefy.facefy_api.repositories.EventRepository;
import com.facefy.facefy_api.services.EventService;

@Service
public class EventServiceImpl implements EventService {

	EventRepository eventRepository;
	CustomerRepository customerRepository;

	public EventServiceImpl(CustomerRepository customerRepository, 
			EventRepository eventRepository) {
		this.customerRepository = customerRepository;
		this.eventRepository = eventRepository;
	}

	@Override
	public List<Event> getEvents(String customerId) throws NotFoundException {
		Customer customer = getCustomer(customerId);
		
		if (customer != null) {
			return customer.getEvents();
		}
		
		throw new NotFoundException();
	}
	
	@Override
	public void buy(String customerId, Event event) {
		// TODO Auto-generated method stub	
	}
	
	private Customer getCustomer(String customerId) throws NotFoundException {
		Customer customer = customerRepository.findOne(customerId);
		
		if (customer != null) {
			return customer;
		}
		
		throw new NotFoundException();
	}

	@Override
	public List<Customer> getCustomersByEvent(String eventId) throws NotFoundException {
		Event event = eventRepository.findOne(eventId);
		
		if (event != null) {
			return event.getCustomers();
		}
		
		throw new NotFoundException();
	}
	
}
