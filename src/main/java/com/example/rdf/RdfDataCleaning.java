package com.example.rdf;

import java.io.File;
import java.io.FileNotFoundException;
//import java.io.OutputStream;
import java.io.PrintStream;
import java.io.FileOutputStream;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.rdf.model.*;
//import org.apache.jena.query.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;



public class RdfDataCleaning 
{

	static String validate_date(String invalid_date, String invalid_date_format) throws ParseException{
		
		String valid_date= "" ;
		String valid_date_format = "yyyy-MM-dd";
		
			SimpleDateFormat date_changer = new SimpleDateFormat(invalid_date_format);
			Date date = date_changer.parse(invalid_date);
			date_changer.applyPattern(valid_date_format);
			valid_date = date_changer.format(date);

		return valid_date;
	}
	
	
	public static void main(String args[]) throws FileNotFoundException, ParseException{
		
		Model my_model = ModelFactory.createDefaultModel();
		PrintStream het_date_file = new PrintStream(new FileOutputStream("D:\\testdata\\Model1.ttl", true));
		System.setOut(het_date_file);
		
		ArrayList<Statement> statements = new ArrayList<Statement>();
		ArrayList<String> new_dates = new ArrayList<String>();
		
		/*  Put the RDF documents inside this folder   */
		File my_dir = new File("D:/testdata/");
		for (int i = 0; i < my_dir.listFiles().length; i++) 
		{
			my_model.read("D:/testdata/" + my_dir.listFiles()[i].getName());
			
			statements.clear();
			new_dates.clear();
			
			StmtIterator iterator = my_model.listStatements(new SimpleSelector(null, DCTerms.issued,(RDFNode) null));
			while(iterator.hasNext()) {
				
				Statement my_st = iterator.nextStatement();
				Resource subject = my_st.getSubject();
				RDFNode predicate = my_st.getPredicate();
				RDFNode object = my_st.getObject();
				
				
				//Different Date Formats
				//2018-07-09T09:40:19.518623
				String DateTimeZone1 = "\\d{4}[-]\\d{2}[-]\\d{2}[T][0-9]{2}[:]\\d{2}[:]\\d{2}[.]\\d+";
				//2018-07-09 09:40:19.518623
				String DateTimeZone2 = "\\d{4}[-]\\d{2}[-]\\d{2}\\s[0-9]{2}[:]\\d{2}[:]\\d{2}[.]\\d+";
				//Date format 31.12.1990
				String dd_mm_yyyy = "\\d{2}[.]\\d{2}[.]\\d{4}$";
				//01.01.1949 - 31.12.2017 take the latest
				String date1_date1 = "\\d{2}[.]\\d{2}[.]\\d{4}\\s[-]\\s\\d{2}[.]\\d{2}[.]\\d{4}";
				//01.01.1949 — 31.12.2017 take the latest.
				String date1_date2 = "\\d{2}[.]\\d{2}[.]\\d{4}\\s[—]\\s\\d{2}[.]\\d{2}[.]\\d{4}";
				//Sat Dec 31 23:00:00 GMT 2005 — Sun Dec 31 22:59:59 GMT 2006
				String day_month_date_time_day_month_date_time = "\\w{3}\\s\\w{3}\\s\\d{2}\\s\\d{2}[:]\\d{2}[:]\\d{2}"
						+ "\\s\\w{3}\\s\\d{4}\\s[—]\\s\\w{3}\\s\\w{3}\\s\\d{2}\\s\\d{2}[:]\\d{2}[:]\\d{2}\\s\\w{3}\\s\\d{4}";
				//Tue Mar 19 00:00:00 GMT 2019
				String day_month_date_time = "\\w{3}\\s\\w{3}\\s\\d{2}\\s\\d{2}[:]\\d{2}[:]\\d{2}\\s\\w{3}\\s\\d{4}$";
				
				
				//Date Format 2018-07-09T09:40:19.518623^^http://www.w3.org/2001/XMLSchema#dateTime
				if((object.toString().matches(DateTimeZone1)) )
				 {
  				    //System.out.println(object.toString());
					String new_date = validate_date(object.toString(), "yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
					//System.out.println(new_date);
					statements.add(my_st);
					new_dates.add(new_date);
				 }
				//Format 2018-07-09 09:40:19.518623
				if((object.toString().matches(DateTimeZone2)) )
				 {
  				    //System.out.println(object.toString());
					String new_date = validate_date(object.toString(), "yyyy-MM-dd HH:mm:ss.SSSSSS");
					//System.out.println(new_date);
					statements.add(my_st);
					new_dates.add(new_date);
				 }
				
				//Date format 31.12.1990
				else if((object.toString().matches(dd_mm_yyyy)) )
				 {
					//System.out.println(object.toString());
					String new_date = validate_date(object.toString(), "dd.MM.yyyy");
					//System.out.println(new_date);
					statements.add(my_st);
					new_dates.add(new_date);
				 }
				
//				// When date is in the format 01.01.1949 - 31.12.2017 take the latest.
				else if((object.toString().matches(date1_date1)) || (object.toString().matches(date1_date2)) )
				 {
					//System.out.println(object.toString());
					String new_date = object.toString().contains("-") ? 
					validate_date(object.toString().split("-")[1].trim(), "dd.MM.yyyy"):validate_date(object.toString().split("—")[1].trim(), "dd.MM.yyyy");
					//System.out.println(new_date);
					statements.add(my_st);
					new_dates.add(new_date);
			     }
				
				// When date is in the format of "Sat Dec 31 23:00:00 GMT 2005 — Sun Dec 31 22:59:59 GMT 2006"
				else if(object.toString().matches(day_month_date_time_day_month_date_time))
				 {
					//System.out.println(object.toString());
					String new_date = validate_date(object.toString().split("—")[1].trim(), "EEE MMM dd HH:mm:ss 'GMT' yyyy");
					//System.out.println(new_date);
					statements.add(my_st);
					new_dates.add(new_date);
					}
				
				// Date Clean for format like "Tue Mar 19 00:00:00 GMT 2019"
				else if(object.toString().matches(day_month_date_time))
				 {
					//System.out.println(object.toString());
					String new_date = validate_date(object.toString(), "EEE MMM dd HH:mm:ss 'GMT' yyyy");
					//System.out.println(new_date);
					statements.add(my_st);
					new_dates.add(new_date);
				 }			
			}
			
			//Update Models with two ArrayLists outside the StmtIterator
			if(statements.size()>0) 
			{
				for(int count=0;count<statements.size();count++) {
					statements.get(count).changeObject(new_dates.get(count));
				}
			}
			my_model.write(new FileOutputStream("D:/testdata/" + my_dir.listFiles()[i].getName()),"TURTLE");
		
		}
		   			
	}
  
}