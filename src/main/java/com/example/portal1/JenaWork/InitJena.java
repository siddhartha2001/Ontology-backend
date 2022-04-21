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
    private static String ontoFile = "PoliticsRDF.owl";

    public static ResultSet execQuery(String queryString) {

        OntModel ontoModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        try {
            @SuppressWarnings("deprecation")
			InputStream in = FileManager.get().open(ontoFile);
            try {
                ontoModel.read(in, null);

                Query query = QueryFactory.create(queryString);

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
    	ResultSet resultSet = execQuery(queryString);
        List<JSONObject> list = new ArrayList<>();
        while (resultSet.hasNext()) {
            JSONObject obj = new JSONObject();
            QuerySolution solution = resultSet.nextSolution();

            obj.put(solution.get("Y").toString().split("#")[1],solution.get("Z").toString().split("#")[1]);
            list.add(obj);
        }
        return list;
    }   
    
    
    
    
}