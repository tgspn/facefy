package com.facefy.facefy_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.facefy.facefy_api.exceptions.BadRequestException;
import com.facefy.facefy_api.exceptions.NotFoundException;
import com.facefy.facefy_api.models.Customer;
import com.facefy.facefy_api.services.CustomerService;

@RestController
public class CustomerController {

	@Autowired
	CustomerService customerService;
	
	@RequestMapping(value = "/customer/find", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Customer get(@RequestHeader(name = "Customer-Id", defaultValue = "", 
		required = true) String customerId) throws NotFoundException {
		return customerService.findCustomer(customerId);
	}
	
	@RequestMapping(value = "/customer", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Iterable<Customer> getByEvent(@RequestHeader(name="Master-Key", 
		defaultValue="", required = true) String masterKey) throws BadRequestException {
		return customerService.getAll(masterKey);
	}
	
	@RequestMapping(value = "/customer/create", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Customer> create(@RequestBody Customer customer) throws BadRequestException {
		customer = customerService.createCustomer(customer);
		return ResponseEntity.ok().body(customer);
	}

	@RequestMapping(value = "/customer/{customerId}/card/{cardId}", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public boolean associateCustomerWithCard(@PathVariable String customerId, 
			@PathVariable String cardId) throws NotFoundException {
		return customerService.associateCustomerWithCard(customerId, cardId);
	}
		
}
