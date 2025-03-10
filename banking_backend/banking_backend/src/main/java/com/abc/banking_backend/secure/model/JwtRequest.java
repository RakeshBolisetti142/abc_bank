package com.abc.banking_backend.secure.model;

import java.io.Serializable;

public class JwtRequest implements Serializable {

	@Override
	public String toString() {
		return "JwtRequest [email=" + email +"]";
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = 5926468583005150707L;
	
	private String email;
	private String password;
	
	//default constructor for JSON Parsing
	public JwtRequest()
	{
		
	}

	public JwtRequest(String email, String password) {
		this.setEmail(email);
		this.setPassword(password);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

}