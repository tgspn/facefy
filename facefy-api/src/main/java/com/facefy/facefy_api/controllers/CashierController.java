package com.facefy.facefy_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.facefy.facefy_api.exceptions.BadRequestException;
import com.facefy.facefy_api.exceptions.NotFoundException;
import com.facefy.facefy_api.services.CashierService;

@RestController
public class CashierController {
	
	@Autowired
	CashierService cashierService;

	@RequestMapping(value = "/cashier/{eventId}", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public boolean buy(@RequestHeader(name = "Customer-Id", defaultValue = "", 
			required = true) String customerId, @PathVariable String eventId,
			@RequestParam(name = "amount", defaultValue = "", required = true) String amount,
			@RequestParam(name = "description", defaultValue = "venda", required = false) String description) 
					throws NotFoundException, BadRequestException {
		return cashierService.buy(customerId, eventId, amount, description);
	}
}
