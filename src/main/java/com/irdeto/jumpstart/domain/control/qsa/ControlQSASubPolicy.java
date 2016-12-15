package com.irdeto.jumpstart.domain.control.qsa;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class ControlQSASubPolicy {
	private String syndicatorId;
	private String name;
	private String description;
	private String optionId;
	private String type;
	private String drmProfileId;
	private String whitelistLicenseProfileGroupId;
	private String blacklistLicenseProfileGroupId;
	private String deviceGroupId;
	private String rw;
	private String isPackage;
	private String recurring;
	private String internalPackage;
	private String contentPackage;
	private String startMode;
	private String tickets;
	private String freeMonths;
	private String noDevices;
	private String permanentRight;
	private String payment;
	private String price;
	private String priceDuration;
	private String currency;
	private String productTaxType;
	private String noOfInstallments;
	private String bindingMonths;
	private String discountPackage;
	private String billingEndDate;
	private String billImmediately;
	private String accessTime;
	private String country;
	private List<String> countryList = new ArrayList<>();
	@XmlAttribute(name = "SyndicatorId")
	public String getSyndicatorId() {
		return syndicatorId;
	}
	public void setSyndicatorId(String syndicatorId) {
		this.syndicatorId = syndicatorId;
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
	@XmlAttribute(name = "OptionId")
	public String getOptionId() {
		return optionId;
	}
	public void setOptionId(String optionId) {
		this.optionId = optionId;
	}
	@XmlAttribute(name = "Type")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@XmlAttribute(name = "DRMProfileId")
	public String getDrmProfileId() {
		return drmProfileId;
	}
	public void setDrmProfileId(String drmProfileId) {
		this.drmProfileId = drmProfileId;
	}
	@XmlAttribute(name = "WhitelistLicenseProfileGroupId")
	public String getWhitelistLicenseProfileGroupId() {
		return whitelistLicenseProfileGroupId;
	}
	public void setWhitelistLicenseProfileGroupId(
			String whitelistLicenseProfileGroupId) {
		this.whitelistLicenseProfileGroupId = whitelistLicenseProfileGroupId;
	}
	@XmlAttribute(name = "BlacklistLicenseProfileGroupId")
	public String getBlacklistLicenseProfileGroupId() {
		return blacklistLicenseProfileGroupId;
	}
	public void setBlacklistLicenseProfileGroupId(
			String blacklistLicenseProfileGroupId) {
		this.blacklistLicenseProfileGroupId = blacklistLicenseProfileGroupId;
	}
	@XmlAttribute(name = "DeviceGroupId")
	public String getDeviceGroupId() {
		return deviceGroupId;
	}
	public void setDeviceGroupId(String deviceGroupId) {
		this.deviceGroupId = deviceGroupId;
	}
	@XmlAttribute(name = "RW")
	public String getRw() {
		return rw;
	}
	public void setRw(String rw) {
		this.rw = rw;
	}
	@XmlAttribute(name = "Package")
	public String getIsPackage() {
		return isPackage;
	}
	public void setIsPackage(String isPackage) {
		this.isPackage = isPackage;
	}
	@XmlAttribute(name = "Recurring")
	public String getRecurring() {
		return recurring;
	}
	public void setRecurring(String recurring) {
		this.recurring = recurring;
	}
	@XmlAttribute(name = "InternalPackage")
	public String getInternalPackage() {
		return internalPackage;
	}
	public void setInternalPackage(String internalPackage) {
		this.internalPackage = internalPackage;
	}
	
	@XmlAttribute(name = "ContentPackage")
	public String getContentPackage() {
		return contentPackage;
	}
	public void setContentPackage(String contentPackage) {
		this.contentPackage = contentPackage;
	}
	@XmlAttribute(name = "StartMode")
	public String getStartMode() {
		return startMode;
	}
	public void setStartMode(String startMode) {
		this.startMode = startMode;
	}
	@XmlAttribute(name = "Tickets")
	public String getTickets() {
		return tickets;
	}
	public void setTickets(String tickets) {
		this.tickets = tickets;
	}
	@XmlAttribute(name = "FreeMonths")
	public String getFreeMonths() {
		return freeMonths;
	}
	public void setFreeMonths(String freeMonths) {
		this.freeMonths = freeMonths;
	}
	@XmlAttribute(name = "NoDevices")
	public String getNoDevices() {
		return noDevices;
	}
	public void setNoDevices(String noDevices) {
		this.noDevices = noDevices;
	}
	@XmlAttribute(name = "PermanentRight")
	public String getPermanentRight() {
		return permanentRight;
	}
	public void setPermanentRight(String permanentRight) {
		this.permanentRight = permanentRight;
	}
	@XmlAttribute(name = "Payment")
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	@XmlAttribute(name = "Price")
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	@XmlAttribute(name = "PriceDuration")
	public String getPriceDuration() {
		return priceDuration;
	}
	public void setPriceDuration(String priceDuration) {
		this.priceDuration = priceDuration;
	}
	@XmlAttribute(name = "Currency")
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	@XmlAttribute(name = "ProductTaxType")
	public String getProductTaxType() {
		return productTaxType;
	}
	public void setProductTaxType(String productTaxType) {
		this.productTaxType = productTaxType;
	}
	@XmlAttribute(name = "NoOfInstallments")
	public String getNoOfInstallments() {
		return noOfInstallments;
	}
	public void setNoOfInstallments(String noOfInstallments) {
		this.noOfInstallments = noOfInstallments;
	}
	@XmlAttribute(name = "BindingMonths")
	public String getBindingMonths() {
		return bindingMonths;
	}
	public void setBindingMonths(String bindingMonths) {
		this.bindingMonths = bindingMonths;
	}
	@XmlAttribute(name = "DiscountPackage")
	public String getDiscountPackage() {
		return discountPackage;
	}
	public void setDiscountPackage(String discountPackage) {
		this.discountPackage = discountPackage;
	}
	@XmlAttribute(name = "BillingEndDate")
	public String getBillingEndDate() {
		return billingEndDate;
	}
	public void setBillingEndDate(String billingEndDate) {
		this.billingEndDate = billingEndDate;
	}
	@XmlAttribute(name = "BillImmediately")
	public String getBillImmediately() {
		return billImmediately;
	}
	public void setBillImmediately(String billImmediately) {
		this.billImmediately = billImmediately;
	}
	@XmlAttribute(name = "AccessTime")
	public String getAccessTime() {
		return accessTime;
	}
	public void setAccessTime(String accessTime) {
		this.accessTime = accessTime;
	}
	@XmlAttribute(name = "Country")
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	@XmlElementWrapper(name = "CountryList")
	@XmlElement(name = "Country")
	public List<String> getCountryList() {
		return countryList;
	}
	public void setCountryList(List<String> countryList) {
		this.countryList = countryList;
	}
	
	
}
