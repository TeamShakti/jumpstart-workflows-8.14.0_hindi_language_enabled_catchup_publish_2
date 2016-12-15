package com.irdeto.jumpstart.domain.config;

import java.util.ArrayList;
import java.util.List;

public class PolicyGroup {

	private String policyId;
	private String policyGroupName;
	private String policyGroupDescription;
    private Integer licenseProfileReference;
    private List<Policy> policiesList = new ArrayList<>();
	public String getPolicyId() {
		return policyId;
	}
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}
	public String getPolicyGroupName() {
		return policyGroupName;
	}
	public void setPolicyGroupName(String policyGroupName) {
		this.policyGroupName = policyGroupName;
	}
	public String getPolicyGroupDescription() {
		return policyGroupDescription;
	}
	public void setPolicyGroupDescription(String policyGroupDescription) {
		this.policyGroupDescription = policyGroupDescription;
	}
	public Integer getLicenseProfileReference() {
		return licenseProfileReference;
	}
	public void setLicenseProfileReference(Integer licenseProfileReference) {
		this.licenseProfileReference = licenseProfileReference;
	}
	public List<Policy> getPoliciesList() {
		return policiesList;
	}
	public void setPoliciesList(List<Policy> policiesList) {
		this.policiesList = policiesList;
	}
    
}
