package com.irdeto.jumpstart.domain.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Transcode {
	private String transcodeProfileId;
    private String name;
    private String transcoderProfile;
    private String transcoderUri;
    private String transcodedFilePattern;
    private Integer transcodedFileCount;
    private String transcoderWorkflow;
    private List<String> protectProfileNameList = new ArrayList<>();
    private List<Protect> protectList = new ArrayList<>();

    @XmlAttribute
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    @XmlAttribute
	public String getTranscoderProfile() {
		return transcoderProfile;
	}
	public void setTranscoderProfile(String transcoderProfile) {
		this.transcoderProfile = transcoderProfile;
	}
	@XmlAttribute
	public String getTranscoderUri() {
		return transcoderUri;
	}
	public void setTranscoderUri(String transcoderUri) {
		this.transcoderUri = transcoderUri;
	}
	@XmlAttribute
	public String getTranscodedFilePattern() {
		return transcodedFilePattern;
	}
	public void setTranscodedFilePattern(String transcodedFilePattern) {
		this.transcodedFilePattern = transcodedFilePattern;
	}
	@XmlAttribute
    public Integer getTranscodedFileCount() {
		return transcodedFileCount;
	}
	public void setTranscodedFileCount(Integer transcodedFileCount) {
		this.transcodedFileCount = transcodedFileCount;
	}
	@XmlAttribute
	public String getTranscoderWorkflow() {
		return transcoderWorkflow;
	}
	public void setTranscoderWorkflow(String transcoderWorkflow) {
		this.transcoderWorkflow = transcoderWorkflow;
	}
	@XmlElement(name="ProtectProfileName")
	public List<String> getProtectProfileNameList() {
		return protectProfileNameList;
	}
	public void setProtectProfileName(List<String> protectProfileNameList) {
		this.protectProfileNameList = protectProfileNameList;
	}
	@XmlTransient
	public String getTranscodeProfileId() {
		return transcodeProfileId;
	}
	public void setTranscodeProfileId(String transcodeProfileId) {
		this.transcodeProfileId = transcodeProfileId;
	}
	@XmlTransient
	public List<Protect> getProtectList() {
		return protectList;
	}
	public void setProtectList(List<Protect> protectList) {
		this.protectList = protectList;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((transcodeProfileId == null) ? 0 : transcodeProfileId
						.hashCode());
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
		Transcode other = (Transcode) obj;
		if (transcodeProfileId == null) {
			if (other.transcodeProfileId != null)
				return false;
		} else if (!transcodeProfileId.equals(other.transcodeProfileId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
        return ToStringBuilder.reflectionToString(this);
	}

}
