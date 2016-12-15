package com.irdeto.jumpstart.domain.reference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_EMPTY)
public class ReferenceScheduleSlot extends ReferenceDocumentWithEntitlementAndTitle {
	@Override
	public String getType() {
		return "scheduleSlot";
	}

	private DateTime linearBroadcastDate;
	private DateTime linearBroadcastEndDate;
	private Map<String, String> genre;
	private List<ReferenceEvent> events = new ArrayList<>();
	private List<ReferenceProgram> programs = new ArrayList<>();
	private String episodeName;
	private String episodeId;
	// code by nitin
		private Boolean catchUp;
		
		private Boolean downloadable;
		private Boolean sTBEnabled;
	private String rating;
	private String screenFormat;
	private String imageUrl;

	@JsonProperty("linearBroadcastDate")
	public DateTime getLinearBroadcastDate() {
		return linearBroadcastDate;
	}

	public void setLinearBroadcastDate(DateTime linearBroadcastDate) {
		this.linearBroadcastDate = linearBroadcastDate;
	}

	@JsonProperty("linearBroadcastEndDate")
	public DateTime getLinearBroadcastEndDate() {
		return linearBroadcastEndDate;
	}

	public void setLinearBroadcastEndDate(DateTime linearBroadcastEndDate) {
		this.linearBroadcastEndDate = linearBroadcastEndDate;
	}

	@JsonProperty("events")
	public List<ReferenceEvent> getEvents() {
		return events;
	}

	public void setEvents(List<ReferenceEvent> events) {
		this.events = events;
	}

	@JsonProperty("programs")
	public List<ReferenceProgram> getPrograms() {
		return programs;
	}

	public void setPrograms(List<ReferenceProgram> programs) {
		this.programs = programs;
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
	
	// code by nitin
		@JsonProperty("catchUp")
	    public Boolean getCatchUp() {
	        return catchUp;
	    }
		public void setCatchUp(Boolean catchUp) {
	        this.catchUp = catchUp;
	    }
		
		
		 @JsonProperty("Downloadable")
		    public Boolean getDownloadable() {
		        return downloadable;
		 }
		 public void setDownloadable(Boolean downloadable) {
		        this.downloadable = downloadable;
		 }

		 @JsonProperty("STBEnabled")
		    public Boolean getSTBEnabled() {
		        return sTBEnabled;
		 }
		 public void setSTBEnabled(Boolean sTBEnabled) {
		        this.sTBEnabled = sTBEnabled;
		 }
	@JsonProperty("screenFormat")
	public String getScreenFormat() {
		return screenFormat;
	}

	public void setScreenFormat(String screenFormat) {
		this.screenFormat = screenFormat;
	}

	@JsonProperty("imageUrl")
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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
}
