package com.irdeto.jumpstart.domain.control;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ControlMedia {
	private List<ControlContent> contentList = new ArrayList<>();
	private String accountId;
	private String mediaId;
	private String name;
	private String description;
	private String longDescription;
	private Integer pgRate;
	private String artist;
	private String url;
	private String search;
	private String sourceId;
	
	@XmlElement(name="Content", required=false)
	public List<ControlContent> getContentList() {
		return contentList;
	}

	public void setContentList(List<ControlContent> contentList) {
		this.contentList = contentList;
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

	@XmlAttribute(name="Name", required=false)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlAttribute(name="Description", required=false)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlAttribute(name="LongDescription", required=false)
	public String getLongDescription() {
		return longDescription;
	}
	
	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}
	
	@XmlAttribute(name="PGRate", required=false)
	public Integer getPgRate() {
		return pgRate;
	}

	public void setPgRate(Integer pgRate) {
		this.pgRate = pgRate;
	}

	@XmlAttribute(name="Artist", required=false)
	public String getArtist() {
		return artist;
	}
	
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	@XmlAttribute(name="Url", required=false)
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	@XmlAttribute(name="Search", required=false)
	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
	
	@XmlAttribute(name="SourceId", required=false)
	public String getSourceId() {
		return sourceId;
	}
	
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	
	@Override
	public String toString() {
        return ToStringBuilder.reflectionToString(this);
	}
}
