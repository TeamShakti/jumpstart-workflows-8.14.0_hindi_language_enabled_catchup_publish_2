package com.irdeto.jumpstart.domain.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Protect {
	private String protectProfileId;
    private String name;
    private String protectionType;
    private Boolean required;

    @XmlAttribute
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    @XmlAttribute
    public String getProtectionType() {
		return protectionType;
	}
	public void setProtectionType(String protectionType) {
		this.protectionType = protectionType;
	}
	@XmlTransient
	public String getProtectProfileId() {
		return protectProfileId;
	}
	public void setProtectProfileId(String protectProfileId) {
		this.protectProfileId = protectProfileId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((protectProfileId == null) ? 0 : protectProfileId.hashCode());
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
		Protect other = (Protect) obj;
		if (protectProfileId == null) {
			if (other.protectProfileId != null)
				return false;
		} else if (!protectProfileId.equals(other.protectProfileId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
        return ToStringBuilder.reflectionToString(this);
	}
    @XmlAttribute
	public Boolean getRequired() {
		return required;
	}
	public void setRequired(Boolean required) {
		this.required = required;
	}
}
