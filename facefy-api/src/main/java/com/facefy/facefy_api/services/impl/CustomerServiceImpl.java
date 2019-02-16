package com.facefy.facefy_api.services.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facefy.facefy_api.exceptions.BadRequestException;
import com.facefy.facefy_api.exceptions.NotFoundException;
import com.facefy.facefy_api.models.Customer;
import com.facefy.facefy_api.repositories.CustomerRepository;
import com.facefy.facefy_api.services.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	
	CustomerRepository customerRepository;
	MessageDigest md;
	
	@Autowired
	public CustomerServiceImpl(CustomerRepository customerRepository) 
			throws NoSuchAlgorithmException {
		this.customerRepository = customerRepository;
		md = MessageDigest.getInstance("MD5");
	}
	
	@Override
	public Customer getMe(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(Customer customer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCard(String userId, String cardId) {
		// TODO Auto-generated method stub
		
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

}
