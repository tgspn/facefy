package com.facefy.facefy_api.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
@SequenceGenerator(name = "U_SEQ", sequenceName = "USER_SEQ", initialValue = 1, allocationSize = 1)
public class Customer {

	@Id
	String customerId;
	
	String firstName;
	
	String lastName;
	
	String base64Photo;

	@OneToOne
	CustomerCard customerCard;
	
}
