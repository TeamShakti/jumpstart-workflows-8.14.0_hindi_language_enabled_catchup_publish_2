package com.irdeto.jumpstart.domain.control.qsa;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class ControlQSASession {
	private ControlQSARegionInfo regionInfo;
	private String sessionId;
	private String ticket;
	private String agentHost;
	private String agentId;
	private String crmId;
	private String userId;
	private String userIp;
	private String pgRate;
	private String country;
	private String ipCountryConfidence;
	private String networkId;
	private String deviceId;
	private String affiliateId;
	private String createTime;
	private String expTime;
	private String noStreams;
	private String maxStreams;
	private String leadId;

	@XmlElement(name = "RegionInfo")
	public ControlQSARegionInfo getRegionInfo() {
		return regionInfo;
	}

	public void setRegionInfo(ControlQSARegionInfo regionInfo) {
		this.regionInfo = regionInfo;
	}

	@XmlAttribute(name = "SessionId")
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	@XmlAttribute(name = "Ticket")
	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	@XmlAttribute(name = "AgentHost")
	public String getAgentHost() {
		return agentHost;
	}

	public void setAgentHost(String agentHost) {
		this.agentHost = agentHost;
	}

	@XmlAttribute(name = "AgentId")
	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	@XmlAttribute(name = "CrmId")
	public String getCrmId() {
		return crmId;
	}

	public void setCrmId(String crmId) {
		this.crmId = crmId;
	}

	@XmlAttribute(name = "UserId")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@XmlAttribute(name = "UserIp")
	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	@XmlAttribute(name = "PGRate")
	public String getPgRate() {
		return pgRate;
	}

	public void setPgRate(String pgRate) {
		this.pgRate = pgRate;
	}

	@XmlAttribute(name = "Country")
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@XmlAttribute(name = "IpCountryConfidence")
	public String getIpCountryConfidence() {
		return ipCountryConfidence;
	}

	public void setIpCountryConfidence(String ipCountryConfidence) {
		this.ipCountryConfidence = ipCountryConfidence;
	}

	@XmlAttribute(name = "NetworkId")
	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	@XmlAttribute(name = "DeviceId")
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	@XmlAttribute(name = "AffiliateId")
	public String getAffiliateId() {
		return affiliateId;
	}

	public void setAffiliateId(String affiliateId) {
		this.affiliateId = affiliateId;
	}

	@XmlAttribute(name = "CreateTime")
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@XmlAttribute(name = "ExpTime")
	public String getExpTime() {
		return expTime;
	}

	public void setExpTime(String expTime) {
		this.expTime = expTime;
	}

	@XmlAttribute(name = "NoStreams")
	public String getNoStreams() {
		return noStreams;
	}

	public void setNoStreams(String noStreams) {
		this.noStreams = noStreams;
	}

	@XmlAttribute(name = "MaxStreams")
	public String getMaxStreams() {
		return maxStreams;
	}

	public void setMaxStreams(String maxStreams) {
		this.maxStreams = maxStreams;
	}

	@XmlAttribute(name = "LeadId")
	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}
}
