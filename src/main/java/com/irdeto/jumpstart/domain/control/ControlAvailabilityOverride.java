package com.irdeto.jumpstart.domain.control;

import javax.xml.bind.annotation.XmlAttribute;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ControlAvailabilityOverride {
	private String country;
	private String releaseDate;
	private String releaseEnd;
	private String affiliateId;
	private Boolean blocked;
	
	@XmlAttribute(name="Country", required=true)
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@XmlAttribute(name="ReleaseDate", required=false)
	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	@XmlAttribute(name="ReleaseEnd", required=false)
	public String getReleaseEnd() {
		return releaseEnd;
	}
	
	public void setReleaseEnd(String releaseEnd) {
		this.releaseEnd = releaseEnd;
	}
	
	@XmlAttribute(name="AffiliateId", required=false)
	public String getAffiliateId() {
		return affiliateId;
	}
	
	public void setAffiliateId(String affiliateId) {
		this.affiliateId = affiliateId;
	}
	
	@XmlAttribute(name="Blocked", required=false)
	public Boolean getBlocked() {
		return blocked;
	}
	
	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}
	
	@Override
	public String toString() {
        return ToStringBuilder.reflectionToString(this);
	}
}
