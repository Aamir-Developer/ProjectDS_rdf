package com.example.rdf;

import java.io.ByteArrayOutputStream;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdfconnection.RDFConnectionFuseki;
import org.apache.jena.rdfconnection.RDFConnectionRemoteBuilder;

public class FindDistinctRecord {
	
	private RDFConnectionRemoteBuilder rdfcon = RDFConnectionFuseki.create().destination("http://127.0.0.1:3030/opal100");

	
	public String fetchData() {
	
		try (RDFConnectionFuseki RCFcon =  (RDFConnectionFuseki) rdfcon.build()) {
			QueryExecution qExec = RCFcon.query("select distinct ?Concept where {[] a ?Concept} LIMIT 30");
			
			ResultSet rs = qExec.execSelect();
			// Converting results into JSON
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ResultSetFormatter.outputAsJSON(outputStream, rs);
			
			String resultReceived =  new String(outputStream.toByteArray());
//			System.out.printf("check result"+ resultReceived);
			return resultReceived;			 
		}
	}
	
	public static void main(String[] args) {
		
		OpalController oc = new OpalController();
		String result = oc.fetchData();
		System.out.printf("check result"+ result);

	}

}
























