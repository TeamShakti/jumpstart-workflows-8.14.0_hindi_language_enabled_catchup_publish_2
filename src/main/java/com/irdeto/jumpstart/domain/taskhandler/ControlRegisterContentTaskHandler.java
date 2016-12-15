package com.irdeto.jumpstart.domain.taskhandler;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.irdeto.domain.constants.TaskProperty;
import com.irdeto.jumpstart.domain.control.ControlBatch;
import com.irdeto.jumpstart.manager.JumpstartControlManager;
import com.irdeto.jumpstart.manager.JumpstartControlManagerImpl;
import com.irdeto.manager.properties.PropertiesManager;
import com.irdeto.manager.web.WebManager;
import com.irdeto.manager.xml.XmlManager;
import com.irdeto.taskhandler.AbstractTaskHandler;

@Component("JumpstartControlRegisterContent")
public class ControlRegisterContentTaskHandler extends AbstractTaskHandler {
	@TaskProperty(type=ControlBatch.class)
	public static final String CONTROL_BATCH_PROPERTY = "ControlBatch";

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
		ControlBatch batch = (ControlBatch) verifiedParameters.get(CONTROL_BATCH_PROPERTY);
		controlManager.registerContent(batch);
	}
}
