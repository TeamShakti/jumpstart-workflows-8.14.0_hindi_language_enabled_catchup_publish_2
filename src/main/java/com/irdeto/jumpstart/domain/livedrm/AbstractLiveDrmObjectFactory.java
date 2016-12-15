package com.irdeto.jumpstart.domain.livedrm;

import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;
import com.irdeto.manager.properties.PropertyException;
import com.irdeto.manager.task.BeanUtil;


public abstract class AbstractLiveDrmObjectFactory implements LiveDrmObjectFactory {
	@Override
	public LiveDrmSoapEnvelope createSoapMsgEnvelope() {
		return new LiveDrmSoapEnvelope(createSoapHeader(), createSoapBody());
	}
	
	protected abstract AbstractLiveDrmSoapBody createSoapBody();

	protected String getCwsUsername() {
		try {
			return BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.CONTROL_CWS_USER_NAME_KEY);
		} catch (PropertyException e) {
		}
		return null;
	}
	
	protected String getCwsPassword() {
		try {
			return BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.CONTROL_CWS_PASSWORD_KEY);
		} catch (PropertyException e) {
		}
		return null;
	}
	
	protected String getAccountId() {
		try {
			return BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.CONTROL_ACCOUNT_ID_KEY);
		} catch (PropertyException e) {
		}
		return null;
	}
	
	protected LiveDrmSoapHeader createSoapHeader() {
		LiveDrmSoapHeader header = new LiveDrmSoapHeader();
		LiveDrmServiceHeader liveDrmServiceHeader = new LiveDrmServiceHeader();
		liveDrmServiceHeader.setUsername(getCwsUsername());
		liveDrmServiceHeader.setPassword(getCwsPassword());
		header.setLiveDrmServiceHeader(liveDrmServiceHeader);
		return header;	
	}
}
