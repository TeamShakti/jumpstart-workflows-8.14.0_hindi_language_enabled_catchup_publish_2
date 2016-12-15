package com.irdeto.jumpstart.domain.reference;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_EMPTY)
public abstract class ReferenceEntity {
	private String id;
	private String provider;

	@JsonProperty("id")
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty("provider")
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
}
