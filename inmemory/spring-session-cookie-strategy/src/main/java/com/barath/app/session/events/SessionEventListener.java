package com.barath.app.session.events;

import java.lang.invoke.MethodHandles;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SessionEventListener implements HttpSessionListener {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	/**
	 * 
	 */
	private static final long serialVersionUID = 8677975109650302857L;	
	
	


	@Override
	public void sessionCreated(HttpSessionEvent se) {
		
		logger.info("WOASESSIONCREATIONEVENT ");
		
	}




	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		
		logger.info("WOASESSIONCREATIONEVENT ");
	}





	






	
	

}
