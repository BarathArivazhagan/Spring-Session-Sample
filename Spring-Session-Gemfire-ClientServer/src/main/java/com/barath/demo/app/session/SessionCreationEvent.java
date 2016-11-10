package com.barath.demo.app.session;

import org.springframework.session.Session;
import org.springframework.session.events.SessionCreatedEvent;

public class SessionCreationEvent extends SessionCreatedEvent {

	public SessionCreationEvent(Object source, Session session) {
		
		super(source, session);
		System.out.println("SESSION CREATION EVENT "+session);
		
	}

}
