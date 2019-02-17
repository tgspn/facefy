package com.facefy.facefy_api.services.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facefy.facefy_api.exceptions.NotFoundException;
import com.facefy.facefy_api.models.Customer;
import com.facefy.facefy_api.models.Transaction;
import com.facefy.facefy_api.repositories.CustomerRepository;
import com.facefy.facefy_api.repositories.TransactionRepository;
import com.facefy.facefy_api.services.CashierService;
import com.facefy.facefy_api.zoop.ZoopHandler;

@Service
public class CashierServiceImpl implements CashierService {

	ZoopHandler zoopHandler;
	CustomerRepository customerRepository;
	TransactionRepository transactionRepository;

	@Autowired
	public CashierServiceImpl(CustomerRepository customerRepository, TransactionRepository transactionRepository) {
		this.customerRepository = customerRepository;
		this.transactionRepository = transactionRepository;
		this.zoopHandler = new ZoopHandler();
	}

	@Override
	public boolean buy(String customerId, String eventId, String amount, String description) throws NotFoundException {
		Customer customer = getCustomer(customerId);
		boolean success = zoopHandler.buy(customer, amount, description);
		
		if (success)
		{
			saveTransaction(customerId, amount);
		}
		return success;
	}

	public Customer getCustomer(String customerId) throws NotFoundException {
		Customer customer = customerRepository.findOne(customerId);

		if (customer != null) {
			return customer;
		}

		throw new NotFoundException();
	}

	public Customer saveTransaction(String customerId, String amount) throws NotFoundException {
		Customer customer = customerRepository.findOne(customerId);

		if (customer != null) {
			Transaction transaction = new Transaction();
			transaction.setCustomerId(customerId);
			transaction.setAmount(amount);
			transaction.setDate(getDate());
			transactionRepository.save(transaction);
			return customer;
		}
		throw new NotFoundException();
	}
	
	private String getDate()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		java.util.Date date = new java.util.Date();
		return dateFormat.format(date);
	}

}
