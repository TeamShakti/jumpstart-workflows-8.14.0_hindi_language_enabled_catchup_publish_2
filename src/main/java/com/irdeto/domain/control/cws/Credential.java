package com.irdeto.domain.control.cws;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType (namespace = "http://schemas.datacontract.org/2004/07/ConsoleService")
public class Credential {
	private String accountId;
	private String password;
	private String sessionId;
	private String userName;

    @XmlElement(name = "AccountId", required = true)
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
    @XmlElement(name = "Password", required = true)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    @XmlElement(name = "SessionId", required = true)
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	@XmlElement(name = "UserName", required = true)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
