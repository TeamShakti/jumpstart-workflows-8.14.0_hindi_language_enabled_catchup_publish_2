package com.irdeto.jumpstart.domain.reference;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_EMPTY)
public class ReferenceGenre extends ReferenceDocument {
    private Map<String, String> title = new HashMap<>();
    private String genrePath;
    private ReferenceGenre parentGenre;
    private Boolean isEnabled;

    @JsonProperty("title")
    public Map<String, String> getTitle() {
        return title;
    }
    public void setTitle(Map<String, String> title) {
        this.title = title;
    }

    @JsonProperty("genrePath")
    public String getGenrePath() {
        return genrePath;
    }
    public void setGenrePath(String genrePath) {
        this.genrePath = genrePath;
    }

    @JsonProperty("parentGenre")
    public ReferenceGenre getParentGenre() {
        return parentGenre;
    }
    @JsonProperty("isEnabled")
    public Boolean getIsEnabled() {
        return isEnabled;
    }
    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
    
    public void setParentGenre(ReferenceGenre parentGenre) {
        this.parentGenre = parentGenre;
    }
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "Genre";
	}
}
