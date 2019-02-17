package com.facefy.facefy_api.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "transaction")
@SequenceGenerator(name = "T_SEQ", sequenceName = "TRANSAC_SEQ", initialValue = 1, allocationSize = 1)
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_SEQ")
	@Column(name = "id", updatable = false, nullable = false)
	long id;

	String customerId;

	String amount;

	String date;

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
