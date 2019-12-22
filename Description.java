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
 * The Description metric awards stars based on the the different factors in the
 * dataset
 * 
 * @author Aamir Mohammed
 */
public class Description implements Metric {

	public static int compareDescWithTitle(String desc, String title, int score) {
		if (desc.length() >= title.length()) {

			if (desc.equals(title)) {
				return score = score + 2;
			}

			return score += 3;

		} else {
			return score++;
		}
	}

	private static final Logger logger = LogManager.getLogger();
	private static final String descriptions = "Extract description information from dataset"
			+ "If dataset has description and if length is not empty and length of description > length of title then award 5 star "
			+ "Else if dataset has description but length of desc = length of title give 5 star."
			+ "Else if dataset has description but length of desc < length of title give 2 star."
			+ "Else if dataset does not has description or its property is null then give 1 star.";

	@Override
	public Integer compute(Model model, String datasetUri) throws Exception {
		logger.info("Processing dataset " + datasetUri);
		Resource dataset = ResourceFactory.createResource(datasetUri);
		 
		if(model.isEmpty()) {
			return null;
		}
		int scores = 1;

		StmtIterator datasetIterator = model.listStatements(new SimpleSelector(dataset, RDF.type, DCAT.Dataset));
		if (datasetIterator.hasNext()) {
			Statement dataSetSentence = datasetIterator.nextStatement();
			Resource dataSet = dataSetSentence.getSubject();

			if (dataSet.hasProperty(DCTerms.description)
					&& !(dataSet.getProperty(DCTerms.description).getObject().toString().isEmpty())) {
				scores += 1;
				String dctdescription = dataSet.getProperty(DCTerms.description).getObject().toString();
				String dcttitle = dataSet.getProperty(DCTerms.title).getObject().toString();
				scores = compareDescWithTitle(dctdescription, dcttitle, scores);

			} else {
				return scores++;
			}

		}
		return scores;

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
