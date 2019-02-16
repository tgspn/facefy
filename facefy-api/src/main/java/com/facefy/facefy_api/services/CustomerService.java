package com.facefy.facefy_api.services;

import com.facefy.facefy_api.exceptions.NotFoundException;
import com.facefy.facefy_api.models.Customer;
import com.facefy.facefy_api.exceptions.BadRequestException;

public interface CustomerService {

	Customer login(Customer customer) throws NotFoundException, BadRequestException;

	Customer getMe(String userId);
	
	void create(Customer customer);
	
	void addCard(String userId, String cardId);
}
