package org.dice_research.opal.catfish;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/* 
 * Date cleaning is the progress of cleaning the dates which is in different formats. 
 * According to DCAT Vocabulary, W3C has defined a standard date format "YYYY-MM-DD".
 * Here, we are checking different date formats in dct:issued property and
 * dct:modified, cleaning the dates accordingly. 
 * Regular expressions are used here to check different date formats.
 * 
 * @Aamir Mohammed
 */

public class Datecleaning {
	private static final Logger LOGGER = LogManager.getLogger();

	// Different Date Formats
	// 2018-07-09T09:40:19.518623
	String DateTimeZone1 = "\\d{4}[-]\\d{2}[-]\\d{2}[T][0-9]{2}[:]\\d{2}[:]\\d{2}[.]\\d+";
	// 2018-07-09 09:40:19.518623
	String DateTimeZone2 = "\\d{4}[-]\\d{2}[-]\\d{2}\\s[0-9]{2}[:]\\d{2}[:]\\d{2}[.]\\d+";
	// Date format 31.12.1990
	String dd_mm_yyyy = "\\d{2}[.]\\d{2}[.]\\d{4}$";
	// 01.01.1949 - 31.12.2017 take the latest
	String date1_date1 = "\\d{2}[.]\\d{2}[.]\\d{4}\\s[-]\\s\\d{2}[.]\\d{2}[.]\\d{4}";
	// 01.01.1949 � 31.12.2017 take the latest
	String date1_date1_longdash = "\\d{2}[.]\\d{2}[.]\\d{4}\\s[�]\\s\\d{2}[.]\\d{2}[.]\\d{4}";
	// 01.01.1949 31.12.2017 take the latest.
	String date1_date2 = "\\d{2}[.]\\d{2}[.]\\d{4}\\s\\s\\d{2}[.]\\d{2}[.]\\d{4}";
	// Sat Dec 31 23:00:00 GMT 2005 � Sun Dec 31 22:59:59 GMT 2006
	String day_month_date_time_day_month_date_time = "\\w{3}\\s\\w{3}\\s\\d{2}\\s\\d{2}[:]\\d{2}[:]\\d{2}"
			+ "\\s\\w{3}\\s\\d{4}\\s[�]\\s\\w{3}\\s\\w{3}\\s\\d{2}\\s\\d{2}[:]\\d{2}[:]\\d{2}\\s\\w{3}\\s\\d{4}";
	// Sat Dec 31 23:00:00 GMT 2005 - Sun Dec 31 22:59:59 GMT 2006
	String day_month_date_time_day_month_date_time1 = "\\w{3}\\s\\w{3}\\s\\d{2}\\s\\d{2}[:]\\d{2}[:]\\d{2}"
			+ "\\s\\w{3}\\s\\d{4}\\s[-]\\s\\w{3}\\s\\w{3}\\s\\d{2}\\s\\d{2}[:]\\d{2}[:]\\d{2}\\s\\w{3}\\s\\d{4}";
	// Sat Dec 31 23:00:00 GMT 2005 Sun Dec 31 22:59:59 GMT 2006
	String day_month_date_time_day_month_date_time_space = "\\w{3}\\s\\w{3}\\s\\d{2}\\s\\d{2}[:]\\d{2}[:]\\d{2}"
			+ "\\s\\w{3}\\s\\d{4}\\s\\s\\w{3}\\s\\w{3}\\s\\d{2}\\s\\d{2}[:]\\d{2}[:]\\d{2}\\s\\w{3}\\s\\d{4}";
	// Tue Mar 19 00:00:00 GMT 2019
	String day_month_date_time = "\\w{3}\\s\\w{3}\\s\\d{2}\\s\\d{2}[:]\\d{2}[:]\\d{2}\\s\\w{3}\\s\\d{4}$";

	static String checkDate(String invalid_date, String invalid_date_format) throws ParseException {
		String valid_date = "";
		String valid_date_format = "yyyy-MM-dd";

		SimpleDateFormat sdf = new SimpleDateFormat(invalid_date_format);
		Date date = sdf.parse(invalid_date);
		sdf.applyPattern(valid_date_format);
		valid_date = sdf.format(date);
		return valid_date;
	}

