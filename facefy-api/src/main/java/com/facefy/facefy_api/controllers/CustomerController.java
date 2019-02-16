package com.facefy.facefy_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.facefy.facefy_api.models.Customer;
import com.facefy.facefy_api.services.CustomerService;

@RestController
public class CustomerController {

	@Autowired
	CustomerService userService;
	
	@RequestMapping(value = "/auth", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Customer get(@RequestBody Customer customer) {
		return null;
	}

	@RequestMapping(value = "/me", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Customer get(@RequestHeader(name="User-Id", defaultValue="", required = true) String customerId) {
		return null;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Customer create(@RequestBody Customer customer) {
		return null;
	}
	
	@RequestMapping(value = "/card/{cardId}", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Customer addCard(@RequestHeader(name="User-Id", defaultValue="", required = true) String customerId,
			@PathVariable String customerCard) {
		return null;
	}
		
}
