package com.irdeto.jumpstart.domain.livedrm;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;


public class GenerateKeysLiveDrmSoapBodyDetails extends AbstractLiveDrmSoapBodyDetails {
	private static final String NAMESPACE_LIVEDRMSERVICE = "http://man.entriq.net/livedrmservice/";
	private String accountId;
	private String contentId;
	private String cryptoPeriod;
	private List<String> protectionSystemList;
	
	@XmlElement(name = "accountId", required = true, namespace = NAMESPACE_LIVEDRMSERVICE)
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	@XmlElement(name = "contentId", required = true, namespace = NAMESPACE_LIVEDRMSERVICE)
	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	@XmlElementWrapper(name = "protectionSystem", namespace = NAMESPACE_LIVEDRMSERVICE)
	@XmlElement(name = "string", namespace = NAMESPACE_LIVEDRMSERVICE)
	public List<String> getProtectionSystemList() {
		return protectionSystemList;
	}

	public void setProtectionSystemList(List<String> protectionSystemList) {
		this.protectionSystemList = protectionSystemList;
	}

	@XmlElement(name = "cryptoPeriod", namespace = NAMESPACE_LIVEDRMSERVICE)
	public String getCryptoPeriod() {
		return cryptoPeriod;
	}

	public void setCryptoPeriod(String cryptoPeriod) {
		this.cryptoPeriod = cryptoPeriod;
	}
}