	public Model processModel(Model model, String datasetUri) throws Exception {
		if (model.isEmpty()) {
			LOGGER.warn("Model is empty.");
		}

		ArrayList<Statement> statements = new ArrayList<Statement>();
		ArrayList<String> new_dates = new ArrayList<String>();
		Resource dataset = ResourceFactory.createResource(datasetUri);
		ArrayList<Property> properties = new ArrayList<Property>();
		properties.add(DCTerms.issued);
		properties.add(DCTerms.modified);

		for (Property property : properties) {

			StmtIterator iterator = model.listStatements(new SimpleSelector(dataset, property, (RDFNode) null));
			while (iterator.hasNext()) {

				Statement my_st = iterator.nextStatement();
				RDFNode dataSetdate = my_st.getObject();
				String date_to_check = dataSetdate.toString();

				// When date is in the format "2018-07-09T09:40:19.518623"
				if ((date_to_check.matches(DateTimeZone1))) {
					String new_date = checkDate(date_to_check, "yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
					new_dates.add(new_date);
					statements.add(my_st);
				}

				// When date is in the format "2018-07-09 09:40:19.518623"
				else if ((date_to_check.matches(DateTimeZone2))) {
					String new_date = checkDate(date_to_check, "yyyy-MM-dd HH:mm:ss.SSSSSS");
					new_dates.add(new_date);
					statements.add(my_st);
				}

				// When date is in the format "31.12.1990"
				else if ((date_to_check.matches(dd_mm_yyyy))) {
					String new_date = checkDate(date_to_check, "dd.MM.yyyy");
					new_dates.add(new_date);
					statements.add(my_st);
				}

				// When date is in the format "01.01.1949 - 31.12.2017 and 01.01.1949
				// 31.12.2017" take the latest
				else if ((date_to_check.matches(date1_date1)) || (date_to_check.matches(date1_date2))) {
					String new_date = date_to_check.contains("-")
							? checkDate(date_to_check.split("-")[1].trim(), "dd.MM.yyyy - dd.MM.yyyy")
							: checkDate(date_to_check.split("\\s\\s")[1].trim(), "dd.MM.yyyy  dd.MM.yyyy");
					new_dates.add(new_date);
					statements.add(my_st);
				}

				// When date is in the format "01.01.1949 � 31.12.2017" take the latest
				else if ((date_to_check.matches(date1_date1_longdash))) {
					String new_date = checkDate(date_to_check.split("�")[1].trim(), "dd.MM.yyyy � dd.MM.yyyy");
					new_dates.add(new_date);
					statements.add(my_st);
				}

				// When date is in the format "Sat Dec 31 23:00:00 GMT 2005 � Sun Dec 31
				// 22:59:59 GMT 2006" and "Sat Dec 31 23:00:00 GMT 2005 Sun Dec 31
				// 22:59:59 GMT 2006"
				else if ((date_to_check.matches(day_month_date_time_day_month_date_time))
						|| (date_to_check.matches(day_month_date_time_day_month_date_time_space))) {
					String new_date = date_to_check.contains("�")
							? checkDate(date_to_check.split("�")[1].trim(),
									"EEE MMM dd HH:mm:ss 'GMT' yyyy � EEE MMM dd HH:mm:ss 'GMT' yyyy")
							: checkDate(date_to_check.split("\\s\\s")[1].trim(),
									"EEE MMM dd HH:mm:ss 'GMT' yyyy  EEE MMM dd HH:mm:ss 'GMT' yyyy");
					new_dates.add(new_date);
					statements.add(my_st);
				}

				// When date is in the format "Sat Dec 31 23:00:00 GMT 2005 - Sun Dec 31
				// 22:59:59 GMT 2006"
				else if (date_to_check.matches(day_month_date_time_day_month_date_time1)) {
					String new_date = checkDate(date_to_check.split("-")[1].trim(),
							"EEE MMM dd HH:mm:ss 'GMT' yyyy - EEE MMM dd HH:mm:ss 'GMT' yyyy");
					new_dates.add(new_date);
					statements.add(my_st);
				}

				// When date is in the format "Tue Mar 19 00:00:00 GMT 2019"
				else if (date_to_check.matches(day_month_date_time)) {
					String new_date = checkDate(date_to_check, "EEE MMM dd HH:mm:ss 'GMT' yyyy");
					new_dates.add(new_date);
					statements.add(my_st);
				}
			}
		}

		// Update Models with two ArrayLists outside the StmtIterator
		if (statements.size() > 0) {
			for (int count = 0; count < statements.size(); count++) {
				statements.get(count).changeObject(new_dates.get(count));
			}
		}

		return model;
	}
}
