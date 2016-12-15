package com.irdeto.jumpstart.workflow.config;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.kie.api.runtime.process.ProcessContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irdeto.domain.operation.MM8Response;
import com.irdeto.jumpstart.domain.Rating;
import com.irdeto.jumpstart.domain.RatingScheme;
import com.irdeto.jumpstart.domain.config.RatingSchemeXML;
import com.irdeto.jumpstart.domain.config.RatingXML;
import com.irdeto.jumpstart.domain.config.Ratings;
import com.irdeto.jumpstart.domain.ingest.MaintainEntities;
import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.manager.jbpm.knowledge.ClassManager;
import com.irdeto.manager.jbpm.knowledge.ClassManagerImpl;
import com.irdeto.manager.task.BeanUtil;

public class RatingHelper extends WorkflowHelper{

	private static final String ATTRIBUTE_NAME_CLASSIFICATION = "classification";
	private static final String ATTRIBUTE_NAME_RATING_LABEL = "ratingLabel";
	private static final String RATINGS_KEY = "ratings";
	private static final String RATING_SCHEME_XML_LIST_KEY = "ratingSchemeXMLList";
	private static final String RATING_XML_LIST_KEY = "ratingXMLList";
	private static final String RATING_SCHEME_XML_KEY = "ratingSchemeXML";
	private static final String RATING_SCHEME_KEY = "ratingScheme";
	private static final String RATING_XML_KEY = "ratingXML";
	private static final String RATING_ENTITY_ID_KEY = "ratingEntityId";
	private static final String RATING_LIST_FROM_RELATIONSHIP_KEY = "ratingListFromRelationship";
	private static final String DELETE_LIST_KEY = "deleteList";
	private static final String RATING_STRING_KEY = "ratingString";
	private static final String RESPONSE_LIST_KEY = "responseList";
	private static final String MAINTAIN_ENTITIES_KEY = "maintainEntities";
	private static final String MAINTAINED_RATING_SCHEME_KEY = "maintainedRatingScheme";
	private static final String MAINTAINED_RATING_LIST_KEY = "maintainedRatingList";
	private static final String MAINTAIN_RELATIONSHIPS_KEY = "maintainRelationships";

	private static Logger logger = LoggerFactory.getLogger(RatingHelper.class);

	@SuppressWarnings("resource")
	public static void readDefaultRatings(ProcessContext kcontext){
		logger.debug("Fail to read ratings.xml in config.data.dir. Instead, read from default ratings from jar");
		ClassManager classManager = BeanUtil.getBean(ClassManagerImpl.class);
		InputStream inputStream = classManager.getClassLoader().getResourceAsStream("data/ratings.xml");
		String ratingString = new Scanner(inputStream, "UTF-8").useDelimiter("\\A").next();
		kcontext.setVariable(RATING_STRING_KEY, ratingString);
	}
	public static void unloadRatingData(ProcessContext kcontext){

		MM8Response responseList = (MM8Response)kcontext.getVariable(RESPONSE_LIST_KEY);
		RatingScheme ratingScheme = (RatingScheme)kcontext.getVariable(RATING_SCHEME_KEY);
		Ratings ratings = (Ratings)kcontext.getVariable(RATINGS_KEY);

		List <Rating> ratingList = getEntityList(Rating.class, responseList);

		RatingSchemeXML ratingSchemeXml = new RatingSchemeXML();
		ratingSchemeXml.setClassificationSystem(ratingScheme.getClassification());

		List<Object> countryOfSystemList = ratingScheme.getCountryOfSystem();
		List<Object> otherIncludedCountryList = ratingScheme.getCountriesIncluded();

		for(Object countrySystem:countryOfSystemList){
			ratingSchemeXml.getCountryOfSystemList().add( (String)countrySystem);
		}

		for(Object otherIncludedCountry:otherIncludedCountryList){
			ratingSchemeXml.getOtherIncludedCountryList().add( (String)otherIncludedCountry);
		}

		for(Rating rating:ratingList){
			RatingXML ratingXml = new RatingXML();
			ratingXml.setRating(rating.getRatingLabel());
			ratingXml.setMinimumAge(rating.getMinimumAge());
			ratingSchemeXml.getRatingList().add(ratingXml);
		}
		ratings.getRatingSchemeList().add(ratingSchemeXml);
	}

	public static void getRatingSchemeXMLList(ProcessContext kcontext){
		Ratings ratingObject = (Ratings) kcontext.getVariable(RATINGS_KEY);
		List<RatingSchemeXML> ratingSchemeXMLList = ratingObject.getRatingSchemeList();
		kcontext.setVariable(RATING_SCHEME_XML_LIST_KEY, ratingSchemeXMLList);
	}

