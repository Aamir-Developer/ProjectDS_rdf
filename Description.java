package org.dice_research.opal.civet.metrics;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
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
 * The Expressiveness awards stars based on the the different factors in the
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

	private static final Logger LOGGER = LogManager.getLogger();
	private static final String DESCRIPTION = "Extract description information from dataset"
			+ "If dataset has description and if length is not empty and length of description > length of title then award 5 star "
			+ "Else if dataset has description but length of desc = length of title give 5 star."
			+ "Else if dataset has description but length of desc < length of title give 2 star."
			+ "Else if dataset does not has description or its property is null then give 1 star.";

	@Override
	public Integer compute(Model model, String datasetUri) throws Exception {
		LOGGER.info("Processing dataset " + datasetUri);
		int scores = 1;

		StmtIterator DatasetIterator = model.listStatements(new SimpleSelector(null, RDF.type, DCAT.Dataset));
		if (DatasetIterator.hasNext()) {
			Statement DataSetSentence = DatasetIterator.nextStatement();
			Resource DataSet = DataSetSentence.getSubject();

			if (DataSet.hasProperty(DCTerms.description)
					&& !(DataSet.getProperty(DCTerms.description).getObject().toString().isEmpty())) {
				scores += 1;
				String dctdescription = DataSet.getProperty(DCTerms.description).getObject().toString();
				String dcttitle = DataSet.getProperty(DCTerms.title).getObject().toString();
				scores = compareDescWithTitle(dctdescription, dcttitle, scores);

			} else {
				return scores++;
			}

		}
		return scores;

	}

	@Override
	public String getDescription() {
		return DESCRIPTION;
	}

	@Override
	public String getUri() throws Exception {
		return Opal.OPAL_METRIC_CATEGORIZATION.getURI();
	}

}