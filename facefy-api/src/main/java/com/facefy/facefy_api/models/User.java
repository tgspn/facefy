package com.facefy.facefy_api.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "\"user\"")
@SequenceGenerator(name = "U_SEQ", sequenceName = "USER_SEQ", initialValue = 1, allocationSize = 1)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "U_SEQ")
	long userId;
}
