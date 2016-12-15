package com.irdeto.jumpstart.domain.reference;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_EMPTY)
public class ReferenceVideoContent extends ReferenceContent {
    private String duration;
    private String entitlementId;
    private List<String> subtitleLanguages = new ArrayList<String>();
    private List<String> dubbedLanguages = new ArrayList<String>();
	private String screenFormat;
	private List<ReferenceSubtitle> subtitleContent = new ArrayList<>();

    @JsonProperty("duration")
    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }

    @JsonProperty("entitlementContentId")
    public String getEntitlementId() {
        return entitlementId;
    }
    public void setEntitlementId(String entitlementId) {
        this.entitlementId = entitlementId;
    }

    @JsonProperty("subtitleLanguages")
    public List<String> getSubtitleLanguages() {
        return subtitleLanguages;
    }
    public void setSubtitleLanguages(List<String> subtitleLanguages) {
        this.subtitleLanguages = subtitleLanguages;
    }

    @JsonProperty("dubbedLanguages")
    public List<String> getDubbedLanguages() {
        return dubbedLanguages;
    }
    public void setDubbedLanguages(List<String> dubbedLanguages) {
        this.dubbedLanguages = dubbedLanguages;
    }

	@JsonProperty("screenFormat")
	public String getScreenFormat() {
		return screenFormat;
	}
	public void setScreenFormat(String screenFormat) {
		this.screenFormat = screenFormat;
	}

	@JsonProperty("subtitleContent")
	public List<ReferenceSubtitle> getSubtitleContent() {
		return subtitleContent;
	}
	public void setSubtitleContent(List<ReferenceSubtitle> subtitleContent) {
		this.subtitleContent = subtitleContent;
	}
	
	@JsonIgnore
	public ReferenceVideoContent copy() {
		ReferenceVideoContent copy = new ReferenceVideoContent();
		copy.setContentType(getContentType());
		copy.getDubbedLanguages().addAll(getDubbedLanguages());
		copy.setDuration(getDuration());
		copy.setEntitlementId(getEntitlementId());
		copy.setId(getId());
		copy.getLanguages().addAll(getLanguages());
		copy.setProvider(getProvider());
		copy.getRenditionMap().putAll(getRenditionMap());
		copy.setScreenFormat(getScreenFormat());
		copy.getSubtitleContent().addAll(getSubtitleContent());
		copy.getSubtitleLanguages().addAll(getSubtitleLanguages());
		return copy;
	}
}
