package com.irdeto.jumpstart.domain.reference;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_EMPTY)
public class ReferenceRendition {
	private String consumerUrl;
	private Map<String, String> licenseAcquisitionUrl = new HashMap<>();
	
	private String resolution;
	private String frameRate;
	private String codec;
	private Integer bitrate;
	private Boolean isHDContent;

	@JsonProperty("resolution")
	public String getResolution() {
		return resolution;
	}
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	@JsonProperty("frameRate")
	public String getFrameRate() {
		return frameRate;
	}
	public void setFrameRate(String frameRate) {
		this.frameRate = frameRate;
	}

	@JsonProperty("codec")
	public String getCodec() {
		return codec;
	}
	public void setCodec(String codec) {
		this.codec = codec;
	}

	@JsonProperty("bitrate")
	public Integer getBitrate() {
		return bitrate;
	}
	public void setBitrate(Integer bitrate) {
		this.bitrate = bitrate;
	}

	@JsonProperty("isHDContent")
	public Boolean getIsHDContent() {
		return isHDContent;
	}
	public void setIsHDContent(Boolean isHDContent) {
		this.isHDContent = isHDContent;
	}

	
	public String getConsumerUrl() {
		return consumerUrl;
	}
	public void setConsumerUrl(String consumerUrl) {
		this.consumerUrl = consumerUrl;
	}
	public Map<String, String> getLicenseAcquisitionUrl() {
		return licenseAcquisitionUrl;
	}
	public void setLicenseAcquisitionUrl(Map<String, String> licenseAcquisitionUrl) {
		this.licenseAcquisitionUrl = licenseAcquisitionUrl;
	}
}
