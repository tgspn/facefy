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
	public boolean buy(String customerId, String eventId) 
			throws NotFoundException, BadRequestException {
		Customer customer = getCustomer(customerId);
		Event event = eventRepository.findOne(eventId);
		
		if (event == null)
			throw new NotFoundException();
		
		String amount = Double.toString(event.getValue()).replace(".", "");
		
		boolean paymentSuccessful = zoopHandler.buy(customer, amount, "E_" + event.getEventId());

		if (!paymentSuccessful)
			throw new BadRequestException();

		List<Event> customerEvents = customer.getEvents() == null ? 
				new ArrayList<>() : customer.getEvents();

		Event existantEvent = eventRepository.findOne(event.getEventId());
		
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
		
		return paymentSuccessful;
	}
	
	@Override
	public Event get(String eventId) throws NotFoundException {
		Event event = eventRepository.findOne(eventId);
		
		if (event != null) {
			return event;
		}
		
		throw new NotFoundException();
	}

	@Override
	public List<Event> getAll() {
		//Os eventos disponíveis estão cadastrados na base de dados -
		//A ideia aqui é existir uma conexão com a API da Sympla para pegar
		// eventos locais. - Por ora a API não possui essa opção.
		return eventRepository.findAll();
	}
	
	private Customer getCustomer(String customerId) throws NotFoundException {
		Customer customer = customerRepository.findOne(customerId);
		
		if (customer != null) {
			return customer;
		}
		
		throw new NotFoundException();
	}
	
}
