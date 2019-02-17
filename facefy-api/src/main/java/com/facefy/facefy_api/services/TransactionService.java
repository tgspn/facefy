package com.facefy.facefy_api.services;

import com.facefy.facefy_api.models.Transaction;

public interface TransactionService {
	
	Transaction[] findByCustomerId(String customerId);
}
