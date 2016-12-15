package com.irdeto.jumpstart.domain.config;

import javax.xml.bind.annotation.XmlAttribute;

public class RatingXML {
	
	private String rating;
	private Integer minimumAge;
	
	@XmlAttribute(name = "rating")
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	
	@XmlAttribute(name = "minimumAge")
	public Integer getMinimumAge() {
		return minimumAge;
	}
	public void setMinimumAge(Integer minimumAge) {
		this.minimumAge = minimumAge;
	}

}
