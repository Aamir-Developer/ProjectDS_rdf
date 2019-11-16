package com.example.rdf;


	import java.io.InputStream;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import org.apache.log4j.BasicConfigurator;

	public class Readrdffromfile {

	    static final String inputFileName  = "model1.ttl";
	    public static void main (String args[]) {
	        BasicConfigurator.configure();
	        // create an empty model
	        Model model = ModelFactory.createDefaultModel();
	        //Model model2 = ModelFactory.createDefaultModel();


	        InputStream input = FileManager.get().open(inputFileName);
	        if (input == null) {
	            throw new IllegalArgumentException("File: " + inputFileName + " not found");
	        }

	        // read the RDF/XML file
	        model.read(inputFileName);       
	        // write it to standard outString queryString = " .... " ;
//	        String queryString =
//	        		"SELECT ?s ?p ?o\r\n" + 
//	        		"WHERE {\r\n" + 
//	        		"  ?s ?p ?o\r\n" + 
//	        		"}\r\n" + 
//	        		"LIMIT 25000";
	        String queryString = "select distinct ?Concept where {[] a ?Concept} LIMIT 135";
	        Query query = QueryFactory.create(queryString) ;
	        //String responseIs = null;

	        try (QueryExecution qexec = QueryExecutionFactory.create(query, model))
	        {
	        	
	        	ResultSet results = qexec.execSelect() ;
	            System.out.println(results.toString());
	            while(results.hasNext())
	            {
	            	QuerySolution solution = results.nextSolution();
	            	//Literal name =solution.getLiteral(x)
	            	System.out.println("The result:"+solution);
	            }
	        	
	        	
	        	
	       	
	        	//this is with construct
//	            Model resultss = qexec.execConstruct() ;
//	            StmtIterator iter = resultss.listStatements();
//	            System.out.println(resultss.toString());
//	            while(iter.hasNext())
//	            {
//	                System.out.println(iter.next());
//
//	            }
//	        }
//	        System.out.println("   Response after rdf read");
//	     model.write(System.out,"TURTLE");
//	     System.out.println(".............................");
//	     model2.write(System.out,"TURTLE");
//	        System.out.println(responseIs); 

	    }
	    }
	}
