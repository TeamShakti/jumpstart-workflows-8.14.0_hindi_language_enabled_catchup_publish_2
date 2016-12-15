package com.irdeto.jumpstart.domain.control;

import javax.xml.bind.annotation.XmlAttribute;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ControlSubContent {
	private String accountId;
	private String mediaId;
	private String contentId;
	private String type;
	private String length;
	private Long fileSize;
	private String url;
	private String mimeType;
	private String mediaType;
	private String bitRate;
	
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
	
	@XmlAttribute(name="Type", required=true)
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	@XmlAttribute(name="Length", required=false)
	public String getLength() {
		return length;
	}
	
	public void setLength(String length) {
		this.length = length;
	}
	
	@XmlAttribute(name="FileSize", required=false)
	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	
	@XmlAttribute(name="Url", required=false)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@XmlAttribute(name="MimeType", required=false)
	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	
	@XmlAttribute(name="MediaType", required=false)
	public String getMediaType() {
		return mediaType;
	}
	
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	
	@XmlAttribute(name="BitRate", required=false)
	public String getBitRate() {
		return bitRate;
	}
	
	public void setBitRate(String bitRate) {
		this.bitRate = bitRate;
	}
	
	@Override
	public String toString() {
        return ToStringBuilder.reflectionToString(this);
	}
}
