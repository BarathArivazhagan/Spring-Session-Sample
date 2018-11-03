package com.barath.demo.app.session;

import org.springframework.session.Session;
import org.springframework.session.events.SessionExpiredEvent;

public class SessionExpiryEvent  extends SessionExpiredEvent{

	public SessionExpiryEvent(Object source, Session session) {
		super(source, session);
		System.out.println("SESSION EXPIRED EVENT "+session.getId());
	}

}
