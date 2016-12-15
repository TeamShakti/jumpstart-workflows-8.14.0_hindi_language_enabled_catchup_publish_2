package com.irdeto.jumpstart.domain.control.qsa;

import javax.xml.bind.annotation.XmlAttribute;

public class ControlQSARegionInfo {
	private String regionName;

	@XmlAttribute(name = "RegionName")
	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

}
