package com.irdeto.domain.control.cws.objectfactory;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.irdeto.domain.control.cws.Price;

@XmlType (namespace = "http://schemas.datacontract.org/2004/07/ConsoleService")
public class PolicyObj {

	private String id;
	private String name;
	private String description;
	private String type;
	private String entitlementStartMode;
	private String entitlementDuration;
	private Boolean requireSubscription;
	private Integer subscriptionReference;
	private Integer licenseProfileReference;
	private Boolean requireAuthentication;
    private Price price;
    private SubscriptionDetails subscriptionDetails;
	private Integer numberOfDevices;
	private String productTaxType;
	private Boolean billImmediately;
	private String syndicateAccountId;
	
	@XmlElement(name = "Id", namespace="http://schemas.datacontract.org/2004/07/Entriq.Security.BusinessObject")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@XmlElement(name = "Name", namespace="http://schemas.datacontract.org/2004/07/Entriq.Security.BusinessObject")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlElement(name = "Description", namespace="http://schemas.datacontract.org/2004/07/Entriq.Security.BusinessObject")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@XmlElement(name = "Type", namespace="http://schemas.datacontract.org/2004/07/Entriq.Security.BusinessObject")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@XmlElement(name = "EntStartMode", namespace="http://schemas.datacontract.org/2004/07/Entriq.Security.BusinessObject")
	public String getEntitlementStartMode() {
		return entitlementStartMode;
	}
	public void setEntitlementStartMode(String entitlementStartMode) {
		this.entitlementStartMode = entitlementStartMode;
	}
	
	@XmlElement(name = "EntDuration", namespace="http://schemas.datacontract.org/2004/07/Entriq.Security.BusinessObject")
	public String getEntitlementDuration() {
		return entitlementDuration;
	}
	public void setEntitlementDuration(String entitlementDuration) {
		this.entitlementDuration = entitlementDuration;
	}
	
	@XmlElement(name = "ReqSubscription", namespace="http://schemas.datacontract.org/2004/07/Entriq.Security.BusinessObject")
	public Boolean getRequireSubscription() {
		return requireSubscription;
	}
	public void setRequireSubscription(Boolean requireSubscription) {
		this.requireSubscription = requireSubscription;
	}
	
	@XmlElement(name = "SubscriptionRef", namespace="http://schemas.datacontract.org/2004/07/Entriq.Security.BusinessObject")
	public Integer getSubscriptionReference() {
		return subscriptionReference;
	}
	public void setSubscriptionReference(Integer subscriptionReference) {
		this.subscriptionReference = subscriptionReference;
	}
	
	@XmlElement(name = "LicenseProfileRef", namespace="http://schemas.datacontract.org/2004/07/Entriq.Security.BusinessObject")
	public Integer getLicenseProfileReference() {
		return licenseProfileReference;
	}
	public void setLicenseProfileReference(Integer licenseProfileReference) {
		this.licenseProfileReference = licenseProfileReference;
	}
	
	@XmlElement(name = "ReqAuthentication", namespace="http://schemas.datacontract.org/2004/07/Entriq.Security.BusinessObject")
	public Boolean getRequireAuthentication() {
		return requireAuthentication;
	}
	public void setRequireAuthentication(Boolean requireAuthentication) {
		this.requireAuthentication = requireAuthentication;
	}
	
	@XmlElement(name = "PriceTable", namespace="http://schemas.datacontract.org/2004/07/Entriq.Security.BusinessObject")
	public Price getPrice() {
		return price;
	}
	public void setPrice(Price price) {
		this.price = price;
	}
	
	@XmlElement(name = "SubscriptionDetails", namespace="http://schemas.datacontract.org/2004/07/Entriq.Security.BusinessObject")
	public SubscriptionDetails getSubscriptionDetails() {
		return subscriptionDetails;
	}
	public void setSubscriptionDetails(SubscriptionDetails subscriptionDetails) {
		this.subscriptionDetails = subscriptionDetails;
	}
	
	@XmlElement(name = "NoDevices", namespace="http://schemas.datacontract.org/2004/07/Entriq.Security.BusinessObject")
	public Integer getNumberOfDevices() {
		return numberOfDevices;
	}
	public void setNumberOfDevices(Integer numberOfDevices) {
		this.numberOfDevices = numberOfDevices;
	}
	
	@XmlElement(name = "ProductTaxType", namespace="http://schemas.datacontract.org/2004/07/Entriq.Security.BusinessObject")
	public String getProductTaxType() {
		return productTaxType;
	}
	public void setProductTaxType(String productTaxType) {
		this.productTaxType = productTaxType;
	}
	
	@XmlElement(name = "BillImmediately", namespace="http://schemas.datacontract.org/2004/07/Entriq.Security.BusinessObject")
	public Boolean getBillImmediately() {
		return billImmediately;
	}
	public void setBillImmediately(Boolean billImmediately) {
		this.billImmediately = billImmediately;
	}
	
	@XmlElement(name = "SyndicateAccountId", namespace="http://schemas.datacontract.org/2004/07/Entriq.Security.BusinessObject")
	public String getSyndicateAccountId() {
		return syndicateAccountId;
	}
	public void setSyndicateAccountId(String syndicateAccountId) {
		this.syndicateAccountId = syndicateAccountId;
	}
	
}
