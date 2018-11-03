package com.barath.app.session;

import org.springframework.session.Session;
import org.springframework.session.events.SessionCreatedEvent;

public class SessionCreationEvent extends SessionCreatedEvent {

	private static final long serialVersionUID = 1060686337584943013L;

	public SessionCreationEvent(Object source, Session session) {
		
		super(source, session);
		System.out.println("SESSION CREATION EVENT "+session);
		
	}

}
