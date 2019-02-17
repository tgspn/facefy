package com.facefy.facefy_api.services.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facefy.facefy_api.exceptions.BadRequestException;
import com.facefy.facefy_api.exceptions.NotFoundException;
import com.facefy.facefy_api.models.Customer;
import com.facefy.facefy_api.models.CustomerCard;
import com.facefy.facefy_api.repositories.CustomerRepository;
import com.facefy.facefy_api.services.CustomerService;
import com.facefy.facefy_api.zoop.ZoopHandler;

@Service
public class CustomerServiceImpl implements CustomerService {

	CustomerRepository customerRepository;
	MessageDigest md;
	
	ZoopHandler zoopHandler;

	@Autowired
	public CustomerServiceImpl(CustomerRepository customerRepository) 
			throws NoSuchAlgorithmException {
		this.customerRepository = customerRepository;
		md = MessageDigest.getInstance("MD5");
		
		this.zoopHandler = new ZoopHandler();
	}

	@Override
	public Customer login(Customer customer) throws NotFoundException, BadRequestException {
		if (customer.getUsername() == null || customer.getPassword() == null) {
			throw new BadRequestException();
		}
		
		String pwdMd5 = new String(md.digest(customer.getPassword().getBytes()));
		
		Optional<Customer> opt = customerRepository.findByUsernameAndPassword(customer.getUsername(),
				pwdMd5);
		
		if (opt.isPresent()) {
			return opt.get();
		}
		
		throw new NotFoundException();
	}

	@Override
	public Iterable<Customer> getAll(String masterKey) throws BadRequestException {
		if (masterKey != "facefyiscool")
			throw new BadRequestException();
		
		return customerRepository.findAll();
	}

	@Override
	public Customer createCustomer(Customer customer) throws BadRequestException {
		String id = zoopHandler.createCustomer(customer);
		
		if (!id.isEmpty()) {
			customer.setCustomerId(id);
			customerRepository.save(customer);
			
			return customer;
		}
		
		throw new BadRequestException();
	}

	@Override
	public Customer findCustomer(String customerId) throws NotFoundException {
		Customer customer = customerRepository.findOne(customerId);
		
		if (customer != null) {
			return customer;
		}
		
		throw new NotFoundException();
	}

	@Override
	public void saveCustomer(Customer customer) {
		customerRepository.save(customer);
	}

	@Override
	public boolean associateCustomerWithCard(String customerId, String cardId) 
			throws NotFoundException {
		Customer customer = findCustomer(customerId);
		
		boolean success = zoopHandler.associateCardWithCustomer(customer.getCustomerId(), cardId);
		
		if (success) {
			CustomerCard card = new CustomerCard(cardId);
			customer.setCustomerCard(card);
			
			customerRepository.save(customer);
		}
		
		return success;
	}
}
