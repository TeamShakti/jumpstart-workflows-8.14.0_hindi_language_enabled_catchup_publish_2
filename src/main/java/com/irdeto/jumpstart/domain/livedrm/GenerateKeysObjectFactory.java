package com.irdeto.jumpstart.domain.livedrm;

import java.util.ArrayList;
import java.util.List;


public class GenerateKeysObjectFactory extends AbstractLiveDrmObjectFactory {
	public static final String PROTECTION_SYSTEM_PLAYREADY = "PlayReady";
	public static final String PROTECTION_SYSTEM_ACTIVECLOAK_PLAYREADY = "ActiveCloakPR";
	public static final String PROTECTION_SYSTEM_HLS_PANTOS = "HLSPantos";
	public static final String PROTECTION_SYSTEM_SKE = "SKE";
	public static final String PROTECTION_SYSTEM_CA = "CA";
	public static final String PROTECTION_SYSTEM_WIDEVINE = "Widevine";

	private String contentId;
	private List<String> protectionSystemList = new ArrayList<>();
	
	@Override
	protected AbstractLiveDrmSoapBody createSoapBody() {
		if (getAccountId() == null) {
			return null;
		}
		GenerateKeysLiveDrmSoapBody generatePlayreadyKeys = new GenerateKeysLiveDrmSoapBody();
		GenerateKeysLiveDrmSoapBodyDetails details = new GenerateKeysLiveDrmSoapBodyDetails();
		details.setAccountId(getAccountId());
		details.setContentId(getContentId());
		details.setProtectionSystemList(getProtectionSystemList());
		details.setCryptoPeriod("1");
		generatePlayreadyKeys.setDetails(details);
		return generatePlayreadyKeys;	
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public List<String> getProtectionSystemList() {
		return protectionSystemList;
	}

	public void setProtectionSystemList(List<String> protectionSystemList) {
		this.protectionSystemList = protectionSystemList;
	}
}
