package com.irdeto.jumpstart.domain.reference;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_EMPTY)
public class ReferenceEvent extends ReferenceDocumentWithEntitlementAndTitle {
	@JsonIgnore
	@Override
	public String getType() {
		return "event";
	}

	private List<ReferenceGenre> genres = new ArrayList<>();
    private DateTime eventBroadcastDate;
    private DateTime eventBroadcastEndDate;
    private List<ReferenceContributor> contributors = new ArrayList<>();
    private List<ReferenceSubscriptionPackage> subscriptionPackages = new ArrayList<>();
    private List<ReferenceTvodCollection> tvodCollections = new ArrayList<>();
    private List<ReferenceScheduleSlot> scheduleSlots = new ArrayList<>();
    private List<ReferenceProgram> programs = new ArrayList<>();
    private List<ReferenceImageContent> imageContents = new ArrayList<>();
    private List<ReferenceRating> ratings = new ArrayList<>();
	private String screenFormat;

    @JsonProperty("genres")
    public List<ReferenceGenre> getGenres() {
        return genres;
    }
    public void setGenres(List<ReferenceGenre> genres) {
        this.genres = genres;
    }

    @JsonProperty("eventBroadcastDate")
    public DateTime getEventBroadcastDate() {
        return eventBroadcastDate;
    }
    public void setEventBroadcastDate(DateTime eventBroadcastDate) {
        this.eventBroadcastDate = eventBroadcastDate;
    }

    @JsonProperty("eventBroadcastEndDate")
    public DateTime getEventBroadcastEndDate() {
        return eventBroadcastEndDate;
    }
    public void setEventBroadcastEndDate(DateTime eventBroadcastEndDate) {
        this.eventBroadcastEndDate = eventBroadcastEndDate;
    }

    @JsonProperty("contributors")
    public List<ReferenceContributor> getContributors() {
        return contributors;
    }
    public void setContributors(List<ReferenceContributor> contributors) {
        this.contributors = contributors;
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
    
    @JsonProperty("scheduleSlots")
    public List<ReferenceScheduleSlot> getScheduleSlots() {
        return scheduleSlots;
    }
    public void setScheduleSlots(List<ReferenceScheduleSlot> scheduleSlots) {
        this.scheduleSlots = scheduleSlots;
    }

    @JsonProperty("programs")
    public List<ReferenceProgram> getPrograms() {
        return programs;
    }
    public void setPrograms(List<ReferenceProgram> programs) {
        this.programs = programs;
    }

    @JsonProperty("imageContents")
    public List<ReferenceImageContent> getImageContents() {
        return imageContents;
    }
    public void setImageContents(List<ReferenceImageContent> imageContents) {
        this.imageContents = imageContents;
    }

    @JsonProperty("ratings")
    public List<ReferenceRating> getRatings() {
        return ratings;
    }
    @JsonProperty("ratings")
    public void setRatings(List<ReferenceRating> ratings) {
        this.ratings = ratings;
    }

	@JsonProperty("screenFormat")
	public String getScreenFormat() {
		return screenFormat;
	}
	public void setScreenFormat(String screenFormat) {
		this.screenFormat = screenFormat;
	}
}
