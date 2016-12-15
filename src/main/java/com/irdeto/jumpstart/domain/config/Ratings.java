package com.irdeto.jumpstart.domain.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Ratings")
public class Ratings {
	
	private List<RatingSchemeXML> ratingSchemeList = new ArrayList<>();

	@XmlElement(name = "RatingScheme")
	public List<RatingSchemeXML> getRatingSchemeList() {
		return ratingSchemeList;
	}

	public void setRatingSchemeList(List<RatingSchemeXML> ratingSchemeList) {
		this.ratingSchemeList = ratingSchemeList;
	}
}
