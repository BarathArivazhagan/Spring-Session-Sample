package com.barath.app.session;

import org.springframework.session.Session;
import org.springframework.session.events.SessionDeletedEvent;

public class SessionDeletionEvent extends SessionDeletedEvent {

	private static final long serialVersionUID = 3520652843171902682L;

	public SessionDeletionEvent(Object source, Session session) {
		super(source, session);
		System.out.println("Session Deleted event "+session.getId());
	}

}
