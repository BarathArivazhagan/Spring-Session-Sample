package com.barath.app.session;

import org.springframework.session.Session;

public class SessionDestroyEvent extends org.springframework.session.events.SessionDestroyedEvent {

	private static final long serialVersionUID = -7518716607649762377L;

	public SessionDestroyEvent(Object source, Session session) {
		super(source, session);
		System.out.println("SESSION DESTROY EVENT "+session.getId());
	}

}
