package com.example.portal1.Controllers;

import com.example.portal1.JenaWork.InitJena;
import net.minidev.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MyController {
	
	@CrossOrigin
	@RequestMapping("/")
	public List<JSONObject> getClassDetails(@RequestParam String class_name){
		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX portal: <http://www.iiitb.ac.in/IMT2018071/DM/portal1#> "
				+ "SELECT ?y ?z WHERE {"
				+ " portal:"
				+ class_name
				+ " ?y ?z . "
				+ "FILTER(isUri(?y) && STRSTARTS(STR(?y), STR(portal:)))"
				+ "}";
		
		List<JSONObject> resultSet = InitJena.describeClass(queryString);
        System.out.println(queryString);
        return resultSet;
	}
	
	@CrossOrigin
	@RequestMapping("/seeker/appliedjobs")
	public List<JSONObject> getAppliedJobDetails(@RequestParam String seeker_name){
		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX portal: <http://www.iiitb.ac.in/IMT2018071/DM/portal1#> "
				+ "SELECT ?y ?z WHERE {"
				+ " portal:"
				+ seeker_name
				+ " portal:AppliesFor ?z . "
				+ "?y portal:Offers ?z"
				+ "}";
		
		List<JSONObject> resultSet = InitJena.describeClass(queryString);
        System.out.println(queryString);
        return resultSet;	
	}
	
	@CrossOrigin
	@RequestMapping("/search/company")
	public List<JSONObject> getJobsCompany(@RequestParam String company_name){
		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX portal: <http://www.iiitb.ac.in/IMT2018071/DM/portal1#> "
				+ "SELECT ?z WHERE {"
				+ " portal:"
				+ company_name
				+ " portal:Offers ?z"
				+ "}";
		
		List<JSONObject> resultSet = InitJena.getItems(queryString);
        System.out.println(queryString);
        return resultSet;
	}
	
}