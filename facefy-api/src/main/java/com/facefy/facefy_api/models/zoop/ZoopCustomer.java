package com.facefy.facefy_api.models.zoop;

import com.facefy.facefy_api.models.Customer;

public class ZoopCustomer {

	String id;
	String firstName;
	String lastName;

	public ZoopCustomer(Customer customer) {
		this.firstName = customer.getFirstName();
		this.lastName = customer.getLastName();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
