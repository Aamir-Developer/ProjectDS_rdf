
package org.dice_research.opal.civet.metrics;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.DCAT;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.RDF;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dice_research.opal.civet.Metric;
import org.dice_research.opal.common.vocabulary.Opal;

/**
 * The Dateformat metric awards stars based on the the dateformat in the dataset
 * 
 * @author Aamir Mohammed
 */
public class Dateformat implements Metric {
	public static int checkDateFormat(String issued) {
		if (issued.matches("\\d{4}-\\d{2}-\\d{2}")) {
			return 5;
		} else {
			return 1;
		}

	}

	private static final Logger logger = LogManager.getLogger();
	private static final String descriptions = "Check the metadata field of dateformat"
			+ "If dateformat in the dataset is according to W3C standards then give 5 starts" + "Else return null";

	@Override
	public Integer compute(Model model, String datasetUri) throws Exception {
		logger.info("Processing dataset " + datasetUri);
		Resource dataset = ResourceFactory.createResource(datasetUri);

		StmtIterator datasetIterator = model.listStatements(new SimpleSelector(dataset, RDF.type, DCAT.Dataset));
		if (datasetIterator.hasNext()) {
			Statement dataSetSentence = datasetIterator.nextStatement();
			Resource dataSet = dataSetSentence.getSubject();

			if (dataSet.hasProperty(DCTerms.issued)
					&& !(dataSet.getProperty(DCTerms.issued).getObject().toString().isEmpty())) {
				String dateissued = dataSet.getProperty(DCTerms.issued).getObject().toString();
				int result = checkDateFormat(dateissued);
				return result;
			} else {
				return null;
			}

		}
		return null;
	}

	@Override
	public String getDescription() {
		return descriptions;
	}

	@Override
	public String getUri() throws Exception {
		return Opal.OPAL_METRIC_CATEGORIZATION.getURI();
	}

}