package com.facefy.facefy_api.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FacefyConfiguration {

	@Value("${facefy.sympla.token}")
	private String symplaToken;

	public String getSymplaToken() {
		return symplaToken;
	}

	public void setSymplaToken(String symplaToken) {
		this.symplaToken = symplaToken;
	}
}
