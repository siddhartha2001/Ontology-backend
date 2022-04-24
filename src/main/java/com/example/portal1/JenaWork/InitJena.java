package com.example.portal1.JenaWork;

import net.minidev.json.JSONObject;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.Ontology;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
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
    private static String ontoFile2 = "file:///home/siddhartha/Documents/workspace-spring-tool-suite-4-4.14.0.RELEASE/portal1/final_portal2.owl";
    private static String ontoFile3 = "file:///home/siddhartha/map123.owl";
    //    http://www.learningsparql.com/2ndeditionexamples/ex013.rq
    
    public static void execUpdate(String queryString) {
    	Dataset dataset = DatasetFactory.createTxnMem();
    	String load = "LOAD <"+ ontoFile1 + ">";	
    	UpdateAction.parseExecute(load, dataset);
    	UpdateAction.parseExecute(queryString, dataset);
    	try{
    		File file = new File("/home/siddhartha/temp.owl");
    		FileOutputStream out = new FileOutputStream(file);
    		RDFDataMgr.write(out, dataset.getDefaultModel(), RDFFormat.RDFXML_PLAIN);
    		System.out.println("fghjklp");
    		out.close();} catch(IOException ie) {
            ie.printStackTrace();
        }   
    }    


    public static ResultSet execQuery(String queryString) {

        OntModel ontoModel1 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        OntModel ontoModel2 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        OntModel map12 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        try {
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
                ontoModel2.read(in2, null);
                map12.read(in3, null);
             
                
                Model temp = ModelFactory.createUnion(ontoModel1, ontoModel2);
                temp = ModelFactory.createUnion(map12, temp);
                
                System.out.println(queryString);
                Query query = QueryFactory.create(queryString);
                System.out.println(query);

                //Execute the query and obtain results
                qe = QueryExecutionFactory.create(query, temp);
                ResultSet results = qe.execSelect();

                // Output query results
                //ResultSetFormatter.out(System.out, results, query);

                rs[0] = results;
                
//                ontoModel.read(in2, null);
//
//                System.out.println(queryString);
//                query = QueryFactory.create(queryString);
//                System.out.println(query);
//
//                //Execute the query and obtain results
//                qe = QueryExecutionFactory.create(query, ontoModel);
//                results = qe.execSelect();
//
//                rs[1] = results;
                
                return rs[0];

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
      
    public static List<JSONObject> describeClass(String queryString){
    	System.out.println(queryString);
    	ResultSet resultSet = execQuery(queryString);
        List<JSONObject> list = new ArrayList<>();
//        for(int i=0; i<2; i++) {
        while (resultSet.hasNext()) {
            JSONObject obj = new JSONObject();
            QuerySolution solution = resultSet.nextSolution();
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
//            if(i == 0) {
//            	obj.put("pid", "portal1");
//            }
//            else {
//            	obj.put("pid", "portal2");
//            }
            obj.put(y,z);
            list.add(obj);
        }
//        }
        return list;
    }   
    
    public static List<JSONObject> getItems(String queryString){
    	System.out.println(queryString);
    	ResultSet resultSet = execQuery(queryString);
        List<JSONObject> list = new ArrayList<>();
        int x=0;
        while (resultSet.hasNext()) {
        	x++;
            JSONObject obj = new JSONObject();
            QuerySolution solution = resultSet.nextSolution();          
            
            String z = solution.get("z").toString();
            if (z.contains("^")) {
            	z = z.substring(0, z.indexOf('^'));
            }
            else if(z.contains("#")) {
            	z = z.split("#")[1];
            }
                        
            obj.put(Integer.toString(x),z);
            list.add(obj);
        }
        return list;
    }  
    
    public static int getCount(String queryString){
    	System.out.println(queryString);
    	ResultSet resultSet = execQuery(queryString);
        int x=0;
        while (resultSet.hasNext()) {
        	x++;
        	QuerySolution solution = resultSet.nextSolution(); 
        }
        return x;
    } 
    
    
}