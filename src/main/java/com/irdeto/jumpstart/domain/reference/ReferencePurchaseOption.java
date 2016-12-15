package com.irdeto.jumpstart.domain.reference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_EMPTY)
public class ReferencePurchaseOption {
    private DateTime startDateTime;
    private DateTime endDateTime;
    private Integer policyGroupId;
    private Integer policyId;
    private String policyType;
    private ReferenceOffer offer;
    private String offeredEntityType;
    private String offeredEntityId;
    private Map<String, Double> price = new HashMap<>();
    private List<ReferenceVideoContent> videoContentList = new ArrayList<>();
    private String country;
	private String contractName;
	private List<String> contentTypeList = new ArrayList<>();

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

    @JsonProperty("policyGroupId")
    public Integer getPolicyGroupId() {
        return policyGroupId;
    }
    public void setPolicyGroupId(Integer policyGroupId) {
        this.policyGroupId = policyGroupId;
    }

    @JsonProperty("policyId")
    public Integer getPolicyId() {
        return policyId;
    }
    public void setPolicyId(Integer policyId) {
        this.policyId = policyId;
    }

    @JsonProperty("policyType")
    public String getPolicyType() {
        return policyType;
    }
    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    @JsonProperty("offer")
    public ReferenceOffer getOffer() {
        return offer;
    }
    public void setOffer(ReferenceOffer offer) {
        this.offer = offer;
    }

    @JsonProperty("offeredEntityType")
    public String getOfferedEntityType() {
        return offeredEntityType;
    }
    public void setOfferedEntityType(String offeredEntityType) {
        this.offeredEntityType = offeredEntityType;
    }

    @JsonProperty("offeredEntityId")
    public String getOfferedEntityId() {
        return offeredEntityId;
    }
    public void setOfferedEntityId(String offeredEntityId) {
        this.offeredEntityId = offeredEntityId;
    }

    @JsonProperty("price")
    public Map<String, Double> getPrice() {
        return price;
    }
    public void setPrice(Map<String, Double> price) {
        this.price = price;
    }

    @JsonProperty("videoContents")
    public List<ReferenceVideoContent> getVideoContents() {
        return videoContentList;
    }
    public void setVideoContents(List<ReferenceVideoContent> videoContentList) {
        this.videoContentList = videoContentList;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

	@JsonProperty("contractName")
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	@JsonProperty("contentTypes")
	public List<String> getContentTypeList() {
		return contentTypeList;
	}
	public void setContentTypeList(List<String> contentTypeList) {
		this.contentTypeList = contentTypeList;
	}
}
