package com.facefy.facefy_api.services;

import com.facefy.facefy_api.exceptions.BadRequestException;
import com.facefy.facefy_api.exceptions.NotFoundException;
import com.facefy.facefy_api.models.Customer;

public interface CustomerService {

	Iterable<Customer> getAll(String masterKey) throws BadRequestException;

	Customer login(Customer customer) throws NotFoundException, BadRequestException;

	Customer getMe(String userId);
	
	void create(Customer customer);
	
	void addCard(String userId, String cardId);
}
