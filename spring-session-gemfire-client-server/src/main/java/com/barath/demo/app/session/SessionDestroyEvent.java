package com.barath.demo.app.session;

import org.springframework.session.Session;

public class SessionDestroyEvent extends org.springframework.session.events.SessionDestroyedEvent {

	public SessionDestroyEvent(Object source, Session session) {
		super(source, session);
		System.out.println("SESSION DESTROY EVENT "+session.getId());
	}

}
