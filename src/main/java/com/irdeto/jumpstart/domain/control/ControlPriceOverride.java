package com.irdeto.jumpstart.domain.control;

import javax.xml.bind.annotation.XmlAttribute;

public class ControlPriceOverride {
	private String optionId;
	private String affiliateId;
	private String country;
	private Float amount;
	
	@XmlAttribute(name="OptionId", required=true)
	public String getOptionId() {
		return optionId;
	}
	
	public void setOptionId(String optionId) {
		this.optionId = optionId;
	}
	
	@XmlAttribute(name="AffiliateId", required=false)
	public String getAffiliateId() {
		return affiliateId;
	}
	
	public void setAffiliateId(String affiliateId) {
		this.affiliateId = affiliateId;
	}
	
	@XmlAttribute(name="Country", required=true)
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	@XmlAttribute(name="Amount", required=true)
	public Float getAmount() {
		return amount;
	}
	
	public void setAmount(Float amount) {
		this.amount = amount;
	}
}
