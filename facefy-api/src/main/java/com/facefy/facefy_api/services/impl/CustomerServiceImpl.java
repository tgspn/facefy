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
import com.facefy.facefy_api.repositories.CustomerRepository;
import com.facefy.facefy_api.services.CustomerService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Service
public class CustomerServiceImpl implements CustomerService {

	CustomerRepository customerRepository;
	MessageDigest md;
	
	String MARKETPLACE_ID = "3249465a7753536b62545a6a684b0000";
	String API_PUBLISHED_KEY = "basic zpk_test_ogmi3TJnV33UDljdN4n8aRit";
	
	@Autowired
	public CustomerServiceImpl(CustomerRepository customerRepository) 
			throws NoSuchAlgorithmException {
		this.customerRepository = customerRepository;
		md = MessageDigest.getInstance("MD5");
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

	@SuppressWarnings("unchecked")
	@Override
	public String createCustomer(Customer customer) {
		Gson gson = new Gson();
		JsonObject jsonObj = new JsonObject();
		jsonObj.addProperty("first_name", customer.getFirstName());
		jsonObj.addProperty("last_name", customer.getLastName());
		String POST_PARAMS = gson.toJson(jsonObj);
		System.out.println(POST_PARAMS);

		StringBuffer response = new StringBuffer();
		HttpURLConnection postConnection;
		try {
			URL obj = new URL("https://api.zoop.ws/v1/marketplaces/"+MARKETPLACE_ID +"/buyers");
			postConnection = (HttpURLConnection) obj.openConnection();
			postConnection.setRequestMethod("POST");
			postConnection.setRequestProperty("Authorization", API_PUBLISHED_KEY);
			postConnection.setRequestProperty("Content-Type", "application/json");
			postConnection.setDoOutput(true);
			OutputStream os = postConnection.getOutputStream();
			os.write(POST_PARAMS.getBytes());
			os.flush();
			os.close();

			int responseCode = postConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_CREATED) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(postConnection.getInputStream()));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				System.out.println(response.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "CUSTOMER NOT CREATED";
		}
		
		return response.toString();
//
//		Map<String, String> jsonJavaRootObject = new Gson().fromJson(response.toString(), Map.class);
//		
//		for (Map.Entry<String, String> pair : jsonJavaRootObject.entrySet()) {
//
//			String key = pair.getKey();
//			String value = pair.getValue();
//			System.out.println("Key: " + key + " | Value= " + value);
//		}
//
//		customer.setCustomerId("123123123");
//		customerRepository.save(customer);
//		return customer.getCustomerId();
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
//		RestTemplate restTemplate = new RestTemplate();
//		Map<String, String> vars = Collections.singletonMap(customerId, cardId);
//		String result = restTemplate.getForObject("http://example.com/hotels/{hotel}/rooms/{hotel}", String.class,
//				vars);
		
		Gson gson = new Gson();
		JsonObject jsonObj = new JsonObject();
		jsonObj.addProperty("token", cardId);
		jsonObj.addProperty("customer", customerId);
		String POST_PARAMS = gson.toJson(jsonObj);
		System.out.println(POST_PARAMS);

		StringBuffer response = new StringBuffer();
		HttpURLConnection postConnection;
		try {
			URL obj = new URL("https://api.zoop.ws/v1/marketplaces/" + MARKETPLACE_ID + "/bank_accounts");
			postConnection = (HttpURLConnection) obj.openConnection();
			postConnection.setRequestMethod("POST");
			postConnection.setRequestProperty("Authorization", API_PUBLISHED_KEY);
			postConnection.setRequestProperty("Content-Type", "application/json");
			postConnection.setDoOutput(true);
			OutputStream os = postConnection.getOutputStream();
			os.write(POST_PARAMS.getBytes());
			os.flush();
			os.close();

			int responseCode = postConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_CREATED) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(postConnection.getInputStream()));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				System.out.println(response.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
