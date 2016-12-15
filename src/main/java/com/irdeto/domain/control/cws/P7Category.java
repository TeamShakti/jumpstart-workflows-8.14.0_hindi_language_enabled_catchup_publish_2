package com.irdeto.domain.control.cws;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.StringUtils;

@XmlType(namespace = "http://schemas.datacontract.org/2004/07/ConsoleService")
public class P7Category {
	private String categoryId;
	private String parentCategoryId;
	private String name;
	private String description;
	private String policyId;
	
	public P7Category() {
		super();
	}

	public P7Category(String categoryId, String parentCategoryId, String name, String policyId, String description) {
		super();
		this.categoryId = categoryId;
		this.parentCategoryId = StringUtils.isBlank(parentCategoryId)?null:parentCategoryId;
		this.name = StringUtils.isBlank(name)?null:name;
		this.description = StringUtils.isBlank(description)?null:description;
		this.policyId = StringUtils.isBlank(policyId)?null:policyId;
	}
	public P7Category(String categoryId) {
		super();
		this.categoryId = categoryId;
	}

	@XmlElement(name = "CategoryId", required = true)
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
    @XmlElement(name = "ParentCategoryId", required = true)
    public String getParentCategoryId() {
		return parentCategoryId;
	}
	public void setParentCategoryId(String parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}
	@XmlElement(name = "Name", required = true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    @XmlElement(name = "Description", required = true)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
    @XmlElement(name = "PolicyId", required = true)
	public String getPolicyId() {
		return policyId;
	}
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

}
