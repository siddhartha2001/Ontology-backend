package com.example.portal1.JenaWork;

import net.minidev.json.JSONObject;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.Ontology;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.shared.JenaException;
import org.apache.jena.util.FileManager;
import org.apache.jena.update.*;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.apache.jena.riot.Lang;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class InitJena {

    private static QueryExecution qe;
    private static String ontoFile1 = "file:///home/siddhartha/Documents/workspace-spring-tool-suite-4-4.14.0.RELEASE/portal1/portal1.owl";
    private static String ds = "file:///home/siddhartha/Documents/workspace-spring-tool-suite-4-4.14.0.RELEASE/portal1/portal1_data.owl";
    private static String sc = "file:///home/siddhartha/Documents/workspace-spring-tool-suite-4-4.14.0.RELEASE/portal1/portal1_schema.owl";
    private static String sc1 = "file:///home/siddhartha/Documents/workspace-spring-tool-suite-4-4.14.0.RELEASE/portal1/map12.owl";
    private static String ds1 = "file:///home/siddhartha/Documents/workspace-spring-tool-suite-4-4.14.0.RELEASE/portal1/portal2_data.owl";    
    private static String ontoFile2 = "file:///home/siddhartha/Documents/workspace-spring-tool-suite-4-4.14.0.RELEASE/portal1/final_portal2.owl";
    private static String ontoFile3 = "file:///home/siddhartha/map123.owl";
    //    http://www.learningsparql.com/2ndeditionexamples/ex013.rq
    
    //use this if we are adding new triplets
    public static void execUpdate(String queryString) {
    	Dataset dataset = DatasetFactory.createTxnMem();
    	String load = "LOAD <"+ ds + ">";	
    	UpdateAction.parseExecute(load, dataset);
    	UpdateAction.parseExecute(queryString, dataset);
    	try{
    		File file = new File("/home/siddhartha/Documents/workspace-spring-tool-suite-4-4.14.0.RELEASE/portal1/portal1_data.owl");
    		FileOutputStream out = new FileOutputStream(file);
    		RDFDataMgr.write(out, dataset.getDefaultModel(), RDFFormat.RDFXML_PLAIN);
    		System.out.println("fghjklp");
    		out.close();} catch(IOException ie) {
            ie.printStackTrace();
        }   
    }    


    //Query on portal1 and portal2 
    public static ResultSet[] execQuery(String queryString) {

        OntModel ontoModel1 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        OntModel ontoModel2 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        OntModel map12 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        try {
        	
        	//loading portal1 schema and data
        	Model data = RDFDataMgr.loadModel(ds);
        	Model schema = RDFDataMgr.loadModel(sc);
        	
        	Reasoner res = ReasonerRegistry.getOWLReasoner();
        	res = res.bindSchema(schema);
//        	InfModel inf = ModelFactory.createInfModel(res, data);   	
        	InfModel inf1 = ModelFactory.createInfModel(res, data);
        	
        	//loading portal2 data and mapping schema
        	data = RDFDataMgr.loadModel(ds1);
        	schema = RDFDataMgr.loadModel(sc1);
        	res = ReasonerRegistry.getOWLReasoner();
        	res = res.bindSchema(schema);
        	InfModel inf2 = ModelFactory.createInfModel(res, data);
        	
        	
        	
        	
        	
            @SuppressWarnings("deprecation")
			InputStream in1 = FileManager.get().open(ontoFile1);
            @SuppressWarnings("deprecation")
            InputStream in2 = FileManager.get().open(ontoFile2);
            @SuppressWarnings("deprecation")
            InputStream in3 = FileManager.get().open(ontoFile3);
          
                       
//            InputStream map1 = FileManager.get().open(ontoFile3);
//            SequenceInputStream ss = new SequenceInputStream(in1, in2);
            ResultSet[] rs = new ResultSet[2]; // combining both statements in one
            try {
                ontoModel1.read(in1, null);
                
                System.out.println(queryString);
                Query query = QueryFactory.create(queryString);
                System.out.println(query);

                //Execute the query and obtain results
     
                qe = QueryExecutionFactory.create(query, inf1);
                ResultSet results = qe.execSelect();
                rs[0] = results;                
                
                //PORTAL 2 RESULTS
//                ontoModel2.read(in2, null);
//                map12.read(in3, null);       
//                Model temp = ModelFactory.createUnion(map12, ontoModel2);
                qe = QueryExecutionFactory.create(query, inf2);
                results = qe.execSelect();

                rs[1] = results;
                
                return rs;

            } catch (Exception e) {
                e.printStackTrace();
            }            
        } catch (JenaException je) {
            System.err.println("ERROR" + je.getMessage());
            je.printStackTrace();
            System.exit(0);
        }
        return null;
    }
     
    // Function to handle 2 SPARQL variables 
    public static List<JSONObject> describeClass(String queryString, int prtl, String key1, String key2){
    	System.out.println(queryString);
    	ResultSet[] resultSet = execQuery(queryString);
        List<JSONObject> list = new ArrayList<>();
        for(int i=0; i<prtl; i++) {
	        while (resultSet[i].hasNext()) {
	            JSONObject obj = new JSONObject();
	            QuerySolution solution = resultSet[i].nextSolution();
	            System.out.println(solution);
	
	            String y = solution.get("y").toString();
	            if(y.contains("^")) {
	            	y = y.substring(0, y.indexOf('^'));
	            }
	            else if(y.contains("#")) {
	            	y = y.split("#")[1];
	            }
	            
	            String z = solution.get("z").toString();
	            if (z.contains("^")) {
	            	z = z.substring(0, z.indexOf('^'));
	            }
	            else if(z.contains("#")) {
	            	z = z.split("#")[1];
	            }
	             
	            System.out.println(y);
	            if(i == 0) {
	            	obj.put("portal", "portal1");
	            }
	            else {
	            	obj.put("portal", "portal2");
	            }
	            obj.put(key1, y);
	            obj.put(key2, z);
	            list.add(obj);
	        }
        }
        return list;
    }
    
    
    //Function to handle 3 SPARQL variables 
    // Function to get all rdf triplet corresponding to a class instance.
    public static List<JSONObject> getClasses(String queryString){
    	System.out.println(queryString);
    	ResultSet[] resultSet = execQuery(queryString);
        List<JSONObject> list = new ArrayList<>();
        String inst = "";
        JSONObject obj = new JSONObject();
        int flg = 0;
        
        for(int i=0; i<2; i++) {
	        while (resultSet[i].hasNext()) {
	            QuerySolution solution = resultSet[i].nextSolution();
	            System.out.println(solution);
	            System.out.println(inst);
	
	            String x = solution.get("x").toString();
	            x = x.split("#")[1];
	            
	            if(!x.equals(inst)) {
	            	if(flg != 0) {
	            		System.out.println("yetgdhisfkjskvd");
	            		list.add(obj);
	            	}
	            	obj = new JSONObject();
	            	inst = x;
	                if(i == 0) {
	                	System.out.println("tryuieqowpiripeowi");
	                	obj.put("portal", "portal1");
	                }
	                else {
	                	obj.put("portal", "portal2");
	                }
	            }
	
	        	String y = solution.get("y").toString();
	        	if(y.contains("^")) {
	        		y = y.substring(0, y.indexOf('^'));
	        	}
	        	else if(y.contains("#")) {
	        		y = y.split("#")[1];
	        	}
	        
	        	String z = solution.get("z").toString();
	        	if (z.contains("^")) {
	        		z = z.substring(0, z.indexOf('^'));
	        	}
	        	else if(z.contains("#")) {
	        		z = z.split("#")[1];
	        	}         
	        	
	        	obj.put(y, z);
	        	flg = 1;
	        }
        }
        
        if(flg != 0) {
        	list.add(obj);
        }
        return list;
    }
    

    //Function to handle 1 SPARQL variables 
    public static List<JSONObject> getItems(String queryString, int prtl){
    	System.out.println(queryString);
    	ResultSet[] resultSet = execQuery(queryString);
        List<JSONObject> list = new ArrayList<>();
        int x=0;
        for(int i=0; i<prtl; i++) {
        while (resultSet[i].hasNext()) {
        	x++;
            JSONObject obj = new JSONObject();
            QuerySolution solution = resultSet[i].nextSolution();          
            
            String z = solution.get("z").toString();
            if (z.contains("^")) {
            	z = z.substring(0, z.indexOf('^'));
            }
            else if(z.contains("#")) {
            	z = z.split("#")[1];
            }
            
            if(i == 0) {
            	System.out.println("tryuieqowpiripeowi");
            	obj.put("portal", "portal1");
            }
            else {
            	obj.put("portal", "portal2");
            }
                        
            obj.put(Integer.toString(x), z);
            list.add(obj);
        }
        }
        return list;
    }  
    
    
    public static int getCount(String queryString, int prtl){
    	System.out.println(queryString);
    	ResultSet[] resultSet = execQuery(queryString);
        int x=0;
        for (int i=0; i<prtl; i++) {
	        while (resultSet[i].hasNext()) {
	        	x++;
	        	QuerySolution solution = resultSet[i].nextSolution(); 
	        }
        }
        return x;
    } 
    
    
}