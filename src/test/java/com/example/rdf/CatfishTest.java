package com.example.rdf;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.junit.Assert;
import org.junit.Test;
import org.dice_research.opal.catfish.Catfish;

public class CatfishTest {

	/**
	 * Tests, if model is empty.
	 */
	@Test
	public void checkEmpty() throws Exception {
		Model model = ModelFactory.createDefaultModel();
		String datasetUri = "https://example.org/dataset1";

		new Catfish().process(model, datasetUri);

		Assert.assertEquals("Model is empty", 0, model.size());

		Assert.assertTrue("Model is empty", model.isEmpty());
	}

//2018-07-09T09:40:19.518623
	@Test
	public void dateTimeZone1() throws Exception {
		Model model = ModelFactory.createDefaultModel();
		String datasetUri = "2018-07-09T09:40:19.518623";

		new Catfish().process(model, datasetUri);

		Assert.assertEquals("dateTimeZone1 format", 0, model.size());

	}

	// 2018-07-09 09:40:19.518623
	@Test
	public void dateTimeZone2() throws Exception {
		Model model = ModelFactory.createDefaultModel();
		String datasetUri = "2018-07-09 09:40:19.518623";

		new Catfish().process(model, datasetUri);

		Assert.assertEquals("dateTimeZone2 format", 0, model.size());

	}

	// Date format 31.12.1990
	@Test
	public void dd_mm_yyyy() throws Exception {
		Model model = ModelFactory.createDefaultModel();
		String datasetUri = "31.12.1990";

		new Catfish().process(model, datasetUri);

		Assert.assertEquals("dd_mm_yyyy format", 0, model.size());

	}

	// 01.01.1949 - 31.12.2017
	@Test
	public void date1_date1() throws Exception {
		Model model = ModelFactory.createDefaultModel();
		String datasetUri = "01.01.1949 - 31.12.2017";

		new Catfish().process(model, datasetUri);

		Assert.assertEquals("date1_date1 format", 0, model.size());

	}

	// 01.01.1949 — 31.12.2017
	@Test
	public void date1_date2() throws Exception {
		Model model = ModelFactory.createDefaultModel();
		String datasetUri = "01.01.1949 — 31.12.2017";

		new Catfish().process(model, datasetUri);

		Assert.assertEquals("date1_date2 format", 0, model.size());

	}

	// Dec 31 23:00:00 GMT 2005 — Sun Dec 31 22:59:59 GMT 2006
	@Test
	public void day_month_date_time_day_month_date_time() throws Exception {
		Model model = ModelFactory.createDefaultModel();
		String datasetUri = "Sat Dec 31 23:00:00 GMT 2005 — Sun Dec 31 22:59:59 GMT 2006";

		new Catfish().process(model, datasetUri);

		Assert.assertEquals("day_month_date_time_day_month_date_time format", 0, model.size());

	}

	// Tue Mar 19 00:00:00 GMT 2019
	@Test
	public void day_month_date_time() throws Exception {
		Model model = ModelFactory.createDefaultModel();
		String datasetUri = "Tue Mar 19 00:00:00 GMT 2019";

		new Catfish().process(model, datasetUri);

		Assert.assertEquals("day_month_date_time format", 0, model.size());

	}
}
