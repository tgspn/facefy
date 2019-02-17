package com.facefy.facefy_api.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "card")
@SequenceGenerator(name = "U_SEQ", sequenceName = "USER_SEQ", initialValue = 1, allocationSize = 1)
public class CustomerCard {

	@Id
	String cardId;
	
	public CustomerCard()
	{
	}

	public CustomerCard(String cardId) {
		this.cardId = cardId;
	}
	
	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
	
}
