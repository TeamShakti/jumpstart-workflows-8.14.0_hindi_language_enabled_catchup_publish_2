package com.irdeto.jumpstart.domain.control.qsa;

import javax.xml.bind.annotation.XmlAttribute;

public class ControlQSAAvailability {
	private String country;
	private String affiliate;
	private String deviceGroupId;
	private String releaseDate;
	private String releaseEnd;
	private String blocked;
	
	@XmlAttribute(name = "Country")
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	@XmlAttribute(name = "Affiliate")
	public String getAffiliate() {
		return affiliate;
	}
	public void setAffiliate(String affiliate) {
		this.affiliate = affiliate;
	}
	@XmlAttribute(name = "DeviceGroupId")
	public String getDeviceGroupId() {
		return deviceGroupId;
	}
	public void setDeviceGroupId(String deviceGroupId) {
		this.deviceGroupId = deviceGroupId;
	}
	@XmlAttribute(name = "ReleaseDate")
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	@XmlAttribute(name = "ReleaseEnd")
	public String getReleaseEnd() {
		return releaseEnd;
	}
	public void setReleaseEnd(String releaseEnd) {
		this.releaseEnd = releaseEnd;
	}
	@XmlAttribute(name = "Blocked")
	public String getBlocked() {
		return blocked;
	}
	public void setBlocked(String blocked) {
		this.blocked = blocked;
	}
}
