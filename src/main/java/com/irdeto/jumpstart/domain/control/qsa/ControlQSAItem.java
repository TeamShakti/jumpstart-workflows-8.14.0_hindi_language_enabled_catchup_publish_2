package com.irdeto.jumpstart.domain.control.qsa;

import javax.xml.bind.annotation.XmlAttribute;

public class ControlQSAItem {
	private String accountId;
	private String itemId;
	private String type;
	private String channelId;
	private String mediaId;
	private String policyId;
	private String recycle;
	private String live;
	private String sourceId;
	private String name;
	private String description;
	private String pgRate;
	private String packageId;
	
	@XmlAttribute(name = "AccountId")
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	@XmlAttribute(name = "ItemId")
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	@XmlAttribute(name= "Type")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@XmlAttribute(name= "ChannelId")
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	@XmlAttribute(name= "MediaId")
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	@XmlAttribute(name= "PolicyId")
	public String getPolicyId() {
		return policyId;
	}
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}
	@XmlAttribute(name= "Recycle")
	public String getRecycle() {
		return recycle;
	}
	public void setRecycle(String recycle) {
		this.recycle = recycle;
	}
	@XmlAttribute(name= "Live")
	public String getLive() {
		return live;
	}
	public void setLive(String live) {
		this.live = live;
	}
	
	@XmlAttribute(name= "SourceId")
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	@XmlAttribute(name= "Name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@XmlAttribute(name= "Description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@XmlAttribute(name= "PGRate")
	public String getPgRate() {
		return pgRate;
	}
	public void setPgRate(String pgRate) {
		this.pgRate = pgRate;
	}
	@XmlAttribute(name= "PackageId")
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
}
