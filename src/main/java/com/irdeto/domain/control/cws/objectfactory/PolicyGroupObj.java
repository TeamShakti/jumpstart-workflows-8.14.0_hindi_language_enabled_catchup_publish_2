package com.irdeto.domain.control.cws.objectfactory;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType (namespace = "http://schemas.datacontract.org/2004/07/ConsoleService")
public class PolicyGroupObj {
	
	private String id;
	private String name;
	private String description;
	private List<PolicyObj> policies;
	private List<String> blockedCountryList;
	
	@XmlElement(name = "Id", namespace="http://schemas.datacontract.org/2004/07/ConsoleService")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@XmlElement(name = "Name", namespace="http://schemas.datacontract.org/2004/07/ConsoleService")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlElement(name = "Description", namespace="http://schemas.datacontract.org/2004/07/ConsoleService")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@XmlElement(name = "Policies", namespace="http://schemas.datacontract.org/2004/07/ConsoleService")
	public List<PolicyObj> getPolicies() {
		return policies;
	}
	public void setPolicies(List<PolicyObj> policies) {
		this.policies = policies;
	}
	
	@XmlElement(name = "BlockedCountryList", namespace="http://schemas.datacontract.org/2004/07/ConsoleService")
	public List<String> getBlockedCountryList() {
		return blockedCountryList;
	}
	public void setBlockedCountryList(List<String> blockedCountryList) {
		this.blockedCountryList = blockedCountryList;
	}
	
}
