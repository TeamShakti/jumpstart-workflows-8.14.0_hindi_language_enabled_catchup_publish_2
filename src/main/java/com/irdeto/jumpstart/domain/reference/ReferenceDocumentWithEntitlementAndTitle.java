package com.irdeto.jumpstart.domain.reference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_EMPTY)
public abstract class ReferenceDocumentWithEntitlementAndTitle extends ReferenceDocumentWithEntitlement {
    private Map<String, String> titleSortName = new HashMap<>();
	private Map<String, String> titleBrief = new HashMap<>();
	private Map<String, String> titleMedium = new HashMap<>();
	private Map<String, String> titleLong = new HashMap<>();
	private Map<String, String> summaryShort = new HashMap<>();
	private Map<String, String> summaryMedium = new HashMap<>();
	private Map<String, String> summaryLong = new HashMap<>();
	private List<String> countryOfOrigin = new ArrayList<>();
	private String showType;
	private String keywords;

	@JsonProperty("titleSortName")
	public Map<String, String> getTitleSortName() {
		return titleSortName;
	}
	public void setTitleSortName(Map<String, String> titleSortName) {
		this.titleSortName = titleSortName;
	}
	@JsonProperty("titleBrief")
	public Map<String, String> getTitleBrief() {
		return titleBrief;
	}
	public void setTitleBrief(Map<String, String> titleBrief) {
		this.titleBrief = titleBrief;
	}
	@JsonProperty("titleMedium")
	public Map<String, String> getTitleMedium() {
		return titleMedium;
	}
	public void setTitleMedium(Map<String, String> titleMedium) {
		this.titleMedium = titleMedium;
	}

	@JsonProperty("titleLong")
	public Map<String, String> getTitleLong() {
		return titleLong;
	}
	public void setTitleLong(Map<String, String> titleLong) {
		this.titleLong = titleLong;
	}

	@JsonProperty("summaryShort")
	public Map<String, String> getSummaryShort() {
		return summaryShort;
	}
	public void setSummaryShort(Map<String, String> summaryShort) {
		this.summaryShort = summaryShort;
	}

	@JsonProperty("summaryMedium")
	public Map<String, String> getSummaryMedium() {
		return summaryMedium;
	}
	public void setSummaryMedium(Map<String, String> summaryMedium) {
		this.summaryMedium = summaryMedium;
	}

	@JsonProperty("summaryLong")
	public Map<String, String> getSummaryLong() {
		return summaryLong;
	}
	public void setSummaryLong(Map<String, String> summaryLong) {
		this.summaryLong = summaryLong;
	}

	@JsonProperty("countryOfOrigin")
	public List<String> getCountryOfOrigin() {
		return countryOfOrigin;
	}
	public void setCountryOfOrigin(List<String> countryOfOrigin) {
		this.countryOfOrigin = countryOfOrigin;
	}

	@JsonProperty("showType")
	public String getShowType() {
		return showType;
	}
	public void setShowType(String showType) {
		this.showType = showType;
	}

	@JsonProperty("keywords")
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
}