	@SuppressWarnings("unchecked")
	public static void mapRatingScheme(ProcessContext kcontext) {
		List<RatingSchemeXML> ratingSchemeXMLList = (List<RatingSchemeXML>) kcontext.getVariable(RATING_SCHEME_XML_LIST_KEY);
		RatingSchemeXML ratingSchemeXML = ratingSchemeXMLList.remove(0);

		List<RatingScheme> ratingSchemeList = new ArrayList<>();
		RatingScheme ratingScheme = new RatingScheme();

		ratingScheme.setClassification(ratingSchemeXML.getClassificationSystem());
		ratingScheme.setCountryOfSystem((List<Object>)(List<?>)ratingSchemeXML.getCountryOfSystemList());
		ratingScheme.setCountriesIncluded((List<Object>)(List<?>)ratingSchemeXML.getOtherIncludedCountryList());
		ratingSchemeList.add(ratingScheme);
		MaintainEntities maintainEntities = new MaintainEntities(RatingScheme.class, ATTRIBUTE_NAME_CLASSIFICATION, ratingSchemeList);
		kcontext.setVariable(MAINTAIN_ENTITIES_KEY, maintainEntities);
		kcontext.setVariable(RATING_SCHEME_XML_KEY, ratingSchemeXML);
	}

	public static void mapRatings(ProcessContext kcontext) {
		RatingSchemeXML ratingSchemeXML = (RatingSchemeXML)kcontext.getVariable(RATING_SCHEME_XML_KEY);
		List<RatingXML> ratingXMLList = ratingSchemeXML.getRatingList();
		List<Rating> ratingList = new ArrayList<>();
		for (RatingXML ratingXML: ratingXMLList) {
			Rating rating = new Rating();

			rating.setRatingLabel(ratingXML.getRating());
			rating.setMinimumAge(ratingXML.getMinimumAge());
			ratingList.add(rating);
		}
		MaintainEntities maintainEntities = new MaintainEntities(Rating.class, ATTRIBUTE_NAME_RATING_LABEL, ratingList);
		kcontext.setVariable(MAINTAIN_ENTITIES_KEY, maintainEntities);
	}


	@SuppressWarnings("unchecked")
	public static void readRatingRelationship (ProcessContext kcontext){
		MM8Response mm8Response = (MM8Response)kcontext.getVariable(MM8_RESPONSE_KEY);
		List<RatingXML> ratingXMLList = (List<RatingXML>) kcontext.getVariable(RATING_XML_LIST_KEY);
		List<Rating> ratingListFromRelationship = (List<Rating>) WorkflowHelper.getEntityList(Rating.class, mm8Response);
		List <Rating> deleteList = new ArrayList <Rating> ();

		if(!ratingListFromRelationship.isEmpty()){
			logger.debug("Found rating relationship");
			kcontext.setVariable(RATING_LIST_FROM_RELATIONSHIP_KEY, ratingListFromRelationship);
			for (Rating ratingMM: ratingListFromRelationship){
				boolean found = false;
				for (RatingXML ratingXML: ratingXMLList){
					if (ratingMM.getRatingLabel().equals(ratingXML.getRating())){
						logger.debug("found matching MM and XML rating");
						found = true;
					}
				}
				if(found==false){
					logger.debug("found rating need to be deleted");
					deleteList.add(ratingMM);
				}
			}
		}
		else{
			logger.debug("Not found rating relationship");
		}
		kcontext.setVariable(DELETE_LIST_KEY, deleteList);
	}

	@SuppressWarnings("unchecked")
	public static boolean gatewayToFoundExistingRating(ProcessContext kcontext){
		List<RatingXML> ratingXMLList = (List<RatingXML>) kcontext.getVariable(RATING_XML_LIST_KEY);
		List<Rating> ratingListFromRelationship = (List<Rating>) kcontext.getVariable(RATING_LIST_FROM_RELATIONSHIP_KEY);
		boolean found = false;
		RatingXML ratingXML = ratingXMLList.remove(0);

		if(ratingListFromRelationship!=null && !ratingListFromRelationship.isEmpty()){
			for(Rating rating: ratingListFromRelationship){
				if(ratingXML.getRating().equals(rating.getRatingLabel())){
					found = true;
					kcontext.setVariable(RATING_ENTITY_ID_KEY, rating.getId());
				}
			}
		}
		kcontext.setVariable(RATING_XML_KEY, ratingXML);
		return found;
	}

	@SuppressWarnings("unchecked")
	public static void deleteRatingNotInXML(ProcessContext kcontext){
		List<Rating> deleteList = (List<Rating>) kcontext.getVariable(DELETE_LIST_KEY);
		Rating deleteRating = deleteList.remove(0);
		kcontext.setVariable(RATING_ENTITY_ID_KEY, deleteRating.getId());
		logger.debug("delete rating in MM: " + deleteRating.getId());
	}
}

