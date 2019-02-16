package com.facefy.facefy_api.services;

import com.facefy.facefy_api.exceptions.BadRequestException;
import com.facefy.facefy_api.exceptions.NotFoundException;
import com.facefy.facefy_api.models.Customer;

public interface CustomerService {

	Iterable<Customer> getAll(String masterKey) throws BadRequestException;

	Customer login(Customer customer) throws NotFoundException, BadRequestException;

	Customer findCustomer(String customerId);

	void saveCustomer(Customer customer);

	String createCustomer(Customer customer);

	boolean associateCustomerWithCard(String customerId, String customerCardId);
}
