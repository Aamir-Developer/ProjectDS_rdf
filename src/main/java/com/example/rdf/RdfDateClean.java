package com.example.rdf;

import java.io.File;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.DCTerms;

public class RdfDateClean {
	static final String inputFileName  = "model1.ttl";
	String checkDate( String oldDateString) throws ParseException {
		System.out.println("m  checkdate m aagya");
		
		String old_format = "dd.MM.yyyy";
		String new_format = "yyyy.MM.dd";
//	if(old_format)	
		SimpleDateFormat sdf = new SimpleDateFormat(old_format);
		System.out.println("check sdf"+sdf);
		Date date = sdf.parse(oldDateString);
		System.out.println("check date"+date);
		sdf.applyPattern(new_format);
		String newDateString = sdf.format(date);
		System.out.println("newDateString:"+newDateString);
		
		return newDateString;
	}
	
	@SuppressWarnings("null")
	String writeLogic() throws ParseException {
		
		Model model = ModelFactory.createDefaultModel();
		model.read(inputFileName);
		StmtIterator iterator = model.listStatements(new SimpleSelector(null, DCTerms.issued,(RDFNode) null));
		System.out.println("i am here 1"+iterator);
		int i=0;
		String changedDateArr [] = null;
		while(iterator.hasNext()) {
//			System.out.println("m while mei aagya");
			Statement my_st = iterator.nextStatement();
			Resource subject = my_st.getSubject();
			RDFNode predicate = my_st.getPredicate();
			RDFNode object = my_st.getObject();
			i++;
//			System.out.println("subject"+subject);
//			System.out.println("predicate"+predicate);
			System.out.println("object    "+object.toString());
//			String changedDate = checkDate(object.toString());
//			changedDateArr[i]= changedDate;
			
			System.out.println("ohh yeah!!!"+ i);	
		}
		
		
		return null;	
	}
	
	
	public static void main(String[] args) throws ParseException {
//		System.out.println("I am in main");
//		String OLD_FORMAT =  "MM-dd-yyyy";
//		final String NEW_FORMAT = "yyyy-MM-dd";
		String oldDateString = "25.04.2010";
//		
		RdfDateClean rdcobj = new RdfDateClean();
		rdcobj.checkDate( oldDateString);
		rdcobj.writeLogic();
		}

}
