package com.barath.app.session;

import java.lang.invoke.MethodHandles;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.ExpiringSession;
import org.springframework.session.MapSession;
import org.springframework.session.SessionRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.barath.app.entities.Session;
import com.barath.app.repository.SessionEntityRepository;

public class SessionRepositoryImpl implements SessionRepository<ExpiringSession> {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private final Map<String,ExpiringSession> sessionMap=new ConcurrentHashMap<String,ExpiringSession>();
	
	private int maxInactiveIntervalInSeconds=180;
	

	private final SessionEntityRepository sessionEntityRepository;
	
	
	public SessionRepositoryImpl(SessionEntityRepository sessionEntityRepository,int maxInactiveIntervalInSeconds) {
		super();
		this.maxInactiveIntervalInSeconds = maxInactiveIntervalInSeconds;
		this.sessionEntityRepository=sessionEntityRepository;
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
		sessionEntityRepository.save(convertToSession(session));
		this.sessionMap.put(session.getId(), session);
	}

	@Override
	public ExpiringSession getSession(String id) {
		ExpiringSession session=null;
		System.out.println("GETTING THE SESSION WITH SESSION ID"+id);
		Session sessionEntity=sessionEntityRepository.findOne(id);
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
		
		logger.info("DELETING THE SESSION WITH SESSION ID===>{}",id);		
		sessionEntityRepository.delete(id);	
		this.sessionMap.remove(id);
	}
	
	
	private ExpiringSession convertToExpiringSession(Session session){
		printSession(session);
		MapSession mapSession=new MapSession();
		mapSession.setId(session.getSessionId());
		mapSession.setCreationTime(session.getCreationTime());;
		mapSession.setLastAccessedTime(session.getLastAccessTime());
		mapSession.setMaxInactiveIntervalInSeconds(session.getMaxInactiveIntervalInSeconds());
		mapSession.setAttribute("USER_NAME", session.getUserName());
		

		return (ExpiringSession)mapSession;
		
	}
	
	
	private Session convertToSession(ExpiringSession session){
		printSession(session);
		Session sessionEntity=new Session(session.getId(), session.getCreationTime(), session.getLastAccessedTime(), session.getMaxInactiveIntervalInSeconds(), (String)session.getAttribute("USER_NAME"), session.isExpired());
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
			 logger.info("SESSION WITH SESSION ID "+session.getId()+" IS EXPIRED");
			 return true;
		 }
		 return false;
	}
	public void  printSession(Object sessionObj) {
		ExpiringSession session=null;
		Session sessionEntity=null;
		if(sessionObj instanceof ExpiringSession){
			session=(ExpiringSession) sessionObj;
			logger.info("Session ID is "+session.getId());
			logger.info("Session Creation time  is "+session.getCreationTime());
			logger.info("Session Last accessed time  is "+session.getLastAccessedTime());
		}else if(sessionObj instanceof Session){
			sessionEntity=(Session) sessionObj;
			logger.info("Session ID is "+sessionEntity.getSessionId());
			logger.info("Session Creation time  is "+sessionEntity.getCreationTime());
			logger.info("Session Last accessed time  is "+sessionEntity.getLastAccessTime());
			
		}
	}

}
