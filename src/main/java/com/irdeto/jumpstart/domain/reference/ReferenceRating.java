package com.irdeto.jumpstart.domain.reference;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_EMPTY)
public class ReferenceRating extends ReferenceDocument {
    private List<String> countries = new ArrayList<>();
    private String ratingLabel;
    private Integer minimumAge;
    private String classification;

    @JsonProperty("countries")
    public List<String> getCountries() {
        return countries;
    }
    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    @JsonProperty("ratingLabel")
    public String getRatingLabel() {
        return ratingLabel;
    }
    public void setRatingLabel(String ratingLabel) {
        this.ratingLabel = ratingLabel;
    }

    @JsonProperty("minimumAge")
    public Integer getMinimumAge() {
        return minimumAge;
    }
    public void setMinimumAge(Integer minimumAge) {
        this.minimumAge = minimumAge;
    }

    @JsonProperty("classification")
    public String getClassification() {
        return classification;
    }
    public void setClassification(String classification) {
        this.classification = classification;
    }
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "Rating";
	}
   }
