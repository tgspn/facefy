package com.facefy.facefy_api.models.zoop;

import com.facefy.facefy_api.models.Customer;

public class ZoopCustomer {

	String id;
	String first_name;
	String last_name;

	public ZoopCustomer(Customer customer) {
		this.first_name = customer.getFirstName();
		this.last_name = customer.getLastName();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	
	

}
