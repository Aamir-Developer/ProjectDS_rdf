package com.example.rdf;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RdfDataClean {

	public static void main(String[] args) throws ParseException {
		

		 String OLD_FORMAT =  "MM-dd-yyyy";
		final String NEW_FORMAT = "yyyy-MM-dd";

		// August 12, 2010
		String oldDateString = "08-15-2010";
		@SuppressWarnings("unused")
		String newDateString;

		SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
		Date d = sdf.parse(oldDateString);
		sdf.applyPattern(NEW_FORMAT);
		newDateString = sdf.format(d);
		System.out.println(newDateString);
	}

}
