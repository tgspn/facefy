package com.facefy.facefy_api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facefy.facefy_api.models.Transaction;
import com.facefy.facefy_api.repositories.TransactionRepository;
import com.facefy.facefy_api.services.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {
	
	TransactionRepository transactionRepository;
	
	@Autowired
	public TransactionServiceImpl(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}
	@Override
	public Transaction[] findByCustomerId(String customerId) {
		return transactionRepository.findByCustomerId(customerId);
	}
	
}
