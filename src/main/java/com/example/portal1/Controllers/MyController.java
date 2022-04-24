package com.example.portal1.Controllers;

import com.example.portal1.JenaWork.InitJena;
import com.example.portal1.Controllers.LoginRequest;
import com.example.portal1.Controllers.Request.SeekerSignUp;
import com.example.portal1.Controllers.Request.*;

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
		
		List<JSONObject> resultSet = InitJena.describeClass(queryString, 2, "y", "z");
        System.out.println(queryString);
        return resultSet;
	}
	
	@CrossOrigin
	@RequestMapping("/seeker/appliedjobs")
	public List<JSONObject> getAppliedJobDetails(@RequestParam String seeker_id){
		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX portal: <http://www.iiitb.ac.in/IMT2018071/DM/portal1#> "
				+ "SELECT ?y ?z WHERE {"
				+ " ?x portal:User_Id "
				+ seeker_id
				+ " . ?x portal:AppliesFor ?jb . "
				+ " ?cmp portal:Offers ?jb ."
				+ " ?jb portal:job_id ?z ."
				+ " ?cmp portal:User_Id ?y"
				+ "}";
		
		List<JSONObject> resultSet = InitJena.describeClass(queryString, 1, "Company_id", "Job_id");
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
		
		List<JSONObject> resultSet = InitJena.describeClass(queryString, 2, "job_id", "job_title");
        System.out.println(queryString);
        return resultSet;
	}
	
	@CrossOrigin
	@RequestMapping("/apply/job")
	void applyJob(@RequestParam String job_id){
		
	}
	
	@CrossOrigin
	@RequestMapping("/seeker")
	public List<JSONObject> getJobsCourses(){
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
	
	@CrossOrigin
	@RequestMapping("/seeker/getjobs")
	public List<JSONObject> getAllJobs(){
		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX portal: <http://www.iiitb.ac.in/IMT2018071/DM/portal1#> "
				+ "SELECT ?x ?y ?z WHERE {"
//				+ " ?z rdf:type portal:Job_post ."
//				+ " ?z portal:job_id ?y"
				+ " ?x rdf:type portal:Job_post ."
				+ " ?x ?y ?z ."
				+ "FILTER(isUri(?y) && STRSTARTS(STR(?y), STR(portal:)))"
				+ "}";
		
		List<JSONObject> resultSet = InitJena.getClasses(queryString);
        System.out.println(queryString);
        return resultSet;
	}
	
	public List<JSONObject> getAllCourses(){
		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX portal: <http://www.iiitb.ac.in/IMT2018071/DM/portal1#> "
				+ "SELECT ?x ?y ?z WHERE {"
				+ " ?x rdf:type portal:Courses ."
				+ " ?x ?y ?z ."
				+ "FILTER(isUri(?y) && STRSTARTS(STR(?y), STR(portal:)))"
				+ "}";
		
		List<JSONObject> resultSet = InitJena.getClasses(queryString);
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
		
		List<JSONObject> resultSet = InitJena.describeClass(queryString, 1, "User_Id", "Name");
        System.out.println(queryString);
        return resultSet;
	}
	
//	@CrossOrigin
//	@RequestMapping("/seeker/test2")
//	public List<JSONObject> getLoc(){
//		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
//				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
//				+ "PREFIX portal1: <http://www.iiitb.ac.in/IMT2018071/DM/portal1#> "
//				+ "PREFIX portal2: <http://www.iiitb.ac.in/IMT2018071/DM/portal2#> "
//				+ "SELECT ?y ?z WHERE {"
//				+ " ?y portal1:Offers ?z ."
//				+ "}";
//		
//		List<JSONObject> resultSet = InitJena.describeClass(queryString);
//        System.out.println(queryString);
//        return resultSet;
//	}
	
//	@CrossOrigin
//	@RequestMapping("/test")
//	public List<JSONObject> test(){
//		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
//				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
//				+ "PREFIX xds: <http://www.w3.org/2001/XMLSchema#> " 
//				+ "PREFIX portal: <http://www.iiitb.ac.in/IMT2018071/DM/portal1#> "
//				+ "PREFIX portal: <http://www.iiitb.ac.in/IMT2018071/DM/portal1#> "
//				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> "
//				+ "SELECT ?z WHERE {"
//				+ "{ ?z rdf:type portal:Company } UNION"
//				+ "{ portal:Company owl:sameAs ?y ."
//				+ " ?z rdf:type ?y}"
//				+ "}";
//		
//		List<JSONObject> resultSet = InitJena.getItems(queryString);
//        System.out.println(queryString);
//        return resultSet;
//	}
	
	@CrossOrigin
	@RequestMapping("/test4")
	public List<JSONObject> test4() {
		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX xds: <http://www.w3.org/2001/XMLSchema#> " 
				+ "PREFIX portal: <http://www.iiitb.ac.in/IMT2018071/DM/portal1#> "
				+ "SELECT ?y ?z WHERE{"
				+ " ?y rdf:type portal:Experience_details ."
				+ " ?z portal:Offers portal:Job1"
				+ "}";
		
		List<JSONObject> resultSet = InitJena.describeClass(queryString, 1, "Users", "Job");
		return resultSet;
	}
	
	@CrossOrigin
	@RequestMapping("test10")
	public int getInstanceCount(@RequestParam String cls) {
		
		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX xds: <http://www.w3.org/2001/XMLSchema#> " 
				+ "PREFIX portal: <http://www.iiitb.ac.in/IMT2018071/DM/portal1#> "
				+ "SELECT ?z WHERE {"
				+ " ?z rdf:type portal:"
				+ cls
				+ " ."
				+ "}";
		
		int count = InitJena.getCount(queryString, 1);
		return count;
	}
	
	public boolean checkEmail(String email, String usrType) {
				
		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX xds: <http://www.w3.org/2001/XMLSchema#> " 
				+ "PREFIX portal: <http://www.iiitb.ac.in/IMT2018071/DM/portal1#> "
				+ "SELECT ?z WHERE {"
				+ " ?z rdf:type portal:"
				+ usrType
				+ " ."
				+ " ?z portal:email \""
				+ email
				+ "\" }";
		
		List<JSONObject> resultSet = InitJena.getItems(queryString, 1);
		return resultSet.isEmpty();
	}
	
	
	@CrossOrigin
	@RequestMapping("/signup/seeker")
	public JSONObject addSeeker(@RequestBody SeekerSignUp seekerSignUp) {
		int cnt = getInstanceCount("Seeker")+1;
		int id = 2000 + cnt;
		String inst = "Seeker" + Integer.toString(cnt);
		JSONObject obj = new JSONObject();
		
		if(checkEmail(seekerSignUp.getUsername(), "Seeker") == false) {
			obj.put("status", "Error: Email already exists");
			return obj;
		}
		
		System.out.println(seekerSignUp.getGender());
		
		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " 
				+ "PREFIX portal: <http://www.iiitb.ac.in/IMT2018071/DM/portal1#> "
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> "
				+ "INSERT DATA {"
				+ " portal:"
				+ inst
				+ " rdf:type owl:NamedIndividual ;"
				+ " rdf:type portal:Seeker ;"
				+ " portal:User_Id \""
				+ Integer.toString(id)
				+ "\"^^xsd:integer ; portal:gender \""
				+ seekerSignUp.getGender()
				+ "\" ; portal:email \""
				+ seekerSignUp.getUsername()
				+ "\" ; portal:password \""
				+ seekerSignUp.getPassword()
				+ "\" ; portal:first_name \""
				+ seekerSignUp.GetFirstName()
				+ "\" ; portal:last_name \""
				+ seekerSignUp.GetLastName()
				+ "\" ; portal:contact_number \""
				+ seekerSignUp.getNumber()
				+ "\"^^xsd:integer ; portal:User_type \""
				+ seekerSignUp.getRole()
				+ "\" ; portal:date_of_birth \""
				+ seekerSignUp.GetDob()
				+ "\"^^xsd:dateTime . }";
		
		System.out.println(id);
		
		InitJena.execUpdate(queryString);
		obj.put("status", "Success");
		obj.put("User_Id", Integer.toString(id));
		return obj;
	}
	
	@CrossOrigin
	@RequestMapping("/signup/company")
	public JSONObject addCompany(@RequestBody CompanySignUp companySignUp) {
		int cnt = getInstanceCount("Company")+1;
		int id = 1000 + cnt;
		String inst = "Company" + Integer.toString(cnt);
		JSONObject obj = new JSONObject();
		
		if(checkEmail(companySignUp.getUsername(), "Company") == false) {
			obj.put("status", "Error: Email already exists");
			return obj;
		}
		
		System.out.println(companySignUp.getURL());
		
		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " 
				+ "PREFIX portal: <http://www.iiitb.ac.in/IMT2018071/DM/portal1#> "
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> "
				+ "INSERT DATA {"
				+ " portal:"
				+ inst
				+ " rdf:type owl:NamedIndividual ;"
				+ " rdf:type portal:Company ;"
				+ " portal:User_Id \""
				+ Integer.toString(id)
				+ "\"^^xsd:integer ; portal:url \""
				+ companySignUp.getURL()
				+ "\" ; portal:email \""
				+ companySignUp.getUsername()
				+ "\" ; portal:password \""
				+ companySignUp.getPassword()
				+ "\" ; portal:cname \""
				+ companySignUp.getName()
				+ "\" ; portal:c_desc \""
				+ companySignUp.getDesc()
				+ "\" ; portal:contact_number \""
				+ companySignUp.getNumber()
				+ "\"^^xsd:integer ; portal:User_type \"company\""
				+ " .}";
		
		System.out.println(id);
		
		InitJena.execUpdate(queryString);
		obj.put("status", "Success");
		obj.put("User_Id", Integer.toString(id));
		return obj;
	}

	
	@CrossOrigin
	@RequestMapping("/seeker/addedu")
	public JSONObject addEdu(@RequestBody AddEducationRequest addEducationRequest){
		int cnt = getInstanceCount("Educational_details")+1;
		int id = 500 + cnt;
		String inst = "Education" + Integer.toString(cnt);
		JSONObject obj = new JSONObject();
		
		System.out.println(addEducationRequest.getCertificate());
		
		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " 
				+ "PREFIX portal: <http://www.iiitb.ac.in/IMT2018071/DM/portal1#> "
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> "
				+ "INSERT {"
				+ " portal:"
				+ inst
				+ " rdf:type owl:NamedIndividual ;"
				+ " rdf:type portal:Educational_details ;"
				+ " portal:education_id \""
				+ Integer.toString(id)
				+ "\"^^xsd:integer ; portal:cgpa \""
				+ addEducationRequest.getCgpa()
				+ "\"^^xsd:float ; portal:certificate \""
				+ addEducationRequest.getCertificate()
				+ "\" ; portal:major \""
				+ addEducationRequest.getMajor()
				+ "\" ; portal:university \""
				+ addEducationRequest.getUniversity()
				+ "\" ."
				+ " ?x portal:HasEducation portal:"
				+ inst
				+ "}"
				+ "WHERE {"
				+ " ?x portal:User_Id "
				+ addEducationRequest.getUserId()
				+ "}";
		
		System.out.println(id);
		
		InitJena.execUpdate(queryString);
		obj.put("status", "Success");
		obj.put("Edu_Id", Integer.toString(id));
		return obj;
	}
	
	@CrossOrigin
	@RequestMapping("/seeker/addexp")
	public JSONObject addexp(@RequestBody AddExperienceRequest exp){
		int cnt = getInstanceCount("Experience_details")+1;
		int id = 500 + cnt;
		String inst = "Exp" + Integer.toString(cnt);
		JSONObject obj = new JSONObject();
		
		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " 
				+ "PREFIX portal: <http://www.iiitb.ac.in/IMT2018071/DM/portal1#> "
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> "
				+ "INSERT {"
				+ " portal:"
				+ inst
				+ " rdf:type owl:NamedIndividual ;"
				+ " rdf:type portal:Experience_details ;"
				+ " portal:exp_job_title \""
				+ exp.getJobTitle()
				+ "\"; portal:job_description \""
				+ exp.getJobDescription()
				+ "\"; portal:start_date \""
				+ exp.getStartDate()
				+ "\"^^xsd:dateTime ; portal:end_time \""
				+ exp.getEndDate()
				+ "\"^^xsd:dateTime ; portal:prev_company \""
				+ exp.getPrevCompany()
				+ "\" ."
				+ " ?x portal:HasExperience portal:"
				+ inst
				+ "}"
				+ "WHERE {"
				+ " ?x portal:User_Id "
				+ exp.getid()
				+ "}";
		
		System.out.println(id);
		
		InitJena.execUpdate(queryString);
		obj.put("status", "Success");
		obj.put("Exp_Id", Integer.toString(id));
		return obj;
	}
	
	public List<JSONObject> checkSkill(String s1) {
		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX xds: <http://www.w3.org/2001/XMLSchema#> " 
				+ "PREFIX portal: <http://www.iiitb.ac.in/IMT2018071/DM/portal1#> "
				+ "SELECT ?z WHERE {"
				+ " ?z rdf:type portal:Skill ."
				+ " ?z portal:skill \""
				+ s1
				+ "\" }";
		
		List<JSONObject> resultSet = InitJena.getItems(queryString, 1);
		return resultSet;
	}
	
	@CrossOrigin
	@RequestMapping("/seeker/addskill")
	public JSONObject addSkill(@RequestBody SkillAddRequest skillAddRequest) {
		JSONObject obj = new JSONObject();
		String queryString;
		if(checkSkill(skillAddRequest.getName()).isEmpty() == true) {
			int cnt = getInstanceCount("Skills")+1;
			int sid = 100 + cnt;
			String inst = "Skill" + Integer.toString(cnt);
			System.out.println("ghjofcdhfuvihoisjpd");
			
			queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
					+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
					+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " 
					+ "PREFIX portal: <http://www.iiitb.ac.in/IMT2018071/DM/portal1#> "
					+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> "
					+ "INSERT {"
					+ " portal:"
					+ inst
					+ " rdf:type owl:NamedIndividual ;"
					+ " rdf:type portal:Skills ;"
					+ " portal:skill_id \""
					+ Integer.toString(sid)
					+ "\"^^xsd:integer ; portal:skill \""
					+ skillAddRequest.getName()
					+ "\" ."
					+ "?x portal:HasSkills portal:"	
					+ inst
					+ " }"
					+ "WHERE {"
					+" ?x portal:User_Id portal:"
					+ skillAddRequest.getId()
					+ " }";
		}
		else {
			queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
					+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
					+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " 
					+ "PREFIX portal: <http://www.iiitb.ac.in/IMT2018071/DM/portal1#> "
					+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> "
					+ "INSERT {"
					+ " ?x portal:HasSkills ?y }"
					+ "WHERE { "
					+ " ?x portal:User_Id portal:"
					+ skillAddRequest.getId()
					+ " . ?y portal:skill portal:"
					+ skillAddRequest.getName()
					+ " }";
		}
		
		InitJena.execUpdate(queryString);
		obj.put("status", "Success");
		return obj;
	}	
}