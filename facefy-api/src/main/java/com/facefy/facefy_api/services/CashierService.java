package com.facefy.facefy_api.services;

import com.facefy.facefy_api.exceptions.NotFoundException;

public interface CashierService {

	boolean buy(String customerId, String eventId, String amount, String description) 
			throws NotFoundException;
}
