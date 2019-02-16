package com.facefy.facefy_api.services;

import com.facefy.facefy_api.models.Customer;

public interface CustomerService {

	Customer getMe(String userId);
	
	void create(Customer customer);
	
	void addCard(String userId, String cardId);
}
