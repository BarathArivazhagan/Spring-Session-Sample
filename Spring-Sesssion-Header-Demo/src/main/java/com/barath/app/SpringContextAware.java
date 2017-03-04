package com.barath.app;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextAware implements ApplicationContextAware{
	
	private ApplicationContext appContext;

	@Override
	public void setApplicationContext(ApplicationContext appContext) throws BeansException {
		this.appContext=appContext;
		
	}

	public ApplicationContext getAppContext() {
		return appContext;
	}

		

}
