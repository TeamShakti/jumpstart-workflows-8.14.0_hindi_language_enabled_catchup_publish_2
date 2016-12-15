package com.irdeto.jumpstart.workflow.ingest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

import com.irdeto.helper.xmlmodel.JDOMDocumentHelper;
import com.irdeto.jumpstart.domain.Rating;
import com.irdeto.jumpstart.domain.RatingScheme;
import com.irdeto.jumpstart.domain.ingest.EntityIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.RatingIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.RatingSchemeIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.SourceInformation;

/**
 * File Name: JDOMIrdetoRatingMapper.java
 *
 * Description: The Mapper class for ratings.xml
 *
 * Developed by Tata Elxsi for Irdeto B.V.
 *
 * Creation Date: 14-Oct-2014
 *
 */
public class JDOMIrdetoRatingMapper extends AbstractVodIngestMapper implements VodIngestMapper {

	private static final String ATTRIBUTE_CLASSIFICATION_SYSTEM = "classificationSystem";
	private static final String ATTRIBUTE_RATING = "rating";
	private static final String ATTRIBUTE_MIN_AGE = "minimumAge";
	private static final String ATTRIBUTE_ID = "id";
	private static final String ELEMENT_COUNTRY_OF_SYS = "CountryOfSystem";
	private static final String ELEMENT_OTHER_INC_COUNTRY = "OtherIncludedCountry";


	@Override
	@JsonIgnore
	public boolean isApplicableMapper() {
		return StringUtils.contains(getInputString(), "<Ratings>");
	}

	@Override
	@JsonIgnore
	public String[] getSchemaFileNames() {
		return new String[] {"ratings.xsd"};
	}

	@Override
	@JsonIgnore
	public boolean isSchemaValidationEnabled() {
		return true;
	}

	/**
	 * This method read Rating Scheme and Ratings and their relationships from the xml and set
	 * to the respective domain entities and forms the wrapper object for ingestion
	 *
	 * @param none
	 * @return none
	 */
	@Override
	public List<EntityIngestWrapper<?>> findEntities() throws Exception {
		List<EntityIngestWrapper<?>> ingestWrapperList = new ArrayList<>();
		ingestWrapperList.addAll(findRatingScheme((getInputString())));
		ingestWrapperList.addAll(findRatings(getInputString()));
		return ingestWrapperList;
	}

	protected List<RatingSchemeIngestWrapper> findRatingScheme(String xmlInput)
			throws Exception {
		List<RatingSchemeIngestWrapper> ingestWrapperList = new ArrayList<>();
		Document document = JDOMDocumentHelper.buildDocument(xmlInput);
		XPathFactory xPathFactory = XPathFactory.instance();
		XPathExpression<Element> contentGroupPath = xPathFactory.compile("/Ratings/RatingScheme", Filters.element(), null);
		List<Element> ratingSchemeElementList = contentGroupPath.evaluate(document);

		for (Element ratingSchemeElement: ratingSchemeElementList) {
			RatingSchemeIngestWrapper ingestWrapper = new RatingSchemeIngestWrapper();
			RatingScheme ratingScheme = new RatingScheme();
			ingestWrapper.setEntity(ratingScheme);
			ingestWrapper.setSourceInformation(new SourceInformation(getFileName(), getFilePath(), this.getClass()));

			String classificationSystem = ratingSchemeElement.getAttributeValue(ATTRIBUTE_CLASSIFICATION_SYSTEM);
			ratingScheme.setClassification(classificationSystem);
			List<Element> countryOfSystemList = ratingSchemeElement.getChildren(ELEMENT_COUNTRY_OF_SYS);
			List<Object> countryOfSystemListValues = getCountryObjectsListValues(countryOfSystemList);
			ratingScheme.setCountryOfSystem(countryOfSystemListValues);
			List<Element> otherIncludeCountryList = ratingSchemeElement.getChildren(ELEMENT_OTHER_INC_COUNTRY);
			List<Object> otherIncludeCountryListValues = getCountryObjectsListValues(otherIncludeCountryList);
			ratingScheme.setCountriesIncluded(otherIncludeCountryListValues);

			ingestWrapperList.add(ingestWrapper);
		}

		return ingestWrapperList;
	}

	protected List<RatingIngestWrapper> findRatings(String xmlInput)
			throws Exception {
		List<RatingIngestWrapper> ingestWrapperList = new ArrayList<>();
		Document document = JDOMDocumentHelper.buildDocument(xmlInput);
		XPathFactory xPathFactory = XPathFactory.instance();
		XPathExpression<Element> contentGroupPath = xPathFactory.compile("//RatingScheme/Rating", Filters.element(), null);
		List<Element> ratingElementList = contentGroupPath.evaluate(document);

		for (Element ratingElement: ratingElementList) {
			String minAge = ratingElement.getAttributeValue(ATTRIBUTE_MIN_AGE);
			String ratingLabel = ratingElement.getAttributeValue(ATTRIBUTE_RATING);
			// code by nitin
			String ID = ratingElement.getAttributeValue(ATTRIBUTE_ID);
			RatingIngestWrapper ingestWrapper = null;
			for (RatingIngestWrapper existingIngestWrapper: ingestWrapperList) {
				if (existingIngestWrapper.getEntity().getRatingLabel().equals(ratingLabel)
						&& String.valueOf(existingIngestWrapper.getEntity().getMinimumAge()).equals(minAge)) {
					ingestWrapper = existingIngestWrapper;
					break;
				}
			}
			if (ingestWrapper == null) {
				ingestWrapper = new RatingIngestWrapper();
				Rating rating = new Rating();
				ingestWrapper.setEntity(rating);
				ingestWrapper.setSourceInformation(new SourceInformation(getFileName(), getFilePath(), this.getClass()));
				//code by nitin
				rating.setUriId(ID);
				rating.setMinimumAge(Integer.valueOf(minAge));
				rating.setRatingLabel(ratingLabel);
				ingestWrapperList.add(ingestWrapper);
			}
			Element ratingSchemeElement = ratingElement.getParentElement();
			String classificationSystem = ratingSchemeElement.getAttributeValue(ATTRIBUTE_CLASSIFICATION_SYSTEM);
			ingestWrapper.addRatingSchemeClassification(classificationSystem);
		}
		return ingestWrapperList;
	}

	@JsonIgnore
	private List<Object> getCountryObjectsListValues(List<Element> elementList) {
		List<Object> otherIncludedCountryListValues = new ArrayList<>();
		for (Element element: elementList) {
			Object value = element.getValue();
			otherIncludedCountryListValues.add(value);
		}
		return otherIncludedCountryListValues;
	}
}

// End JDOMIrdetoRatingMapper
