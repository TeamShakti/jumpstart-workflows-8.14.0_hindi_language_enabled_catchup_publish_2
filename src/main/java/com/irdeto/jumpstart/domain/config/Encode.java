package com.irdeto.jumpstart.domain.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

public class Encode {
	private String encodeProfileId;
    private String name;
    private String liveUri;
    private String protectionType;

    @XmlAttribute
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@XmlAttribute
	public String getLiveUri() {
		return liveUri;
	}
	public void setLiveUri(String liveUri) {
		this.liveUri = liveUri;
	}
	@XmlTransient
	public String getEncodeProfileId() {
		return encodeProfileId;
	}
	public void setEncodeProfileId(String encodeProfileId) {
		this.encodeProfileId = encodeProfileId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((encodeProfileId == null) ? 0 : encodeProfileId.hashCode());
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
		Encode other = (Encode) obj;
		if (encodeProfileId == null) {
			if (other.encodeProfileId != null)
				return false;
		} else if (!encodeProfileId.equals(other.encodeProfileId))
			return false;
		return true;
	}
    @XmlAttribute
	public String getProtectionType() {
		return protectionType;
	}
	public void setProtectionType(String protectionType) {
		this.protectionType = protectionType;
	}
}
