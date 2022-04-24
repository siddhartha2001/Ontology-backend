package com.example.portal1.Controllers.Request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CompanySignUp {
    private String username;
	private String password;
	private String role;
	private String cname;
	private String desc;
	private String number;
	private String url;
	
	public CompanySignUp(@JsonProperty("email") String username,
						@JsonProperty("password") String password,
						@JsonProperty("role") String role,
						@JsonProperty("cname") String cname,
						@JsonProperty("desc") String desc,
						@JsonProperty("phone") String number,
						@JsonProperty("url") String url) {
		this.username = username;
		this.password = password;
		this.role = role;
		this.url = url;
		this.cname = cname;
		this.desc = desc;
		this.number = number;		
		
	}
	
	public String getName() {
		return cname;
	}
	
	public String getNumber() {
		return number;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public String getURL() {
		return url;
	}

	public String getUsername() {
		return username;
	}
	
	public String getRole() {
		return role;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setRole(String role) {
		this.role = role;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}