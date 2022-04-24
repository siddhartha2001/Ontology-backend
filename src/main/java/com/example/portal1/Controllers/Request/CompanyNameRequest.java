package com.example.portal1.Controllers.Request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CompanyNameRequest {
	private String companyname;
	
	public CompanyNameRequest(@JsonProperty("companyName") String companyname ){
			this.companyname = companyname;
	}
	
	public String getCompanyName() {
		return companyname;
	}
	
}
