package com.facefy.facefy_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.facefy.facefy_api.exceptions.BadRequestException;
import com.facefy.facefy_api.exceptions.NotFoundException;
import com.facefy.facefy_api.models.Transaction;
import com.facefy.facefy_api.services.impl.TransactionServiceImpl;

@RestController
public class TransactionController {

	@Autowired
	TransactionServiceImpl transactionService;

	@RequestMapping(value = "/transaction", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Transaction[] findAllByCustomerId(
			@RequestHeader(name = "Customer-Id", defaultValue = "", required = true) String customerId)
			throws NotFoundException, BadRequestException {
		Transaction[] transactionsArray = transactionService.findByCustomerId(customerId);
		for (Transaction transaction : transactionsArray) {
			String unformattedAmount = transaction.getAmount();
			String formattedAmount = unformattedAmount.substring(0, unformattedAmount.length() - 2)
					+ "." + unformattedAmount.substring(unformattedAmount.length() - 2);
			
			transaction.setAmount(formattedAmount);
		}
		return transactionService.findByCustomerId(customerId);
	}
}
