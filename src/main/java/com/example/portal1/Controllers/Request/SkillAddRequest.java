package com.example.portal1.Controllers.Request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SkillAddRequest {
	private String id;
	private String sName;
	
	public SkillAddRequest(@JsonProperty("userId") String id,
						@JsonProperty("skill") String sName ){
		this.id = id;
		this.sName = sName;
	}

	public String getName() {
		return sName;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String role) {
		this.id = role;
	}

	public void setName(String username) {
		this.sName = username;
	}
}
