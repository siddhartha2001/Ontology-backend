package com.example.portal1.JenaWork;

import net.minidev.json.JSONObject;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.shared.JenaException;
import org.apache.jena.util.FileManager;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class InitJena {

    private static QueryExecution qe;
    private static String ontoFile = "portal1.owl";

    public static ResultSet execQuery(String queryString) {

        OntModel ontoModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        try {
            @SuppressWarnings("deprecation")
			InputStream in = FileManager.get().open(ontoFile);
            try {
                ontoModel.read(in, null);

                System.out.println(queryString);
                Query query = QueryFactory.create(queryString);
                System.out.println(query);

                //Execute the query and obtain results
                qe = QueryExecutionFactory.create(query, ontoModel);
                ResultSet results = qe.execSelect();

                // Output query results
                //ResultSetFormatter.out(System.out, results, query);

                return results;

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
                        
            obj.put(y,z);
            list.add(obj);
        }
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
    
    
    
    
}