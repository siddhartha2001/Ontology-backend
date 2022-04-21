package com.example.portal1.Controllers;

import com.example.portal1.JenaWork.InitJena;
import com.example.portal1.Controllers.LoginRequest;
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
				+ "SELECT ?y ?z WHERE {"
				+ "?x portal:cname \""
				+ company_name
				+ "\" . ?x portal:Offers ?v1 ."
				+ "?v1 portal:job_title ?z ."
				+ "?v1 portal:job_id ?y"
				+ "}";
		
		List<JSONObject> resultSet = InitJena.describeClass(queryString);
        System.out.println(queryString);
        return resultSet;
	}
	
	@CrossOrigin
	@RequestMapping("/apply/job")
	void applyJob(@RequestParam String job_id){
		
	}
	
	@CrossOrigin
	@RequestMapping("/seeker")
	public List<JSONObject> getJobsCourses(@RequestParam String seeker_name){
		List<JSONObject> jobs = getAllJobs();
		List<JSONObject> courses = getAllCourses();
		List<JSONObject> jc = new ArrayList<>();
		
		JSONObject obj = new JSONObject();
		obj.put("Jobs", jobs);
		jc.add(obj);
		
		obj = new JSONObject();
		obj.put("Courses", courses);
		jc.add(obj);
		
		return jc; 
	}
	
	public List<JSONObject> getAllJobs(){
		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX portal: <http://www.iiitb.ac.in/IMT2018071/DM/portal1#> "
				+ "SELECT ?y ?z WHERE {"
				+ " ?z rdf:type portal:Job_post ."
				+ " ?z portal:job_id ?y"
				+ "}";
		
		List<JSONObject> resultSet = InitJena.describeClass(queryString);
        System.out.println(queryString);
        return resultSet;
	}
	
	public List<JSONObject> getAllCourses(){
		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX portal: <http://www.iiitb.ac.in/IMT2018071/DM/portal1#> "
				+ "SELECT ?y ?z WHERE {"
				+ " ?z rdf:type portal:Courses ."
				+ " "
				+ "?z portal:course_id ?y"
				+ "}";
		
		List<JSONObject> resultSet = InitJena.describeClass(queryString);
        System.out.println(queryString);
        return resultSet;
	}
	
	@CrossOrigin
	@RequestMapping("/login")
	public List<JSONObject> verifyLogin(@RequestBody LoginRequest loginRequest) {
		String name = null;
		switch (loginRequest.getRole()) {
			case "Company":
				name = "cname";
				break;
			case "Seeker":
				name = "first_name";
				break;
			case "Trainer":
				name = "tname";
				break;
		}
		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX portal: <http://www.iiitb.ac.in/IMT2018071/DM/portal1#> "
				+ "SELECT ?y ?z WHERE {"
				+ " ?x rdf:type portal:"
				+ loginRequest.getRole()
				+ " . ?x portal:email \""
				+ loginRequest.getUsername()
				+ "\" . ?x portal:password \""
				+ loginRequest.getPassword()
				+ "\" . ?x portal:User_Id ?y ."
				+ "  ?x portal:"
				+ name
				+ " ?z"
				+ "}";
		
		List<JSONObject> resultSet = InitJena.describeClass(queryString);
        System.out.println(queryString);
        return resultSet;
	}
	
	
	
	
	
	
	
	
}