package com.javabrains.springsecurityjwt.models;

import org.springframework.stereotype.Component;

@Component
public class AuthenticationResponse {
	
	private String jwt;
	
	public AuthenticationResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public AuthenticationResponse(String jwt) {
		this.jwt = jwt;
	}

	public String getJwt() {
		return jwt;
	}
	
	

}
