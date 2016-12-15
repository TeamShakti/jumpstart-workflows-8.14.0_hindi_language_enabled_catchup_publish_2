package com.irdeto.jumpstart.domain.ingest;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.domain.mediamanager.QueryFilterParameter;
import com.irdeto.jumpstart.domain.ImageContent;
import com.irdeto.jumpstart.domain.RatingScheme;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

/**
 * File Name: RatingSchemeIngestWrapper.java
 *
 * Description: The wrapper class for Rating Scheme
 *
 * Developed by Tata Elxsi for Irdeto B.V.
 *
 * Creation Date: 14-Oct-2014
 *
 */
public class RatingSchemeIngestWrapper extends AbstractEntityIngestWrapper<RatingScheme> {

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@JsonIgnore
	@Override
	public QueryFilterParameter getMatchingQueryFilter() {
		return WorkflowHelper.getQueryFilterParameter(WorkflowHelper.ATTRIBUTE_NAME_CLASSIFICATION, getEntity().getClassification(), false);
	}

	@Override
	@JsonIgnore
	protected List<ImageContent> getImageContentList(RatingScheme entity) {
		return null;
	}

	@Override
	@JsonIgnore
	protected void setImageContentList(
			RatingScheme entity,
									   List<ImageContent> imageContentList
	) {
	}

	@Override
	@JsonIgnore
	public String getUriId() {
		return getEntity().getClassification();
	}

	@Override
	@JsonIgnore
	protected List<String> getCompareLinks() {
		return Arrays.asList(WorkflowHelper.RATING_RELATIONSHIP_NAME);
	}
}

//End RatingSchemeIngestWrapper.java
