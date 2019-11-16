package com.example.rdf;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.util.FileManager;
import java.io.ByteArrayOutputStream;

public class ReadspqlTTLfile {

	public void scanData() {
		Model model = FileManager.get().loadModel("D:\\opal-graph\\model1.ttl\\");
//		System.out.println("model" + model);
		String queryString = "select distinct ?Concept where {[] a ?Concept} LIMIT 135";

		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		try {
			ResultSet results = qexec.execSelect();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ResultSetFormatter.outputAsJSON(outputStream, results);
			String json = new String(outputStream.toByteArray());
			System.out.println("json" + json);
//			ResultSet results = qexec.execSelect();

		} finally {
			qexec.close();
		}

	}

	public static void main(String[] args) {

		ReadspqlTTLfile obj = new ReadspqlTTLfile();
		obj.scanData();

	}

//
//	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();ResultSetFormatter.outputAsJSON(outputStream,results);
//
//	String json = new String(outputStream.toByteArray());
//	return;
////        return (json);

}
