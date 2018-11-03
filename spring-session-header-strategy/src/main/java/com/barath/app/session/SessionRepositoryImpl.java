package com.barath.app.session;

import java.lang.invoke.MethodHandles;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.session.ExpiringSession;
import org.springframework.session.MapSession;
import org.springframework.session.SessionRepository;
import org.springframework.util.Assert;

import com.barath.app.entities.Session;
import com.barath.app.repository.SessionEntityRepository;

public class SessionRepositoryImpl implements SessionRepository<ExpiringSession> {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private final Map<String,ExpiringSession> sessionMap=new ConcurrentHashMap<String,ExpiringSession>();
	
	private int maxInactiveIntervalInSeconds=180;
	
	private final SessionEntityRepository sessionRepository;
	
	
	public SessionRepositoryImpl(SessionEntityRepository sessionRepository,int maxInactiveIntervalInSeconds) {
		super();
		this.maxInactiveIntervalInSeconds = maxInactiveIntervalInSeconds;
		this.sessionRepository = sessionRepository;
	}

	@Override
	public ExpiringSession createSession() {
		logger.info("NEW SESSION IS CREATED =====>");
		ExpiringSession session=new MapSession();
		session.setMaxInactiveIntervalInSeconds(this.maxInactiveIntervalInSeconds);
		this.sessionMap.put(session.getId(), session);		
		return session;
	}

	@Override
	public void save(ExpiringSession session) {
		logger.info("SAVING THE SESSION WITH SESSION ID "+session.getId());
		sessionRepository.save(convertToSessionEntity(session));
		this.sessionMap.put(session.getId(), session);
	}

	@Override
	public ExpiringSession getSession(String id) {
		ExpiringSession session=null;
		logger.info("GETTING THE SESSION WITH SESSION ID"+id);
		Session sessionEntity=sessionRepository.getOne(id);
		if(sessionEntity !=null ){
			session=convertToExpiringSession(sessionEntity);
			if(isExpired(session)){
				delete(id);
			}
		}
		return session;
	}

	@Override
	public void delete(String id) {
		
		logger.info("DELETING THE SESSION WITH SESSION ID===>");
		Assert.notNull(sessionRepository,"Session Repository cannot be null");
		sessionRepository.delete(id);
		this.sessionMap.remove(id);
	}
	
	
	private ExpiringSession convertToExpiringSession(Session sessionEntity){
		
		MapSession mapSession=new MapSession();
		mapSession.setId(sessionEntity.getSessionId());
		mapSession.setCreationTime(sessionEntity.getCreationTime());;
		mapSession.setLastAccessedTime(sessionEntity.getLastAccessTime());
		mapSession.setLastAccessedTime(sessionEntity.getMaxInactiveIntervalInSeconds());
		

		return (ExpiringSession)mapSession;
		
	}
	
	
	private Session convertToSessionEntity(ExpiringSession session){
		
	Session sessionEntity=new Session(session.getId(), session.getCreationTime(), session.getLastAccessedTime(), session.getMaxInactiveIntervalInSeconds(), (String)session.getAttribute("USERNAME"), session.isExpired());
		
		return sessionEntity;
	}
	
	public boolean isExpired(ExpiringSession session){
	 logger.info("IS SESSIOON EXPIRED METHOD IS INVOKED");	
	 Date creationDate=new Date(session.getCreationTime());
	 Date lastAccessedDate=new Date(session.getLastAccessedTime());
	 long milliSeconds=System.currentTimeMillis()-TimeUnit.SECONDS.toMillis(session.getMaxInactiveIntervalInSeconds());
	boolean isExpired=System.currentTimeMillis()-TimeUnit.SECONDS.toMillis(session.getMaxInactiveIntervalInSeconds()) >= session.getLastAccessedTime();
	logger.info("IS EXPIRED "+isExpired);
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
			logger.info("Session ID is {}",session.getId());
			logger.info("Session Creation time  is {}",session.getCreationTime());
			logger.info("Session Last accessed time  is {} ",session.getLastAccessedTime());
		}else if(sessionObj instanceof Session){
			sessionEntity=(Session) sessionObj;
			logger.info("Session ID is {}",sessionEntity.getSessionId());
			logger.info("Session Creation time  is {}",sessionEntity.getCreationTime());
			logger.info("Session Last accessed time  {} ",sessionEntity.getLastAccessTime());
			
		}
	}

}
