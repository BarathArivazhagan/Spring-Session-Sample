package com.barath.app.session;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.ExpiringSession;
import org.springframework.session.MapSession;
import org.springframework.session.SessionRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.barath.app.entities.SessionEntity;
import com.barath.app.repository.SessionEntityRepository;

public class WOASpringSessionRepository implements SessionRepository<ExpiringSession> {

	private final Map<String,ExpiringSession> sessionMap=new ConcurrentHashMap<String,ExpiringSession>();
	
	private int maxInactiveIntervalInSeconds=180;
	
	
	@Autowired
	private SessionEntityRepository sessionRep;
	
	
	public WOASpringSessionRepository(int maxInactiveIntervalInSeconds) {
		super();
		this.maxInactiveIntervalInSeconds = maxInactiveIntervalInSeconds;
	}

	@Override
	public ExpiringSession createSession() {
		System.out.println("NEW SESSION IS CREATED =====>");
		ExpiringSession session=new MapSession();
		session.setMaxInactiveIntervalInSeconds(this.maxInactiveIntervalInSeconds);
		this.sessionMap.put(session.getId(), session);		
		return session;
	}

	@Override
	public void save(ExpiringSession session) {
		System.out.println("SAVING THE SESSION WITH SESSION ID "+session.getId());
		sessionRep.save(convertToSessionEntity(session));
		this.sessionMap.put(session.getId(), session);
	}

	@Override
	//@Transactional(readOnly=true)
	public ExpiringSession getSession(String id) {
		ExpiringSession session=null;
		System.out.println("GETTING THE SESSION WITH SESSION ID"+id);
		SessionEntity sessionEntity=sessionRep.findOne(id);
		if(sessionEntity !=null ){
			session=convertToExpiringSession(sessionEntity);
			if(isExpired(session)){
				delete(id);
				return null;
			}
		}
		return session;
	}

	@Override
	public void delete(String id) {
		
		System.out.println("DELETING THE SESSION WITH SESSION ID===>");
		Assert.notNull(sessionRep,"Session Repository cannot be null");
		
		
		
		
		sessionRep.delete(id);
		
		this.sessionMap.remove(id);
	}
	
	
	private ExpiringSession convertToExpiringSession(SessionEntity sessionEntity){
		printSession(sessionEntity);
		MapSession mapSession=new MapSession();
		mapSession.setId(sessionEntity.getSessionId());
		mapSession.setCreationTime(sessionEntity.getCreationTime());;
		mapSession.setLastAccessedTime(sessionEntity.getLastAccessTime());
		mapSession.setMaxInactiveIntervalInSeconds(sessionEntity.getMaxInactiveIntervalInSeconds());
		mapSession.setAttribute("USER_NAME", sessionEntity.getUserName());
		

		return (ExpiringSession)mapSession;
		
	}
	
	
	private SessionEntity convertToSessionEntity(ExpiringSession session){
	printSession(session);
	SessionEntity sessionEntity=new SessionEntity(session.getId(), session.getCreationTime(), session.getLastAccessedTime(), session.getMaxInactiveIntervalInSeconds(), (String)session.getAttribute("USER_NAME"), session.isExpired());
		
		return sessionEntity;
	}
	
	public boolean isExpired(ExpiringSession session){
	 System.out.println("IS SESSIOON EXPIRED METHOD IS INVOKED");	
	 Date creationDate=new Date(session.getCreationTime());
	 Date lastAccessedDate=new Date(session.getLastAccessedTime());
	 long milliSeconds=System.currentTimeMillis()-TimeUnit.SECONDS.toMillis(session.getMaxInactiveIntervalInSeconds());
	boolean isExpired=System.currentTimeMillis()-TimeUnit.SECONDS.toMillis(session.getMaxInactiveIntervalInSeconds()) >= session.getLastAccessedTime();
	System.out.println("IS EXPIRED "+isExpired);
	 if(isExpired){
		 System.out.println("SESSION WITH SESSION ID "+session.getId()+" IS EXPIRED");
		 
		 return true;
	 }
	 return false;
	}
	public void  printSession(Object sessionObj) {
		ExpiringSession session=null;
		SessionEntity sessionEntity=null;
		if(sessionObj instanceof ExpiringSession){
			session=(ExpiringSession) sessionObj;
			System.out.println("Session ID is "+session.getId());
			System.out.println("Session Creation time  is "+session.getCreationTime());
			System.out.println("Session Last accessed time  is "+session.getLastAccessedTime());
		}else if(sessionObj instanceof SessionEntity){
			sessionEntity=(SessionEntity) sessionObj;
			System.out.println("Session ID is "+sessionEntity.getSessionId());
			System.out.println("Session Creation time  is "+sessionEntity.getCreationTime());
			System.out.println("Session Last accessed time  is "+sessionEntity.getLastAccessTime());
			
		}
	}

}
