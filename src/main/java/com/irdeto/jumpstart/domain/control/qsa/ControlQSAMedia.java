package com.irdeto.jumpstart.domain.control.qsa;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class ControlQSAMedia {
	private ControlQSAContent content;
	private String accountId;
	private String mediaId;
	private String policyId;
	private String recycle;
	private String sourceId;
	private String name;
	private String description;
	private String pgRate;
	private String artist;
	private String category;
	private String subCategory;
	private String url;
	private String search;
	private String user1;
	private String user2;

	@XmlElement(name = "Content")
	public ControlQSAContent getContent() {
		return content;
	}

	public void setContent(ControlQSAContent content) {
		this.content = content;
	}

	@XmlAttribute(name = "AccountId")
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
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

	@XmlAttribute(name = "Recycle")
	public String getRecycle() {
		return recycle;
	}

	public void setRecycle(String recycle) {
		this.recycle = recycle;
	}

	@XmlAttribute(name = "SourceId")
	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	@XmlAttribute(name = "Name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlAttribute(name = "Description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlAttribute(name = "PGRate")
	public String getPgRate() {
		return pgRate;
	}

	public void setPgRate(String pgRate) {
		this.pgRate = pgRate;
	}

	@XmlAttribute(name = "Artist")
	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	@XmlAttribute(name = "Category")
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@XmlAttribute(name = "SubCategory")
	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	@XmlAttribute(name = "Url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@XmlAttribute(name = "Search")
	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	@XmlAttribute(name = "User1")
	public String getUser1() {
		return user1;
	}

	public void setUser1(String user1) {
		this.user1 = user1;
	}

	@XmlAttribute(name = "User2")
	public String getUser2() {
		return user2;
	}

	public void setUser2(String user2) {
		this.user2 = user2;
	}
}
