package com.example.portal1.Controllers.Request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplyJobRequest {
	private String userId;
	private String jobId;
	
	public ApplyJobRequest(@JsonProperty("userId") String userId,
						@JsonProperty("jobId") String jobId ){
		this.userId = userId;
		this.jobId = jobId;
	}

	public String getUserId() {
		return userId;
	}
	
	public String getJobId() {
		return jobId;
	}
}
