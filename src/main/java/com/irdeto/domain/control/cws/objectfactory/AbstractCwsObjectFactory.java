package com.irdeto.domain.control.cws.objectfactory;

import com.irdeto.domain.control.cws.Credential;
import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;
import com.irdeto.manager.properties.PropertyException;
import com.irdeto.manager.task.BeanUtil;

public abstract class AbstractCwsObjectFactory {
	protected static Credential createUserCredential()  {
		Credential userCredential = new Credential();
		try {
			userCredential.setAccountId(BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.CPS_ACCOUNT_ID_KEY));
			userCredential.setUserName(BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.CONTROL_CWS_USER_NAME_KEY));
			userCredential.setPassword(BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.CONTROL_CWS_PASSWORD_KEY));
		} catch (PropertyException e) {
		}
		return userCredential;
	}

}
