package com.facefy.facefy_api.zoop;

import java.io.IOException;

import org.apache.tomcat.util.codec.binary.Base64;

import com.facefy.facefy_api.models.Customer;
import com.facefy.facefy_api.models.zoop.ZoopCardAssociation;
import com.facefy.facefy_api.models.zoop.ZoopCustomer;
import com.facefy.facefy_api.models.zoop.ZoopPayment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ZoopHandler {

	Gson gson;

	OkHttpClient client;
	final String URL = "https://api.zoop.ws/v1/marketplaces/";

	final String MARKETPLACE_ID = "3249465a7753536b62545a6a684b0000";
	final String SELLER_ID = "00c92e1ebc98487fb3a4511982c54ea7";
	
	String API_PUBLISHED_KEY = "Basic enBrX3Rlc3Rfb2dtaTNUSm5WMzNVRGxqZE40bjhhUml0Og==";

	public ZoopHandler() {
		this.gson = new GsonBuilder().serializeNulls().create();
		this.client = new OkHttpClient();
	}
	
	public String createCustomer(Customer customer) {
		String url = URL + MARKETPLACE_ID + "/buyers";
	
		ZoopCustomer zoopCustomer = new ZoopCustomer(customer);
		
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(null, gson.toJson(zoopCustomer));
		Request request = new Request.Builder()
		  .url(url)
		  .post(body)
		  .addHeader("Content-Type", "application/json")
		  .addHeader("Authorization", API_PUBLISHED_KEY)
		  .build();

		try {
			Response response = client.newCall(request).execute();
			
			if (response.isSuccessful()) {
				zoopCustomer = gson.fromJson(response.body().string(), ZoopCustomer.class);
				return zoopCustomer.getId();
			}
			
			return "";
		} catch (IOException e) {
			return "";
		}
	}
	
	public boolean associateCardWithCustomer(String customerId, String cardId) {
		String url = URL + MARKETPLACE_ID + "/bank_accounts";
		
		ZoopCardAssociation zca = new ZoopCardAssociation(cardId, customerId);
		
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, gson.toJson(zca));
		Request request = new Request.Builder()
		  .url("https://api.zoop.ws/v1/marketplaces/3249465a7753536b62545a6a684b0000/bank_accounts")
		  .post(body)
		  .addHeader("Content-Type", "application/json")
		  .addHeader("Authorization", API_PUBLISHED_KEY)
		  .build();

		try {
			Response response = client.newCall(request).execute();
			return response.isSuccessful();
		} catch (IOException e) {
			return false;
		}
	}

	public boolean buy(Customer customer, String amount, String description) {
		String buyerId = customer.getCustomerId();
		
		ZoopPayment zoopPayment = new ZoopPayment(amount, SELLER_ID, buyerId, description);
		
		String url = URL + MARKETPLACE_ID + "/transactions";

		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, gson.toJson(zoopPayment));
		Request request = new Request.Builder()
		  .url(url)
		  .post(body)
		  .addHeader("Content-Type", "application/json")
		  .addHeader("Authorization", API_PUBLISHED_KEY)
		  .build();

		try {
			Response response = client.newCall(request).execute();
			return response.isSuccessful();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
