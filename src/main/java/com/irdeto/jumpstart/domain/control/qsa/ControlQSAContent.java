package com.irdeto.jumpstart.domain.control.qsa;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class ControlQSAContent {
	private ControlQSAAvailability availability;
	private List<ControlQSASubcontent> subcontentList = new ArrayList<>();
	private String accountId;
	private String contentId;
	private String mediaId;
	private String policyId;
	private String channelId;
	private String recycle;
	private String live;
	private String onAir;

	@XmlElement(name = "Availability")
	public ControlQSAAvailability getAvailability() {
		return availability;
	}

	public void setAvailability(ControlQSAAvailability availability) {
		this.availability = availability;
	}

	@XmlElement(name = "SubContent")
	public List<ControlQSASubcontent> getSubcontentList() {
		return subcontentList;
	}

	public void setSubcontentList(List<ControlQSASubcontent> subcontentList) {
		this.subcontentList = subcontentList;
	}

	@XmlAttribute(name = "AccountId")
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	@XmlAttribute(name = "ContentId")
	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	@XmlAttribute(name = "MediaId")
	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	@XmlAttribute(name = "PolicyId")
	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	@XmlAttribute(name = "ChannelId")
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@XmlAttribute(name = "Recycle")
	public String getRecycle() {
		return recycle;
	}

	public void setRecycle(String recycle) {
		this.recycle = recycle;
	}

	@XmlAttribute(name = "Live")
	public String getLive() {
		return live;
	}

	public void setLive(String live) {
		this.live = live;
	}

	@XmlAttribute(name = "OnAir")
	public String getOnAir() {
		return onAir;
	}

	public void setOnAir(String onAir) {
		this.onAir = onAir;
	}
}
