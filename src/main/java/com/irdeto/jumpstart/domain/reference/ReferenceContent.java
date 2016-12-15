package com.irdeto.jumpstart.domain.reference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.irdeto.jumpstart.domain.config.Device;


@JsonInclude(Include.NON_EMPTY)
public abstract class ReferenceContent extends ReferenceEntity {
    private String contentType;
    private List<String> languages = new ArrayList<>();
	private Map<Device, ReferenceRendition> renditionMap = new HashMap<>();

    @JsonProperty("contentType")
    public String getContentType() {
        return contentType;
    }
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @JsonProperty("languages")
    public List<String> getLanguages() {
        return languages;
    }
    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    @JsonProperty("renditions")
	public Map<String, ReferenceRendition> getDeviceClassRenditionMap() {
    	Map<String, ReferenceRendition> deviceClassRenditionMap = new HashMap<>();
    	if (getRenditionMap() != null) {
	    	for (Entry<Device, ReferenceRendition> entry: getRenditionMap().entrySet()) {
	    		deviceClassRenditionMap.put(entry.getKey().getDeviceClass(), entry.getValue());
	    	}
    	}
    	return deviceClassRenditionMap;
    }

    @JsonIgnore
	public Map<Device, ReferenceRendition> getRenditionMap() {
		return renditionMap;
	}
	public void setRenditionMap(Map<Device, ReferenceRendition> renditionMap) {
		this.renditionMap = renditionMap;
	}
}
