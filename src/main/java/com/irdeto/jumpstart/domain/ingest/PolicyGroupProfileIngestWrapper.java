package com.irdeto.jumpstart.domain.ingest;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.domain.mediamanager.QueryFilterParameter;
import com.irdeto.jumpstart.domain.ImageContent;
import com.irdeto.jumpstart.domain.PolicyGroupProfile;
import com.irdeto.jumpstart.domain.PolicyProfile;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

/**
 * File Name: PolicyGroupProfileIngestWrapper.java
 *
 * Description: The wrapper class for PolicyGroupProfile
 *
 * Developed by Tata Elxsi for Irdeto B.V.
 *
 * Creation Date: 05-Feb-2015
 *
 */
public class PolicyGroupProfileIngestWrapper extends
		AbstractEntityIngestWrapper<PolicyGroupProfile> {

	public void addPoliciesNameList(List<String> policiesNameList) {
		super.addRelationshipsList(
				PolicyGroupProfile.class,
				WorkflowHelper.POLICY_PROFILE_RELATIONSHIP_NAME,
				PolicyProfile.class,
				WorkflowHelper.ATTRIBUTE_POLICY_NAME,
				policiesNameList
		);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@JsonIgnore
	@Override
	public QueryFilterParameter getMatchingQueryFilter() {
		return WorkflowHelper.getQueryFilterParameter(
				WorkflowHelper.ATTRIBUTE_POLICY_GROUP_NAME, getEntity()
						.getPolicyGroupName(), false);
	}

	@Override
	@JsonIgnore
	protected List<String> getCompareLinks() {
		return Arrays.asList(WorkflowHelper.POLICY_PROFILE_RELATIONSHIP_NAME);
	}

	@Override
	@JsonIgnore
	protected List<ImageContent> getImageContentList(PolicyGroupProfile entity) {
		return null;
	}

	@Override
	@JsonIgnore
	protected void setImageContentList(
			PolicyGroupProfile entity,
			List<ImageContent> imageContentList
	) {

	}

	@Override
	@JsonIgnore
	public String getUriId() {
		return getEntity().getPolicyGroupName();
	}
}
