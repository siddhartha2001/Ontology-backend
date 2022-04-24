package com.example.portal1.Controllers.Request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddEducationRequest {
	private String userId;
	private String certificate;
	private String major;
	private String university;
	private String cgpa;
	
	public AddEducationRequest(@JsonProperty("userId") String id,
						@JsonProperty("certificate") String certificate,
						@JsonProperty("major") String major,
						@JsonProperty("university") String university,
						@JsonProperty("cgpa") String cgpa){
		this.userId = id;
		this.certificate = certificate;
		this.cgpa=cgpa;
		this.major=major;
		this.university=university;
	}

	public String getCertificate() {
		return certificate;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public String getCgpa() {
		return cgpa;
	}
	
	public String getMajor() {
		return major;
	}
	
	public String getUniversity() {
		return university;
	}
}
