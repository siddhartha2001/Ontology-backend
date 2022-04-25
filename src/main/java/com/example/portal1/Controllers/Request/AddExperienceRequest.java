package com.example.portal1.Controllers.Request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddExperienceRequest {
	private String id;
    private String start_date;
	private String end_date;
	private String job_description;
	private String job_title;
	private String prev_company;
	
	public AddExperienceRequest(@JsonProperty("userId") String id,
			@JsonProperty("jobTitle") String job_title,
			@JsonProperty("jobDesc") String job_description,
			@JsonProperty("startDate") String start_date,
			@JsonProperty("endDate") String end_date,
			@JsonProperty("prevCompany") String prev_company) {
		
		this.id = id;
		this.job_title = job_title;
		this.job_description = job_description;
		this.start_date = start_date;
		this.end_date = end_date;
		this.prev_company = prev_company;	
		
	}
	public String getid() {
		return id;
	}
	
	public String getJobTitle() {
		return job_title;
	}
	
	public String getJobDescription() {
		return job_description;
	}
	
	public String getStartDate() {
		return start_date;
	}
	
	public String getEndDate() {
		return end_date;
	}
	
	public String getPrevCompany() {
		return prev_company;
	}
}