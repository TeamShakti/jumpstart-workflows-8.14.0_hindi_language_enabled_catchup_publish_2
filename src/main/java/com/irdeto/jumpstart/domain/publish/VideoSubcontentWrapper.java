package com.irdeto.jumpstart.domain.publish;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.irdeto.jumpstart.domain.config.Device;
import com.irdeto.jumpstart.domain.config.Protect;
import com.irdeto.jumpstart.domain.config.TermMap;
import com.irdeto.jumpstart.domain.config.Transcode;

public class VideoSubcontentWrapper {
	private String sourceVideoSubcontentId;
	private String protectedVideoSubcontentId;
	private List<TermWrapper> termWrapperList = new ArrayList<>();
	private List<TermMap> termMapList = new ArrayList<>();
	private List<Device> deviceList = new ArrayList<>();
	private List<Transcode> transcodeList = new ArrayList<>();
	private List<Protect> protectList = new ArrayList<>();
	
	public VideoSubcontentWrapper() {
		super();
	}
	
	public VideoSubcontentWrapper(String sourceVideoSubcontentId, String protectedVideoSubcontentId) {
		super();
		setSourceVideoSubcontentId(sourceVideoSubcontentId);
		setProtectedVideoSubcontentId(protectedVideoSubcontentId);
	}
	
	public String getProtectedVideoSubcontentId() {
		return protectedVideoSubcontentId;
	}
	public void setProtectedVideoSubcontentId(String protectedVideoSubcontentId) {
		this.protectedVideoSubcontentId = protectedVideoSubcontentId;
	}
	public List<TermWrapper> getTermWrapperList() {
		return termWrapperList;
	}
	public void setTermWrapperList(List<TermWrapper> termWrapperList) {
		this.termWrapperList = termWrapperList;
	}
	public List<TermMap> getTermMapList() {
		return termMapList;
	}
	public void setTermMapList(List<TermMap> termMapList) {
		this.termMapList = termMapList;
	}
	public List<Device> getDeviceList() {
		return deviceList;
	}
	public void setDeviceList(List<Device> deviceList) {
		this.deviceList = deviceList;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((protectedVideoSubcontentId == null) ? 0
						: protectedVideoSubcontentId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VideoSubcontentWrapper other = (VideoSubcontentWrapper) obj;
		if (protectedVideoSubcontentId == null) {
			if (other.protectedVideoSubcontentId != null)
				return false;
		} else if (!protectedVideoSubcontentId
				.equals(other.protectedVideoSubcontentId))
			return false;
		return true;
	}


	@Override
	public String toString() {
        return ToStringBuilder.reflectionToString(this);
	}

	public String getSourceVideoSubcontentId() {
		return sourceVideoSubcontentId;
	}

	public void setSourceVideoSubcontentId(String sourceVideoSubcontentId) {
		this.sourceVideoSubcontentId = sourceVideoSubcontentId;
	}

	public List<Transcode> getTranscodeList() {
		return transcodeList;
	}

	public void setTranscodeList(List<Transcode> transcodeList) {
		this.transcodeList = transcodeList;
	}

	public List<Protect> getProtectList() {
		return protectList;
	}

	public void setProtectList(List<Protect> protectList) {
		this.protectList = protectList;
	}

}
