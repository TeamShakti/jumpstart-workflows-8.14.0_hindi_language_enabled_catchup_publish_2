package com.irdeto.domain.control.cws;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.irdeto.domain.control.cws.objectfactory.PolicyGroupObj;
import com.irdeto.domain.control.cws.objectfactory.PolicyObj;

@XmlType(namespace = "http://schemas.datacontract.org/2004/07/ConsoleService")
public class CMessage {
	private Credential credential;
	private Integer maxResults;
	private Integer startIndex;
	private String searchText;
	private Integer index;
	private Integer policyGroupId;
	private PolicyId policyIds;
	private PolicyObj policyProfile;
	private PolicyGroupObj policyGroupProfile;
	
	@XmlElement(name = "UserCredential", namespace="http://schemas.datacontract.org/2004/07/ConsoleService")
	public Credential getCredential() {
		return credential;
	}

	public void setCredential(Credential credential) {
		this.credential = credential;
	}

	@XmlElement(name = "MaxResults", namespace="http://schemas.datacontract.org/2004/07/ConsoleService")
	public Integer getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}

	@XmlElement(name = "StartIndex", namespace="http://schemas.datacontract.org/2004/07/ConsoleService")
	public Integer getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	@XmlElement(name = "SearchText", namespace="http://schemas.datacontract.org/2004/07/ConsoleService", nillable=true)
	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	@XmlElement(name = "Index", namespace="http://schemas.datacontract.org/2004/07/ConsoleService")
	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	@XmlElement(name = "PolicyGroupId", namespace="http://schemas.datacontract.org/2004/07/ConsoleService")
	public Integer getPolicyGroupId() {
		return policyGroupId;
	}

	public void setPolicyGroupId(Integer policyGroupId) {
		this.policyGroupId = policyGroupId;
	}

	@XmlElement(name = "PolicyIds", namespace="http://schemas.datacontract.org/2004/07/ConsoleService")
	public PolicyId getPolicyIds() {
		return policyIds;
	}

	public void setPolicyIds(PolicyId policyIds) {
		this.policyIds = policyIds;
	}
	
	@XmlElement(name = "PolicyIds", namespace="http://schemas.datacontract.org/2004/07/ConsoleService")
	public PolicyObj getPolicyProfile() {
		return policyProfile;
	}

	public void setPolicyProfile(PolicyObj policyProfile) {
		this.policyProfile = policyProfile;
	}

	@XmlElement(name = "PolicyIds", namespace="http://schemas.datacontract.org/2004/07/ConsoleService")
	public PolicyGroupObj getPolicyGroupProfile() {
		return policyGroupProfile;
	}

	public void setPolicyGroupProfile(PolicyGroupObj policyGroupProfile) {
		this.policyGroupProfile = policyGroupProfile;
	}

}
