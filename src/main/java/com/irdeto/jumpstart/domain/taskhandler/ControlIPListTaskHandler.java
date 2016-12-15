package com.irdeto.jumpstart.domain.taskhandler;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.irdeto.domain.constants.TaskProperty;
import com.irdeto.jumpstart.domain.control.ControlBatch;
import com.irdeto.jumpstart.domain.control.ControlIPList;
import com.irdeto.jumpstart.manager.JumpstartControlManager;
import com.irdeto.jumpstart.manager.JumpstartControlManagerImpl;
import com.irdeto.manager.properties.PropertiesManager;
import com.irdeto.manager.web.WebManager;
import com.irdeto.manager.xml.XmlManager;
import com.irdeto.taskhandler.AbstractTaskHandler;

@Component("ControlIPList")
public class ControlIPListTaskHandler extends AbstractTaskHandler {
	@TaskProperty(type=ControlBatch.class)
	public static final String CONTROL_IPLIST_PROPERTY = "ControlIPList";

	@Resource(name="propertiesManager")
	private PropertiesManager propertiesManager;

	@Resource(name="webManager")
	private WebManager webManager;

	@Resource(name="xmlManager")
	private XmlManager xmlManager;
	
	private JumpstartControlManager controlManager;
	
	@PostConstruct
	protected void setupJumpstartControlManager() {
		controlManager = new JumpstartControlManagerImpl(propertiesManager, webManager, xmlManager);
	}
	
	@Override
	public void processTask(long workItemId, String workItemName, Map<String, Object> verifiedParameters, Map<String, Object> results) throws Exception {
		ControlIPList ipList = (ControlIPList) verifiedParameters.get(CONTROL_IPLIST_PROPERTY);
		controlManager.ipList(ipList);
	}
}
