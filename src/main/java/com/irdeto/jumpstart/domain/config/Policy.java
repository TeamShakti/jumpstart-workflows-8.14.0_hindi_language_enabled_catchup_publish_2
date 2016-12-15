package com.irdeto.jumpstart.domain.config;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;

public class Policy {
	private String policyId;
	private String policyName;
	private String policyDescription;
	private String policyType;
	private String entitlementStartMode;
	private String entitlementDuration;
	private Boolean requireSubscription;
	private Integer subscriptionReference;
	private Integer licenseProfileReference;
	private Boolean requireAuthentication;
	private Map<String, Map<String, String>> priceTable = new HashMap<String, Map<String, String>>();
	private Integer subscriptionBillingInterval;
	private Integer subscriptionGroupId;
	private Integer subscriptionId;
	private DateTime subscriptionReleaseDate;
	private DateTime subscriptionReleaseEnd;
	private Integer subscriptionMinimumBillingInterval;
	private DateTime subscriptionBillingEndDate;
	private Integer numberOfDevices;
	private String productTaxType;
	private Boolean billImmediately;
	private String syndicateAccountId;
	
	public String getPolicyId() {
		return policyId;
	}
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}
	public String getPolicyName() {
		return policyName;
	}
	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}
	public String getPolicyDescription() {
		return policyDescription;
	}
	public void setPolicyDescription(String policyDescription) {
		this.policyDescription = policyDescription;
	}
	public String getPolicyType() {
		return policyType;
	}
	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}
	public String getEntitlementStartMode() {
		return entitlementStartMode;
	}
	public void setEntitlementStartMode(String entitlementStartMode) {
		this.entitlementStartMode = entitlementStartMode;
	}
	public String getEntitlementDuration() {
		return entitlementDuration;
	}
	public void setEntitlementDuration(String entitlementDuration) {
		this.entitlementDuration = entitlementDuration;
	}
	public Boolean getRequireSubscription() {
		return requireSubscription;
	}
	public void setRequireSubscription(Boolean requireSubscription) {
		this.requireSubscription = requireSubscription;
	}
	public Integer getSubscriptionReference() {
		return subscriptionReference;
	}
	public void setSubscriptionReference(Integer subscriptionReference) {
		this.subscriptionReference = subscriptionReference;
	}
	public Integer getLicenseProfileReference() {
		return licenseProfileReference;
	}
	public void setLicenseProfileReference(Integer licenseProfileReference) {
		this.licenseProfileReference = licenseProfileReference;
	}
	public Boolean getRequireAuthentication() {
		return requireAuthentication;
	}
	public void setRequireAuthentication(Boolean requireAuthentication) {
		this.requireAuthentication = requireAuthentication;
	}
	public Map<String, Map<String, String>> getPriceTable() {
		return priceTable;
	}

	public void setPriceTable(Map<String, Map<String, String>> priceTable) {
		this.priceTable = priceTable;
	}
	public Integer getSubscriptionBillingInterval() {
		return subscriptionBillingInterval;
	}
	public void setSubscriptionBillingInterval(Integer subscriptionBillingInterval) {
		this.subscriptionBillingInterval = subscriptionBillingInterval;
	}
	public Integer getSubscriptionGroupId() {
		return subscriptionGroupId;
	}
	public void setSubscriptionGroupId(Integer subscriptionGroupId) {
		this.subscriptionGroupId = subscriptionGroupId;
	}
	public Integer getSubscriptionId() {
		return subscriptionId;
	}
	public void setSubscriptionId(Integer subscriptionId) {
		this.subscriptionId = subscriptionId;
	}
	public DateTime getSubscriptionReleaseDate() {
		return subscriptionReleaseDate;
	}
	public void setSubscriptionReleaseDate(DateTime subscriptionReleaseDate) {
		this.subscriptionReleaseDate = subscriptionReleaseDate;
	}
	public DateTime getSubscriptionReleaseEnd() {
		return subscriptionReleaseEnd;
	}
	public void setSubscriptionReleaseEnd(DateTime subscriptionReleaseEnd) {
		this.subscriptionReleaseEnd = subscriptionReleaseEnd;
	}
	public Integer getSubscriptionMinimumBillingInterval() {
		return subscriptionMinimumBillingInterval;
	}
	public void setSubscriptionMinimumBillingInterval(
			Integer subscriptionMinimumBillingInterval) {
		this.subscriptionMinimumBillingInterval = subscriptionMinimumBillingInterval;
	}
	public DateTime getSubscriptionBillingEndDate() {
		return subscriptionBillingEndDate;
	}
	public void setSubscriptionBillingEndDate(DateTime subscriptionBillingEndDate) {
		this.subscriptionBillingEndDate = subscriptionBillingEndDate;
	}
	public Integer getNumberOfDevices() {
		return numberOfDevices;
	}
	public void setNumberOfDevices(Integer numberOfDevices) {
		this.numberOfDevices = numberOfDevices;
	}
	public String getProductTaxType() {
		return productTaxType;
	}
	public void setProductTaxType(String productTaxType) {
		this.productTaxType = productTaxType;
	}
	public Boolean getBillImmediately() {
		return billImmediately;
	}
	public void setBillImmediately(Boolean billImmediately) {
		this.billImmediately = billImmediately;
	}
	public String getSyndicateAccountId() {
		return syndicateAccountId;
	}
	public void setSyndicateAccountId(String syndicateAccountId) {
		this.syndicateAccountId = syndicateAccountId;
	}
	
}
