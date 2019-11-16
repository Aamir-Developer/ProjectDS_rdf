package com.example.rdf;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.DCTerms;

public class RdfDateClean {
	static final String inputFileName  = "model1.ttl";
	//Date format 31.12.1990
	String dd_mm_yyyy = "\\d{2}[.]\\d{2}[.]\\d{4}$";
	//01.01.1949 - 31.12.2017
	String date1_date2 = "\\d{2}[.]\\d{2}[.]\\d{4}\\s[-]\\s\\d{2}[.]\\d{2}[.]\\d{4}";

	String checkDate( String oldDateString) throws ParseException {
		System.out.println("-----------------------------------------------------------------");
		
		String old_format = "dd.MM.yyyy";
		String new_format = "yyyy.MM.dd";
	
		SimpleDateFormat sdf = new SimpleDateFormat(old_format);
		Date date = sdf.parse(oldDateString);
		sdf.applyPattern(new_format);
		String newDateString = sdf.format(date);
		System.out.println("oldDateString:::"+oldDateString);
		System.out.println("newDateString:::"+newDateString);
		System.out.println("-----------------------------------------------------------------");
		return newDateString;
	}
	
	@SuppressWarnings("null")
	String writeLogic() throws ParseException {
		
		Model model = ModelFactory.createDefaultModel();
		model.read(inputFileName);
		StmtIterator iterator = model.listStatements(new SimpleSelector(null, DCTerms.issued,(RDFNode) null));
//		System.out.println("i am here 1"+iterator);
		int i=0,j=0, k=0;
		RDFNode UnchangedDateArr[] = null;
		String changedDateArr [] = null;
//		String splited[] = null;
		while(iterator.hasNext()) {
//			System.out.println("m while mei aagya");
			Statement my_st = iterator.nextStatement();
			Resource subject = my_st.getSubject();
			RDFNode predicate = my_st.getPredicate();
			RDFNode object = my_st.getObject();
			i++;
//			System.out.println("subject"+subject);
//			System.out.println("predicate"+predicate);
//			System.out.println("object    "+object.toString());
//			String changedDate = checkDate(object.toString());
//			changedDateArr[i]= changedDate;
			if((object.toString().matches(dd_mm_yyyy)) )
			 {
				 //System.out.println(object.toString());
//				String new_date = checkDate(object.toString());
//				System.out.println("new_date   "+new_date);
			
			 }
			else if((object.toString().matches(date1_date2)) )
			 {
				 //System.out.println(object.toString());
//				String new_date = checkDate(object.toString());
//				System.out.println("new_date   "+new_date);
			
			 }
			
			else {
//				String str = "Hello I'm your String";
//				String[] splited = str.split("\\s+");
//				String bis = null;;
			    String mystr = object.toString();
				
				
				if(mystr.contains("bis")) {
					String[] splited =  mystr.split("bis");
//					System.out.println("splited    "+ splited[0]);
				}
				else {
				     System.out.println("unused dates:"+ mystr);
				     System.out.println("ignored:"+ k++);
				     
				}
				
				
//				UnchangedDateArr[i] = object;
//				System.out.println(object.toString());
//				System.out.println("j::::"+ j++);
			}
				
		}
		
//		System.out.println("UnchangedDateArr::"+ Arrays.toString(UnchangedDateArr));
		System.out.println("ohh yeah!!!");
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
