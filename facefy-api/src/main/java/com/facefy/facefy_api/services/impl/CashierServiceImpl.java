package com.facefy.facefy_api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.facefy.facefy_api.exceptions.NotFoundException;
import com.facefy.facefy_api.models.Customer;
import com.facefy.facefy_api.repositories.CustomerRepository;
import com.facefy.facefy_api.services.CashierService;
import com.facefy.facefy_api.zoop.ZoopHandler;

public class CashierServiceImpl implements CashierService {

	ZoopHandler zoopHandler;
	CustomerRepository customerRepository;
	
	@Autowired
	public CashierServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
		this.zoopHandler = new ZoopHandler();
	}

	@Override
	public boolean buy(String customerId, String amount, String description) 
			throws NotFoundException {
		Customer customer = getCustomer(customerId);
		boolean success = zoopHandler.buy(customer, amount, description);
		return success;
	}
	
	private Customer getCustomer(String customerId) throws NotFoundException {
		Customer customer = customerRepository.findOne(customerId);
		
		if (customer != null) {
			return customer;
		}
		
		throw new NotFoundException();
	}

}
