package com.irdeto.jumpstart.domain.reference;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_EMPTY)
public class ReferenceContributor extends ReferenceEntity {
    private String contribution;
    private Map<String, String> sortableName = new HashMap<>();
	private Map<String, String> firstName = new HashMap<>();
	private Map<String, String> lastName = new HashMap<>();
	private Map<String, String> fullName = new HashMap<>();

    @JsonProperty("contribution")
    public String getContribution() {
        return contribution;
    }
    public void setContribution(String contribution) {
        this.contribution = contribution;
    }

    @JsonProperty("sortableName")
    public Map<String, String> getSortableName() {
        return sortableName;
    }
    public void setSortableName(Map<String, String> sortableName) {
        this.sortableName = sortableName;
    }

	@JsonProperty("firstName")
    public Map<String, String> getFirstName() {
		return firstName;
	}
	public void setFirstName(Map<String, String> firstName) {
		this.firstName = firstName;
	}

	@JsonProperty("lastName")
	public Map<String, String> getLastName() {
		return lastName;
	}
	public void setLastName(Map<String, String> lastName) {
		this.lastName = lastName;
	}

	@JsonProperty("fullName")
	public Map<String, String> getFullName() {
		return fullName;
	}
	public void setFullName(Map<String, String> fullName) {
		this.fullName = fullName;
	}
}
