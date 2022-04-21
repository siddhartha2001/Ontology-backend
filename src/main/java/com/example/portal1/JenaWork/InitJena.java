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
                System.out.println(qe);
                ResultSet results = qe.execSelect();
                System.out.println(results);

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

            obj.put(solution.get("y").toString().split("#")[1],solution.get("z").toString().split("#")[1]);
            list.add(obj);
        }
        return list;
    }   
    
    
    
    
}