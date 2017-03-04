package com.barath.app.session.events;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.session.Session;
import org.springframework.session.events.AbstractSessionEvent;
import org.springframework.session.events.SessionCreatedEvent;

public class WOASessionEventListener implements HttpSessionListener {

	
	
	
	
	



	/**
	 * 
	 */
	private static final long serialVersionUID = 8677975109650302857L;	
	
	


	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		System.out.println("WOASESSIONCREATIONEVENT ");
		
	}




	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		System.out.println("WOASESSIONCREATIONEVENT ");
	}





	






	
	

}
