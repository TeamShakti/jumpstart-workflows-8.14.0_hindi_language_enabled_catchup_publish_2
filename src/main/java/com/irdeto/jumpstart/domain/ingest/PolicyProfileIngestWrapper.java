package com.irdeto.jumpstart.domain.ingest;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.domain.mediamanager.QueryFilterParameter;
import com.irdeto.jumpstart.domain.ImageContent;
import com.irdeto.jumpstart.domain.PolicyGroupProfile;
import com.irdeto.jumpstart.domain.PolicyProfile;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

/**
 * File Name: PolicyProfileIngestWrapper.java
 *
 * Description: The wrapper class for PolicyProfile
 *
 * Developed by Tata Elxsi for Irdeto B.V.
 *
 * Creation Date: 05-Feb-2015
 *
 */
public class PolicyProfileIngestWrapper extends AbstractEntityIngestWrapper<PolicyProfile> {

	public void addPolicyNameList(List<String> policyNameList) {
		super.addRelationshipsList(
				PolicyProfile.class,
				WorkflowHelper.POLICY_GROUP_PROFILE_RELATIONSHIP_NAME,
				PolicyGroupProfile.class,
				WorkflowHelper.ATTRIBUTE_POLICY_GROUP_NAME,
				policyNameList
		);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@JsonIgnore
	@Override
	public QueryFilterParameter getMatchingQueryFilter() {
		return WorkflowHelper.getQueryFilterParameter(WorkflowHelper.ATTRIBUTE_POLICY_NAME, getEntity().getPolicyName(), false);
	}

	@Override
	@JsonIgnore
	protected List<String> getCompareLinks() {
		return null;
	}

	@Override
	@JsonIgnore
	protected List<ImageContent> getImageContentList(PolicyProfile entity) {
		return null;
	}

	@Override
	@JsonIgnore
	protected void setImageContentList(
			PolicyProfile entity,
			List<ImageContent> imageContentList
	) {
	}

	@Override
	@JsonIgnore
	public String getUriId() {
		return getEntity().getPolicyName();
	}
}
