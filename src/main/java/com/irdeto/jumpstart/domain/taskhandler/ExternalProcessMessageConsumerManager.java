package com.irdeto.jumpstart.domain.taskhandler;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;

import org.apache.activemq.RedeliveryPolicy;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;
import com.irdeto.manager.endpoint.activemq.MessageConsumerManager;
import com.irdeto.manager.properties.PropertiesManager;
import com.irdeto.manager.properties.PropertyException;
import com.irdeto.manager.secondstagebean.SecondStageBeanManager;


@Component("externalProcessMessageConsumerManager")
public class ExternalProcessMessageConsumerManager extends MessageConsumerManager implements ApplicationContextAware {
	@Resource(name = "propertiesManager")
	private PropertiesManager propertiesManager;
	@Resource(name = "connectionFactory")
	private ConnectionFactory connectionFactory;
	//@Resource(name = "externalProcessMessageHandler")
	private ExternalProcessMessageHandler externalProcessMessageHandler;
	@Resource(name = "redeliveryPolicy")
	private RedeliveryPolicy redeliveryPolicy;
	private ApplicationContext applicationContext;
	@Resource(name="secondStageBeanManager")
	private SecondStageBeanManager secondStageBeanManager;
	
	@PostConstruct
	@Override
	public void setupConnection() {
		this.externalProcessMessageHandler = SpringHelper.getBean(ExternalProcessMessageHandler.class,applicationContext);
		setHandler(externalProcessMessageHandler);
		try {
			String queueName = propertiesManager.getProperty(JumpstartPropertyKey.ACTIVEMQ_QUEUE_COMMAND_LINE_KEY);
			setQueue(queueName);
			setRedeliveryPolicy(redeliveryPolicy);
			super.setupConnection();
		} catch (PropertyException e) {
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
}
