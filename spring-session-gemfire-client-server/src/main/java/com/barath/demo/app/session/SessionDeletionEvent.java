package com.barath.demo.app.session;

import org.springframework.session.Session;
import org.springframework.session.events.SessionDeletedEvent;

public class SessionDeletionEvent extends SessionDeletedEvent {

	public SessionDeletionEvent(Object source, Session session) {
		super(source, session);
		System.out.println("Session Deleted event "+session.getId());
	}

}
