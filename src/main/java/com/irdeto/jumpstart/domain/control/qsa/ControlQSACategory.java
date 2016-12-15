package com.irdeto.jumpstart.domain.control.qsa;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;


public class ControlQSACategory {
	private String accountId;
	private String categoryId;
	private String name;
	private String description;
	private String policyId;
	private List<ControlQSASubPolicy> subPolicyList = new ArrayList<>();
	@XmlAttribute(name = "AccountId")
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	@XmlAttribute(name = "CategoryId")
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	@XmlAttribute(name = "Name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@XmlAttribute(name = "Description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@XmlAttribute(name = "PolicyId")
	public String getPolicyId() {
		return policyId;
	}
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}
	@XmlElementWrapper(name = "SubPolicyList")
	@XmlElement(name = "SubPolicy")
	public List<ControlQSASubPolicy> getSubPolicyList() {
		return subPolicyList;
	}
	public void setSubPolicyList(List<ControlQSASubPolicy> subPolicyList) {
		this.subPolicyList = subPolicyList;
	}
}
