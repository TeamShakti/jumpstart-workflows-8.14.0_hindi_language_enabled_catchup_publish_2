package com.irdeto.jumpstart.domain.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;


public class RatingSchemeXML {
	
	private List<String> countryOfSystemList = new ArrayList<>();
	private List<String> otherIncludedCountryList = new ArrayList<>();
	private List<RatingXML> ratingList = new ArrayList<>();
	private String classificationSystem;
	
	@XmlAttribute(name = "classificationSystem")
	public String getClassificationSystem() {
		return classificationSystem;
	}
	public void setClassificationSystem(String classificationSystem) {
		this.classificationSystem = classificationSystem;
	}
	
	@XmlElement(name = "CountryOfSystem")
	public List<String> getCountryOfSystemList() {
		return countryOfSystemList;
	}
	public void setCountryOfSystemList(List<String> countryOfSystemList) {
		this.countryOfSystemList = countryOfSystemList;
	}
	
	@XmlElement(name = "OtherIncludedCountry")
	public List<String> getOtherIncludedCountryList() {
		return otherIncludedCountryList;
	}
	public void setOtherIncludedCountryList(List<String> otherIncludedCountryList) {
		this.otherIncludedCountryList = otherIncludedCountryList;
	}
	
	@XmlElement(name = "Rating")
	public List<RatingXML> getRatingList() {
		return ratingList;
	}
	public void setRatingList(List<RatingXML> ratingList) {
		this.ratingList = ratingList;
	}
	
}
