package com.irdeto.jumpstart.domain.control.qsa;

import javax.xml.bind.annotation.XmlAttribute;

public class ControlQSASubcontent {
	private String type;
	private String mediaType;
	private String bitRate;
	private String fileSize;
	private String mimeType;
	private String length;
	private String modified;
	private String registered;
	private String recycle;
	private String url;
	
	@XmlAttribute(name = "Type")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@XmlAttribute(name = "MediaType")
	public String getMediaType() {
		return mediaType;
	}
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	@XmlAttribute(name = "BitRate")
	public String getBitRate() {
		return bitRate;
	}
	public void setBitRate(String bitRate) {
		this.bitRate = bitRate;
	}
	@XmlAttribute(name = "FileSize")
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	@XmlAttribute(name = "MimeType")
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	@XmlAttribute(name = "Length")
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	@XmlAttribute(name = "Modified")
	public String getModified() {
		return modified;
	}
	public void setModified(String modified) {
		this.modified = modified;
	}
	@XmlAttribute(name = "Registered")
	public String getRegistered() {
		return registered;
	}
	public void setRegistered(String registered) {
		this.registered = registered;
	}
	@XmlAttribute(name = "Recycle")
	public String getRecycle() {
		return recycle;
	}
	public void setRecycle(String recycle) {
		this.recycle = recycle;
	}
	@XmlAttribute(name = "Url")
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
