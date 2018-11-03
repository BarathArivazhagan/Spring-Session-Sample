package com.barath.app.session;

import org.springframework.session.Session;
import org.springframework.session.events.SessionExpiredEvent;

public class SessionExpiryEvent  extends SessionExpiredEvent{

	private static final long serialVersionUID = 2321454644040537312L;

	public SessionExpiryEvent(Object source, Session session) {
		super(source, session);
		System.out.println("SESSION EXPIRED EVENT "+session.getId());
	}

}
