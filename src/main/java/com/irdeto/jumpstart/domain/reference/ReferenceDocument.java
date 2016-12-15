package com.irdeto.jumpstart.domain.reference;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_EMPTY)
public abstract class ReferenceDocument extends ReferenceEntity {
	private String indexId;
    private DateTime startDateTime;
    private DateTime endDateTime;
    private DateTime lastPublishDateTime;

	@JsonIgnore
	public String getIndexId() {
		return indexId;
	}

	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}

	@JsonIgnore
	public abstract String getType();

    @JsonProperty("startDateTime")
    public DateTime getStartDateTime() {
        return startDateTime;
    }
    public void setStartDateTime(DateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    @JsonProperty("endDateTime")
    public DateTime getEndDateTime() {
        return endDateTime;
    }
    public void setEndDateTime(DateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    @JsonIgnore
    public DateTime getLastPublishDateTime() {
        return lastPublishDateTime;
    }
    public void setLastPublishDateTime(DateTime lastPublishDateTime) {
        this.lastPublishDateTime = lastPublishDateTime;
    }
}
