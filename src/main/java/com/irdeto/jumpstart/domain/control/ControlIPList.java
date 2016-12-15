package com.irdeto.jumpstart.domain.control;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="IPList")
public class ControlIPList {
	private String crmId;
	private List<ControlIPAddress> ipAddressList;
	
	@XmlAttribute(name="CrmId")
	public String getCrmId() {
		return crmId;
	}
	
	public void setCrmId(String crmId) {
		this.crmId = crmId;
	}

	@XmlElement(name="IPAddress")
	public List<ControlIPAddress> getIpAddressList() {
		return ipAddressList;
	}
	
	public void setIpAddressList(List<ControlIPAddress> ipAddressList) {
		this.ipAddressList = ipAddressList;
	}
}
