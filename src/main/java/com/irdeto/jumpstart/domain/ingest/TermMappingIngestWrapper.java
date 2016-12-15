package com.irdeto.jumpstart.domain.ingest;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.domain.mediamanager.QueryFilterParameter;
import com.irdeto.jumpstart.domain.DeviceProfile;
import com.irdeto.jumpstart.domain.ImageContent;
import com.irdeto.jumpstart.domain.PolicyGroupProfile;
import com.irdeto.jumpstart.domain.PolicyProfile;
import com.irdeto.jumpstart.domain.Term;
import com.irdeto.jumpstart.domain.TermMapping;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

/**
 * File Name: TermMappingIngestWrapper.java
 *
 * Description: The wrapper class for TermMapping
 *
 * Developed by Tata Elxsi for Irdeto B.V.
 *
 * Creation Date: 15-Oct-2014
 *
 */
public class TermMappingIngestWrapper extends AbstractEntityIngestWrapper<TermMapping> {

	/**
	 * This method will add the relationship between TermMapping and DeviceProfile entities
	 *
	 * @param deviceProfileNameList
	 * @return none
	 */
	public void addDeviceProfileNameList(List<String> deviceProfileNameList) {
		super.addRelationshipsList(
				TermMapping.class,
				WorkflowHelper.DEVICE_PROFILE_RELATIONSHIP_NAME,
				DeviceProfile.class,
				WorkflowHelper.ATTRIBUTE_NAME_NAME,
				deviceProfileNameList
		);
	}

	public void addTermContractNameList(List<String> contractNameList) {
		super.addRelationshipsList(
				TermMapping.class,
				WorkflowHelper.TERM_RELATIONSHIP_NAME,
				Term.class,
				WorkflowHelper.ATTRIBUTE_NAME_CONTRACT_NAME,
				contractNameList
		);
	}

	public void addPolicyGroupNameList(List<String> policGroupNameList) {
		super.addRelationshipsList(
				TermMapping.class,
				WorkflowHelper.POLICY_GROUP_PROFILE_RELATIONSHIP_NAME,
				PolicyGroupProfile.class,
				WorkflowHelper.ATTRIBUTE_POLICY_GROUP_NAME,
				policGroupNameList
		);
	}

	public void addPolicyNameList(List<String> policyNameList) {
		super.addRelationshipsList(
				TermMapping.class,
				WorkflowHelper.POLICY_PROFILE_RELATIONSHIP_NAME,
				PolicyProfile.class,
				WorkflowHelper.ATTRIBUTE_POLICY_NAME,
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
		return WorkflowHelper.getQueryFilterParameter(WorkflowHelper.ATTRIBUTE_NAME_CONTRACT_NAME, getEntity().getContractName(), false);
	}

	@Override
	@JsonIgnore
	protected List<ImageContent> getImageContentList(TermMapping entity) {
		return null;
	}


	@Override
	@JsonIgnore
	protected void setImageContentList(
			TermMapping entity,
			List<ImageContent> imageContentList
	) {
	}

	@Override
	@JsonIgnore
	public String getUriId() {
		return getEntity().getContractName();
	}

	@Override
	@JsonIgnore
	protected List<String> getCompareLinks() {
		return Arrays.asList(WorkflowHelper.DEVICE_PROFILE_RELATIONSHIP_NAME,
				WorkflowHelper.POLICY_GROUP_PROFILE_RELATIONSHIP_NAME,
				WorkflowHelper.POLICY_PROFILE_RELATIONSHIP_NAME);
	}
}

// End TermMappingIngestWrapper
