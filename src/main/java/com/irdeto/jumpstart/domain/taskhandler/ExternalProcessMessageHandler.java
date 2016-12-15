package com.irdeto.jumpstart.domain.taskhandler;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.irdeto.manager.endpoint.activemq.MessageHandler;
import com.irdeto.manager.json.JsonManager;
import com.irdeto.manager.workflow.WorkItemManager;

@Component("externalProcessMessageHandler")
public class ExternalProcessMessageHandler implements MessageHandler,ApplicationContextAware {
	private static final Logger logger = LoggerFactory.getLogger(ExternalProcessMessageHandler.class);

	private static final String WORK_ITEM_ID = "WorkItemId";

	//@Resource(name = "externalProcessMonitorManager")
	private ExternalProcessMonitorManager externalProcessMonitorManager;
	@Resource(name = "jsonManager")
	private JsonManager jsonManager;
	@Resource(name = "workItemManager")
	private WorkItemManager workItemManager;

	private ApplicationContext applicationContext;


	@PostConstruct
	public void setup() {
		this.externalProcessMonitorManager = SpringHelper.getBean(ExternalProcessMonitorManagerImpl.class,applicationContext);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void handle(Message message) throws Exception {
		if (!(message instanceof TextMessage)) {
			return;
		}
		TextMessage textMessage = (TextMessage) message;
		String text = textMessage.getText();
		logger.debug("Command Result:{}", text);
		Map<String, Object> commandLineResponseMessage = (Map<String, Object>) jsonManager
				.deserializeObject(text, Map.class);
		Object workItemIdObject = commandLineResponseMessage.get(WORK_ITEM_ID);
		if (workItemIdObject instanceof String
				&& StringUtils.isNumeric((String) workItemIdObject)) {
			long workItemId = Long.valueOf((String) workItemIdObject);
			externalProcessMonitorManager.cancel(
					workItemId);
			workItemManager.completeWorkItem(workItemId,
					commandLineResponseMessage, true);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		 this.applicationContext = applicationContext;
	}
}
