package com.facefy.facefy_api.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.facefy.facefy_api.exceptions.BadRequestException;
import com.facefy.facefy_api.exceptions.NotFoundException;
import com.facefy.facefy_api.models.Customer;
import com.facefy.facefy_api.models.Event;
import com.facefy.facefy_api.repositories.CustomerRepository;
import com.facefy.facefy_api.repositories.EventRepository;
import com.facefy.facefy_api.services.EventService;
import com.facefy.facefy_api.zoop.ZoopHandler;

@Service
public class EventServiceImpl implements EventService {

	EventRepository eventRepository;
	CustomerRepository customerRepository;
	
	ZoopHandler zoopHandler;

	public EventServiceImpl(CustomerRepository customerRepository, 
			EventRepository eventRepository) {
		this.customerRepository = customerRepository;
		this.eventRepository = eventRepository;
		
		this.zoopHandler = new ZoopHandler();
	}

	@Override
	public List<Event> getEventsFromCustomer(String customerId) throws NotFoundException {
		Customer customer = getCustomer(customerId);
		
		if (customer != null) {
			return customer.getEvents();
		}
		
		throw new NotFoundException();
	}
	
	@Override
	public void buy(String customerId, Event event, String amount) 
			throws NotFoundException, BadRequestException {
		Customer customer = getCustomer(customerId);
		
		boolean paymentSuccessful = zoopHandler.buy(customer, amount, "E_" + event.getId());

		if (!paymentSuccessful)
			throw new BadRequestException();

		List<Event> customerEvents = customer.getEvents() == null ? 
				new ArrayList<>() : customer.getEvents();

		Event existantEvent = eventRepository.findOne(event.getId());
		
		if (existantEvent != null) {
			List<Customer> customers = existantEvent.getCustomers() == null ?
					new ArrayList<>() : existantEvent.getCustomers();
			customers.add(customer);
			existantEvent.setCustomers(customers);
			eventRepository.save(existantEvent);
		} else {
			List<Customer> customers = new ArrayList<>();
			customers.add(customer);
			event.setCustomers(customers);
			eventRepository.save(event);
		}
		
		customerEvents.add(event);
		customer.setEvents(customerEvents);

		customerRepository.save(customer);
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

	@Override
	public List<Event> getAll() {
		return eventRepository.findAll();
	}
	
}
