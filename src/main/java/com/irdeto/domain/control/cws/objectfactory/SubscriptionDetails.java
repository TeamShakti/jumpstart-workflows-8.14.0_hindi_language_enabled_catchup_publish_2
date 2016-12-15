package com.irdeto.domain.control.cws.objectfactory;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType (namespace = "http://schemas.datacontract.org/2004/07/Entriq.Security.BusinessObject")
public class SubscriptionDetails {

	private String id;
	private String billingInterval;
	private String subscriptionGroupId ;
	private String minBillingInterval;
	private String releaseEnd;
	private String releaseDate;
	private String billingEndDate;
	
	@XmlElement(name = "Id", namespace="http://schemas.datacontract.org/2004/07/Entriq.Security.BusinessObject")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@XmlElement(name = "BillingInterval", namespace="http://schemas.datacontract.org/2004/07/Entriq.Security.BusinessObject")
	public String getBillingInterval() {
		return billingInterval;
	}
	public void setBillingInterval(String billingInterval) {
		this.billingInterval = billingInterval;
	}
	
	@XmlElement(name = "SubscriptionGroupId", namespace="http://schemas.datacontract.org/2004/07/Entriq.Security.BusinessObject")
	public String getSubscriptionGroupId() {
		return subscriptionGroupId;
	}
	public void setSubscriptionGroupId(String subscriptionGroupId) {
		this.subscriptionGroupId = subscriptionGroupId;
	}
	
	@XmlElement(name = "MinBillingInterval", namespace="http://schemas.datacontract.org/2004/07/Entriq.Security.BusinessObject")
	public String getMinBillingInterval() {
		return minBillingInterval;
	}
	public void setMinBillingInterval(String minBillingInterval) {
		this.minBillingInterval = minBillingInterval;
	}
	
	@XmlElement(name = "ReleaseEnd", namespace="http://schemas.datacontract.org/2004/07/Entriq.Security.BusinessObject")
	public String getReleaseEnd() {
		return releaseEnd;
	}
	public void setReleaseEnd(String releaseEnd) {
		this.releaseEnd = releaseEnd;
	}
	
	@XmlElement(name = "ReleaseDate", namespace="http://schemas.datacontract.org/2004/07/Entriq.Security.BusinessObject")
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	@XmlElement(name = "BillingEndDate", namespace="http://schemas.datacontract.org/2004/07/Entriq.Security.BusinessObject")
	public String getBillingEndDate() {
		return billingEndDate;
	}
	public void setBillingEndDate(String billingEndDate) {
		this.billingEndDate = billingEndDate;
	}
	
}
