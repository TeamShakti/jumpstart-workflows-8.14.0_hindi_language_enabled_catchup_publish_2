package com.irdeto.jumpstart.domain.reference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_EMPTY)
public class ReferenceProgram extends ReferenceDocumentWithEntitlementAndTitle {
	@Override
	@JsonIgnore
	public String getType() {
		return "program";
	}

    private List<ReferenceGenre> genres = new ArrayList<>();
    private ReferenceSeries series;
    private ReferenceBrand brand;
    private DateTime linearBroadcastDate;
    private List<ReferenceContributor> contributors = new ArrayList<>();
    private List<ReferenceEvent> events = new ArrayList<>();
    private List<ReferenceScheduleSlot> scheduleSlots = new ArrayList<>();
    private List<ReferenceSubscriptionPackage> subscriptionPackages = new ArrayList<>();
    private List<ReferenceTvodCollection> tvodCollections = new ArrayList<>();
    private List<ReferenceImageContent> imageContents = new ArrayList<>();
    private List<ReferenceRating> ratings = new ArrayList<>();
	private String episodeName;
	private String episodeId;
	//code by nitin
	private String nDSOfferID;
	private Integer ndsPrice;
	private Map<String, String> genre;
	private String rating;
	private Integer maxconcurrentstream;
	private Boolean isDownloadable;
	private Integer downloadExpiry;
	 private Integer mSORating;
	private Boolean isClosedCaptioning;
	private String displayRunTime;
	private Integer yearOfRelease;
	private Boolean isSeasonPremiere;
	private Boolean isSeasonFinale;

    @JsonProperty("genres")
    public List<ReferenceGenre> getGenres() {
        return genres;
    }
    public void setGenres(List<ReferenceGenre> genres) {
        this.genres = genres;
    }

    @JsonProperty("series")
    public ReferenceSeries getSeries() {
        return series;
    }
    public void setSeries(ReferenceSeries series) {
        this.series = series;
    }

    @JsonProperty("brand")
    public ReferenceBrand getBrand() {
        return brand;
    }
    public void setBrand(ReferenceBrand brand) {
        this.brand = brand;
    }

    @JsonProperty("linearBroadcastDate")
    public DateTime getLinearBroadcastDate() {
        return linearBroadcastDate;
    }
    public void setLinearBroadcastDate(DateTime linearBroadcastDate) {
        this.linearBroadcastDate = linearBroadcastDate;
    }

    @JsonProperty("contributors")
    public List<ReferenceContributor> getContributors() {
        return contributors;
    }
    public void setContributors(List<ReferenceContributor> contributors) {
        this.contributors = contributors;
    }

    @JsonProperty("events")
    public List<ReferenceEvent> getEvents() {
        return events;
    }
    public void setEvents(List<ReferenceEvent> events) {
        this.events = events;
    }

    @JsonProperty("scheduleSlots")
    public List<ReferenceScheduleSlot> getScheduleSlots() {
        return scheduleSlots;
    }
    public void setScheduleSlots(List<ReferenceScheduleSlot> scheduleSlots) {
        this.scheduleSlots = scheduleSlots;
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
    public void setRatings(List<ReferenceRating> ratings) {
        this.ratings = ratings;
    }

	@JsonProperty("episodeName")
	public String getEpisodeName() {
		return episodeName;
	}
	public void setEpisodeName(String episodeName) {
		this.episodeName = episodeName;
	}

	@JsonProperty("episodeId")
	public String getEpisodeId() {
		return episodeId;
	}
	public void setEpisodeId(String episodeId) {
		this.episodeId = episodeId;
	}

	@JsonProperty("NDSOfferID")
    public String getNDSOfferID() {
        return nDSOfferID;
    }
	 public void setNDSOfferID(String nDSOfferID) {
	        this.nDSOfferID = nDSOfferID;
	    }
	 
	 @JsonProperty("ndsPrice")
	    public Integer getNdsPrice() {
	        return ndsPrice;
	    }
	 public void setNdsPrice(Integer ndsPrice) {
	        this.ndsPrice = ndsPrice;
	    }
	 
	 @JsonProperty("maxconcurrentstream")
	    public Integer getMaxconcurrentstream() {
	        return maxconcurrentstream;
	    }
	 public void setMaxconcurrentstream(Integer maxconcurrentstream) {
	        this.maxconcurrentstream = maxconcurrentstream;
	    }
	 
	 @JsonProperty("isDownloadable")
	    public Boolean getIsDownloadable() {
	        return isDownloadable;
	    }
	 public void setIsDownloadable(Boolean isDownloadable) {
	        this.isDownloadable = isDownloadable;
	    }
	 
	 @JsonProperty("downloadExpiry")
	    public Integer getDownloadExpiry() {
	        return downloadExpiry;
	    }
	 public void setDownloadExpiry(Integer downloadExpiry) {
	        this.downloadExpiry = downloadExpiry;
	    }
	 
	 @JsonProperty("MSORating")
	    public Integer getMSORating() {
	        return mSORating;
	    }
	 public void setMSORating(Integer mSORating) {
	        this.mSORating = mSORating;
	    }
	
	 @JsonProperty("genre")
		public Map<String, String> getGenre() {
			return genre;
		}

		@JsonProperty("genre")
		public void setGenre(Map<String, String> genre) {
			this.genre = genre;
		}

		@JsonProperty("rating")
		public String getRating() {
			return rating;
		}

		@JsonProperty("rating")
		public void setRating(String rating) {
			this.rating = rating;
		}
	
	@JsonProperty("isClosedCaptioning")
	public Boolean getIsClosedCaptioning() {
		return isClosedCaptioning;
	}
	public void setIsClosedCaptioning(Boolean isClosedCaptioning) {
		this.isClosedCaptioning = isClosedCaptioning;
	}

	@JsonProperty("displayRunTime")
	public String getDisplayRunTime() {
		return displayRunTime;
	}
	public void setDisplayRunTime(String displayRunTime) {
		this.displayRunTime = displayRunTime;
	}

	@JsonProperty("yearOfRelease")
	public Integer getYearOfRelease() {
		return yearOfRelease;
	}
	public void setYearOfRelease(Integer yearOfRelease) {
		this.yearOfRelease = yearOfRelease;
	}

	@JsonProperty("isSeasonPremiere")
	public Boolean getIsSeasonPremiere() {
		return isSeasonPremiere;
	}
	public void setIsSeasonPremiere(Boolean isSeasonPremiere) {
		this.isSeasonPremiere = isSeasonPremiere;
	}

	@JsonProperty("isSeasonFinale")
	public Boolean getIsSeasonFinale() {
		return isSeasonFinale;
	}
	public void setIsSeasonFinale(Boolean isSeasonFinale) {
		this.isSeasonFinale = isSeasonFinale;
	}
}
