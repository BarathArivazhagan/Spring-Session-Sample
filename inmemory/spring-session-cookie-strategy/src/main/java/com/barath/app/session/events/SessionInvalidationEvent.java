package com.barath.app.session.events;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.session.Session;
import org.springframework.session.events.SessionDeletedEvent;

public class SessionInvalidationEvent  extends SessionDeletedEvent{
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


	/**
	 * 
	 */
	private static final long serialVersionUID = -1231022179787154301L;

	public SessionInvalidationEvent(Object source, Session session) {
		super(source, session);
		logger.info("WOASessionInvalidationEvent ");
		logger.info("WOASessionInvalidationEvent SESSION WITH SESSION ID  "+session.getId());
	}

}
