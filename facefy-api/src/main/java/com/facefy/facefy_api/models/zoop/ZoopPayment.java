package com.facefy.facefy_api.models.zoop;

public class ZoopPayment {

	String amount;
	String currency;
	String description;
	String on_behalf_of;
	String customer;
	String payment_type;
	
	public ZoopPayment(String amount, String on_behalf_of, String customer, String description) {
		this.amount = amount;
		this.currency = "BRL";
		this.description = description;
		this.on_behalf_of = on_behalf_of;
		this.customer = customer;
		this.payment_type = "wallet";
	}
	
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOn_behalf_of() {
		return on_behalf_of;
	}
	public void setOn_behalf_of(String on_behalf_of) {
		this.on_behalf_of = on_behalf_of;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getPayment_type() {
		return payment_type;
	}
	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}
}
