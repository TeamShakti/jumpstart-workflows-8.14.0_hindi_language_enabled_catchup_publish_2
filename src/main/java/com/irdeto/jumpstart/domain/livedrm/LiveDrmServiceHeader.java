package com.irdeto.jumpstart.domain.livedrm;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType (namespace = "http://man.entriq.net/livedrmservice/")
public class LiveDrmServiceHeader {
	private String username;
	private String password;

	@XmlAttribute(name = "m_sUsername", required = true)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	@XmlAttribute(name = "m_sPassword", required = true)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
