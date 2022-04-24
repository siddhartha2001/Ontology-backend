package com.example.portal1.Controllers.Request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SeekerSignUp {
    private String username;
	private String password;
	private String role;
	private String gender;
	private String first_name;
	private String last_name;
	private String dob;
	private String number;
	
	public SeekerSignUp(@JsonProperty("email") String username,
						@JsonProperty("password") String password,
						@JsonProperty("userRole") String role,
						@JsonProperty("gender") String gender,
						@JsonProperty("fname") String first_name,
						@JsonProperty("lname") String last_name,
						@JsonProperty("phone") String number,
						@JsonProperty("dob") String dob) {
		this.username = username;
		this.password = password;
		this.role = role;
		this.gender = gender;
		this.first_name = first_name;
		this.last_name = last_name;
		this.dob = dob;
		this.number = number;		
		
	}
	
	public String getGender() {
		return gender;
	}
	
	public String getNumber() {
		return number;
	}
	
	public String GetFirstName() {
		return first_name;
	}
	
	public String GetLastName() {
		return last_name;
	}
	
	public String GetDob() {
		return dob;
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