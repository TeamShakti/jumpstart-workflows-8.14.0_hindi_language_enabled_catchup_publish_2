package com.irdeto.jumpstart.domain.control.qsa;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class ControlQSAPolicy {
	private List<String> countryList = new ArrayList<>();
	private String policyId;
	private String name;
	private String description;
	private String redirectURL;
	private String available;
	private String blockUnresolvedIP;
	private String defaultCountry;

	@XmlElementWrapper(name = "CountryList")
	@XmlElement(name = "Country")
	public List<String> getCountryList() {
		return countryList;
	}

	public void setCountryList(List<String> countryList) {
		this.countryList = countryList;
	}

	@XmlAttribute(name = "PolicyId")
	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
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

	@XmlAttribute(name = "RedirectURL")
	public String getRedirectURL() {
		return redirectURL;
	}

	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}

	@XmlAttribute(name = "Available")
	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	@XmlAttribute(name = "BlockUnresolvedIP")
	public String getBlockUnresolvedIP() {
		return blockUnresolvedIP;
	}

	public void setBlockUnresolvedIP(String blockUnresolvedIP) {
		this.blockUnresolvedIP = blockUnresolvedIP;
	}

	@XmlAttribute(name = "DefaultCountry")
	public String getDefaultCountry() {
		return defaultCountry;
	}

	public void setDefaultCountry(String defaultCountry) {
		this.defaultCountry = defaultCountry;
	}
	
}
