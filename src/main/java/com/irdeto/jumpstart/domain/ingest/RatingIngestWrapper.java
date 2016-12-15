package com.irdeto.jumpstart.domain.ingest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.domain.mediamanager.QueryFilterParameter;
import com.irdeto.domain.mediamanager.command.MaintainRelationshipsCommand;
import com.irdeto.domain.mediamanager.filter.EqFilter;
import com.irdeto.domain.mediamanager.filter.Filter;
import com.irdeto.domain.mmentityapi.HttpQueryFilterParameter;
import com.irdeto.jumpstart.domain.Brand;
import com.irdeto.jumpstart.domain.Event;
import com.irdeto.jumpstart.domain.ImageContent;
import com.irdeto.jumpstart.domain.Program;
import com.irdeto.jumpstart.domain.Rating;
import com.irdeto.jumpstart.domain.RatingScheme;
import com.irdeto.jumpstart.domain.ScheduleSlot;
import com.irdeto.jumpstart.domain.Series;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

/**
 * File Name: RatingIngestWrapper.java
 * <p/>
 * Description: The wrapper class used for adding relationships on rating entity on ingest flow
 * <p/>
 * Developed by Tata Elxsi for Irdeto B.V.
 * <p/>
 * Creation Date: 14-Oct-2014
 */
public class RatingIngestWrapper extends AbstractEntityIngestWrapper<Rating> {
	/**
	 * This method will add the relationship between Rating and Rating Scheme entities
	 *
	 * @param ratingSchemeClassificationList
	 * @return none
	 */
	public void addRatingSchemeClassificationList(List<String> ratingSchemeClassificationList) {
		super.addRelationshipsList(
				Rating.class,
				WorkflowHelper.RATING_SCHEME_RELATIONSHIP_NAME,
				RatingScheme.class,
				WorkflowHelper.ATTRIBUTE_NAME_CLASSIFICATION,
				ratingSchemeClassificationList
		);
	}

	public void addProgramUriIdList(List<String> uriIdList) {
		addRelationshipsList(
				WorkflowHelper.PROGRAM_RELATIONSHIP_NAME,
				Program.class,
				uriIdList
		);
	}

	public void addSeriesUriIdList(List<String> uriIdList) {
		addRelationshipsList(
				WorkflowHelper.SERIES_RELATIONSHIP_NAME,
				Series.class,
				uriIdList
		);
	}

	public void addBrandUriIdList(List<String> uriIdList) {
		addRelationshipsList(
				WorkflowHelper.BRAND_RELATIONSHIP_NAME,
				Brand.class,
				uriIdList
		);
	}

	public void addScheduleSlotUriIdList(List<String> uriIdList) {
		addRelationshipsList(
				WorkflowHelper.SCHEDULE_SLOT_RELATIONSHIP_NAME,
				ScheduleSlot.class,
				uriIdList
		);
	}

	public void addEventUriIdList(List<String> uriIdList) {
		addRelationshipsList(
				WorkflowHelper.EVENT_RELATIONSHIP_NAME,
				Event.class,
				uriIdList
		);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@JsonIgnore
	@Override
	public QueryFilterParameter getMatchingQueryFilter() {
		HttpQueryFilterParameter queryFilterParameter = new HttpQueryFilterParameter();
		Filter filter = new EqFilter(WorkflowHelper.ATTRIBUTE_MIN_AGE, (getEntity().getMinimumAge()).toString());
		queryFilterParameter.addFilter(filter);
		filter = new EqFilter(WorkflowHelper.ATTRIBUTE_NAME_RATING_LABEL, (getEntity().getRatingLabel()));
		queryFilterParameter.addFilter(filter);
		return queryFilterParameter;
	}

	@Override
	@JsonIgnore
	protected List<ImageContent> getImageContentList(Rating entity) {
		return null;
	}

	@Override
	@JsonIgnore
	protected void setImageContentList(
			Rating entity,
			List<ImageContent> imageContentList
	) {
	}

	@Override
	@JsonIgnore
	public String getUriId() {
		return getEntity().getRatingLabel() + ":" + getEntity().getMinimumAge();
	}

	@Override
	@JsonIgnore
	protected List<String> getCompareLinks() {
		return new ArrayList<>();
	}

	public void addRatingSchemeClassification(String classificationSystem) {
		boolean found = false;
		for (MaintainRelationshipsCommand maintainRelationships : getMaintainRelationshipsList()) {
			if (WorkflowHelper.RATING_SCHEME_RELATIONSHIP_NAME.equals(maintainRelationships.getRelationshipKey())) {
				if (!maintainRelationships.getRelatedEntityLogicalKeyList().contains(classificationSystem)) {
					maintainRelationships.getRelatedEntityLogicalKeyList().add(classificationSystem);
				}
				found = true;
				break;
			}
		}
		if (!found) {
			List<String> ratingSchemeClassificationList = new ArrayList<>();
			ratingSchemeClassificationList.add(classificationSystem);
			super.addRelationshipsList(
					Rating.class,
					WorkflowHelper.RATING_SCHEME_RELATIONSHIP_NAME,
					RatingScheme.class,
					WorkflowHelper.ATTRIBUTE_NAME_CLASSIFICATION,
					ratingSchemeClassificationList
			);
		}
	}

	protected void addRelationshipsList(String relName, Class rel, List<String> relList) {
		super.addRelationshipsList(
				Rating.class,
				relName,
				rel,
				WorkflowHelper.ATTRIBUTE_NAME_URI_ID,
				relList
		);
	}
}

// End RatingIngestWrapper.java
