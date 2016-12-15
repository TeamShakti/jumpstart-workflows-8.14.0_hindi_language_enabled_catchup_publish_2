package com.irdeto.jumpstart.domain.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;


public class Device {
	private String deviceProfileId;
    private String deviceClass;
    private String name;
    private Boolean enabled;
    private String packagingType;
    private List<String> encodeProfileNameList = new ArrayList<>();
    private List<String> transcodeProfileNameList = new ArrayList<>();
    private List<String> protectProfileNameList = new ArrayList<>();
    private List<Encode> encodeList = new ArrayList<>();
    private List<Transcode> transcodeList = new ArrayList<>();
    private List<Protect> protectList = new ArrayList<>();
    
    @XmlAttribute
	public String getDeviceClass() {
		return deviceClass;
	}
	public void setDeviceClass(String deviceClass) {
		this.deviceClass = deviceClass;
	}
    @XmlAttribute
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@XmlAttribute
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	@XmlElement(name="EncodeProfileName")
	public List<String> getEncodeProfileNameList() {
		return encodeProfileNameList;
	}
	public void setEncodeProfileNameList(List<String> encodeProfileNameList) {
		this.encodeProfileNameList = encodeProfileNameList;
	}
	@XmlElement(name="TranscodeProfileName")
	public List<String> getTranscodeProfileNameList() {
		return transcodeProfileNameList;
	}
	public void setTranscodeProfileNameList(List<String> transcodeProfileNameList) {
		this.transcodeProfileNameList = transcodeProfileNameList;
	}
	@XmlElement(name="ProtectProfileName")
	public List<String> getProtectProfileNameList() {
		return protectProfileNameList;
	}
	public void setProtectProfileNameList(List<String> protectProfileNameList) {
		this.protectProfileNameList = protectProfileNameList;
	}
	
	@XmlTransient
	public String getDeviceProfileId() {
		return deviceProfileId;
	}
	public void setDeviceProfileId(String deviceProfileId) {
		this.deviceProfileId = deviceProfileId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((deviceProfileId == null) ? 0 : deviceProfileId.hashCode());
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
		Device other = (Device) obj;
		if (deviceProfileId == null) {
			if (other.deviceProfileId != null)
				return false;
		} else if (!deviceProfileId.equals(other.deviceProfileId))
			return false;
		return true;
	}
	
	@XmlTransient
	public List<Encode> getEncodeList() {
		return encodeList;
	}
	public void setEncodeList(List<Encode> encodeList) {
		this.encodeList = encodeList;
	}
	@XmlTransient
	public List<Transcode> getTranscodeList() {
		return transcodeList;
	}
	public void setTranscodeList(List<Transcode> transcodeList) {
		this.transcodeList = transcodeList;
	}
	@XmlTransient
	public List<Protect> getProtectList() {
		return protectList;
	}
	public void setProtectList(List<Protect> protectList) {
		this.protectList = protectList;
	}
	@XmlAttribute
	public String getPackagingType() {
		return packagingType;
	}
	public void setPackagingType(String packagingType) {
		this.packagingType = packagingType;
	}
}
