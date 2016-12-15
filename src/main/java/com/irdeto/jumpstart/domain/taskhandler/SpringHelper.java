package com.irdeto.jumpstart.domain.taskhandler;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;

public class SpringHelper {

	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz, ApplicationContext applicationContext){
		
		try {
			return applicationContext.getBean(clazz);
		} catch (BeansException e) {
			AutowireCapableBeanFactory factory = applicationContext.getAutowireCapableBeanFactory();
			T bean = (T) factory.createBean(clazz, AutowireCapableBeanFactory.AUTOWIRE_NO, false);
			return bean;
		}
			
	}
}
