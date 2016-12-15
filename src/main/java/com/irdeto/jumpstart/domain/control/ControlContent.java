package com.irdeto.jumpstart.domain.control;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ControlContent {
	private List<ControlSubContent> subContentList = new ArrayList<>();
	private List<ControlPriceOverride> priceOverrideList = new ArrayList<>();
	private List<ControlAvailabilityOverride> availabilityOverrideList = new ArrayList<>();
	private String accountId;
	private String mediaId;
	private String contentId;
	private String policyId;
	private Boolean live;
	private String onAir;
	private String type;
	
	@XmlElement(name="SubContent", required=false)
	public List<ControlSubContent> getSubContentList() {
		return subContentList;
	}

	public void setSubContentList(List<ControlSubContent> subContentList) {
		this.subContentList = subContentList;
	}

	@XmlElement(name="PriceOverride", required=false)
	public List<ControlPriceOverride> getPriceOverrideList() {
		return priceOverrideList;
	}
	
	public void setPriceOverrideList(List<ControlPriceOverride> priceOverrideList) {
		this.priceOverrideList = priceOverrideList;
	}

	@XmlElement(name="AvailabilityOverride", required=false)
	public List<ControlAvailabilityOverride> getAvailabilityOverrideList() {
		return availabilityOverrideList;
	}
	
	public void setAvailabilityOverrideList(
			List<ControlAvailabilityOverride> availabilityOverrideList) {
		this.availabilityOverrideList = availabilityOverrideList;
	}
	
	@XmlAttribute(name="AccountId", required=true)
	public String getAccountId() {
		return accountId;
	}
	
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	@XmlAttribute(name="MediaId", required=true)
	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
	@XmlAttribute(name="ContentId", required=true)
	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	@XmlAttribute(name="PolicyId", required=false)
	public String getPolicyId() {
		return policyId;
	}
	
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	@XmlAttribute(name="Live", required=false)
	public Boolean getLive() {
		return live;
	}
	
	public void setLive(Boolean live) {
		this.live = live;
	}
	
	@XmlAttribute(name="OnAir", required=false)
	public String getOnAir() {
		return onAir;
	}
	
	public void setOnAir(String onAir) {
		this.onAir = onAir;
	}
	
	@XmlAttribute(name="Type", required=false)
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
        return ToStringBuilder.reflectionToString(this);
	}
}
