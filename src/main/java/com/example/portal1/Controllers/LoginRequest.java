package com.example.portal1.Controllers;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequest {
    private String username;
	private String password;
	private String role;
	
	public LoginRequest(@JsonProperty("userName") String username,
						@JsonProperty("password") String password,
						@JsonProperty("role") String role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public String getUsername() {
		return username;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}