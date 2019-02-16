package com.facefy.facefy_api.services.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facefy.facefy_api.exceptions.BadRequestException;
import com.facefy.facefy_api.exceptions.NotFoundException;
import com.facefy.facefy_api.models.Customer;
import com.facefy.facefy_api.models.zoop.ZoopCustomer;
import com.facefy.facefy_api.repositories.CustomerRepository;
import com.facefy.facefy_api.services.CustomerService;
import com.facefy.facefy_api.zoop.ZoopHandler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

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
	public Customer findCustomer(String customerId) {
		return customerRepository.findOne(customerId);
	}

	@Override
	public void saveCustomer(Customer customer) {
		customerRepository.save(customer);
	}

	@Override
	public boolean associateCustomerWithCard(String customerId, String cardId) {
		return true;
	}
//		Gson gson = new Gson();
//		JsonObject jsonObj = new JsonObject();
//		jsonObj.addProperty("token", cardId);
//		jsonObj.addProperty("customer", customerId);
//		String POST_PARAMS = gson.toJson(jsonObj);
//		System.out.println(POST_PARAMS);
//
//		StringBuffer response = new StringBuffer();
//		HttpURLConnection postConnection;
//		try {
//			URL obj = new URL("https://api.zoop.ws/v1/marketplaces/" + MARKETPLACE_ID + "/bank_accounts");
//			postConnection = (HttpURLConnection) obj.openConnection();
//			postConnection.setRequestMethod("POST");
//			postConnection.setRequestProperty("Authorization", API_PUBLISHED_KEY);
//			postConnection.setRequestProperty("Content-Type", "application/json");
//			postConnection.setDoOutput(true);
//			OutputStream os = postConnection.getOutputStream();
//			os.write(POST_PARAMS.getBytes());
//			os.flush();
//			os.close();
//
//			int responseCode = postConnection.getResponseCode();
//			if (responseCode == HttpURLConnection.HTTP_CREATED) { // success
//				BufferedReader in = new BufferedReader(new InputStreamReader(postConnection.getInputStream()));
//				String inputLine;
//				while ((inputLine = in.readLine()) != null) {
//					response.append(inputLine);
//				}
//				in.close();
//				System.out.println(response.toString());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//		return true;
//	}
}
