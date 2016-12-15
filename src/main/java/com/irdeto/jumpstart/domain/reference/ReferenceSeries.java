package com.irdeto.jumpstart.domain.reference;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_EMPTY)
public class ReferenceSeries extends ReferenceDocumentWithEntitlementAndTitle {
	@Override
	@JsonIgnore
	public String getType() {
		return "series";
	}

    private ReferenceBrand brand;
    private List<ReferenceSubscriptionPackage> subscriptionPackages = new ArrayList<>();
    private List<ReferenceTvodCollection> tvodCollections = new ArrayList<>();
    private List<ReferenceRating> ratings = new ArrayList<>();
    private List<ReferenceGenre> genres = new ArrayList<>();
    private List<ReferenceImageContent> imageContents = new ArrayList<>();
	private String season;

    @JsonProperty("brand")
    public ReferenceBrand getBrand() {
        return brand;
    }
    public void setBrand(ReferenceBrand brand) {
        this.brand = brand;
    }

    @JsonProperty("subscriptionPackages")
    public List<ReferenceSubscriptionPackage> getSubscriptionPackages() {
        return subscriptionPackages;
    }
    public void setSubscriptionPackages(List<ReferenceSubscriptionPackage> subscriptionPackages) {
        this.subscriptionPackages = subscriptionPackages;
    }

    @JsonProperty("tvodCollections")
    public List<ReferenceTvodCollection> getTvodCollections() {
        return tvodCollections;
    }
    public void setTvodCollections(List<ReferenceTvodCollection> tvodCollections) {
        this.tvodCollections = tvodCollections;
    }

    @JsonProperty("ratings")
    public List<ReferenceRating> getRatings() {
        return ratings;
    }
    public void setRatings(List<ReferenceRating> ratings) {
        this.ratings = ratings;
    }

    @JsonProperty("genres")
    public List<ReferenceGenre> getGenres() {
        return genres;
    }
    public void setGenres(List<ReferenceGenre> genres) {
        this.genres = genres;
    }

    @JsonProperty("imageContents")
    public List<ReferenceImageContent> getImageContents() {
        return imageContents;
    }
    public void setImageContents(List<ReferenceImageContent> imageContents) {
        this.imageContents = imageContents;
    }

	@JsonProperty("season")
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
}
