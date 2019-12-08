//package org.dice_research.opal.common.interfaces;
package org.dice_research.opal.catfish;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.vocabulary.DCAT;
import org.dice_research.opal.common.utilities.ModelSerialization;

/**
 * Processes data in Jena model.
 * 
 * For serialization of byte arrays, {@link ModelSerialization} can be used
 * before and after calling the process method.
 *
 * @author Adrian Wilke
 */
public interface JenaModelProcessor {

	/**
	 * Reads data in given Jena {@link Model}, processes data related to DCAT
	 * {@link DCAT#Dataset} URIs, and returns new Jena {@link Model}.
	 * 
	 * @param model      Jena input model
	 * @param datasetUri URI of DCAT dataset to process
	 * @return Jena output model with processed data
	 * @throws Exception On errors
	 */
	public Model process(Model model, String datasetUri) throws Exception;

}