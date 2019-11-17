package com.example.rdf;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class demo {

	public static void main(String[] args) throws ParseException {
		
    int av = (int) 'a';
		String mystr = "1949-01-01 bis gestern";
		
		Boolean bisCheck = mystr.contains("bis");
		System.out.println(mystr.length());
		String[] splited =  mystr.split("bis");
		System.out.println("splited    "+ splited[0]);
		
}
}
