package com.facefy.facefy_api.models.zoop;

public class ZoopCardAssociation {

	String token;
	String customer;
	
	public ZoopCardAssociation(String token, String customer) {
		this.token = token;
		this.customer = customer;
	}
	
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
}
