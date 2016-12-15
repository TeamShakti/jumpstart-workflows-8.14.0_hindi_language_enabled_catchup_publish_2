package com.irdeto.domain.control.cws;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType (namespace = "http://schemas.datacontract.org/2004/07/Entriq.Security.BusinessObject")
public class Price {

	private String id;
	private String country;
	private String affiliate;
	private String Currency;
	private String amount;
	private String saleAmount;
	
	@XmlElement(name = "Id", namespace="http://schemas.datacontract.org/2004/07/Entriq.Security.BusinessObject")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@XmlElement(name = "Country", namespace="http://schemas.datacontract.org/2004/07/Entriq.Security.BusinessObject")
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	@XmlElement(name = "Affiliate", namespace="http://schemas.datacontract.org/2004/07/Entriq.Security.BusinessObject")
	public String getAffiliate() {
		return affiliate;
	}
	public void setAffiliate(String affiliate) {
		this.affiliate = affiliate;
	}
	
	@XmlElement(name = "Currency", namespace="http://schemas.datacontract.org/2004/07/Entriq.Security.BusinessObject")
	public String getCurrency() {
		return Currency;
	}
	public void setCurrency(String currency) {
		Currency = currency;
	}
	
	@XmlElement(name = "Amount", namespace="http://schemas.datacontract.org/2004/07/Entriq.Security.BusinessObject")
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	@XmlElement(name = "SaleAmount", namespace="http://schemas.datacontract.org/2004/07/Entriq.Security.BusinessObject")
	public String getSaleAmount() {
		return saleAmount;
	}
	public void setSaleAmount(String saleAmount) {
		this.saleAmount = saleAmount;
	}
	
	
}
