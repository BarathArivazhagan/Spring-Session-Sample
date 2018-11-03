package com.barath.app.session.events;

import org.springframework.session.Session;
import org.springframework.session.events.SessionDeletedEvent;

public class WOASessionInvalidationEvent  extends SessionDeletedEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1231022179787154301L;

	public WOASessionInvalidationEvent(Object source, Session session) {
		super(source, session);
		System.out.println("WOASessionInvalidationEvent ");
		System.out.println("WOASessionInvalidationEvent SESSION WITH SESSION ID  "+session.getId());
	}

}
