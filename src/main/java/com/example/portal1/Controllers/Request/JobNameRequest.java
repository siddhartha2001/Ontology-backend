package com.example.portal1.Controllers.Request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JobNameRequest {
	private String jobname;
	
	public JobNameRequest(@JsonProperty("jobName") String jobname ){
			this.jobname = jobname;
	}
	
	public String getJobName() {
		return jobname;
	}
	
}