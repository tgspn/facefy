package com.facefy.facefy_api.sympla;

import com.facefy.facefy_api.models.Event;

public class SymplaHandler {

	String token;
	
	String URL = "https://api.sympla.com.br/public/v3/";
	
	public SymplaHandler(String token) {
		this.token = token;
	}
	
}
