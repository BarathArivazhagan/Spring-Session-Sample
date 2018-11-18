package com.barath.app.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.query.FunctionDomainException;
import org.apache.geode.cache.query.NameResolutionException;
import org.apache.geode.cache.query.Query;
import org.apache.geode.cache.query.QueryInvocationTargetException;
import org.apache.geode.cache.query.QueryService;
import org.apache.geode.cache.query.SelectResults;
import org.apache.geode.cache.query.TypeMismatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.session.ExpiringSession;
import org.springframework.session.MapSession;
import org.springframework.session.SessionRepository;
import org.springframework.session.events.SessionCreatedEvent;



/**
 * This is custom implementation of gemfire repository with gemfire session.
 * 
 *  
 * @author barath
 *
 */
public class GemfireSessionRepository implements SessionRepository<ExpiringSession>{
	
	private static final Logger logger=LoggerFactory.getLogger(GemfireSessionRepository.class);
	private static final String SESSION_REGION_NAME="session";
	private Region<String, ExpiringSession> sessionRegion; 
	private ApplicationEventPublisher eventPublisher;

	private static final String SELECT_QUERYSTRING="select * from /session";	
	private int maxInactiveIntervalInSeconds=180;
	
	private ClientCache clientCache;	
	
	public GemfireSessionRepository(int maxInactiveIntervalInSeconds,ClientCache clientCache,ApplicationEventPublisher eventPublisher) {
		super();
		this.maxInactiveIntervalInSeconds = maxInactiveIntervalInSeconds;
		this.eventPublisher=eventPublisher;
		this.clientCache=clientCache;
		this.sessionRegion=clientCache.getRegion(SESSION_REGION_NAME);
	}

	@Override
	public ExpiringSession createSession() {
		
		ExpiringSession session=new MapSession();
		session.setMaxInactiveIntervalInSeconds(this.maxInactiveIntervalInSeconds);
		eventPublisher.publishEvent(new SessionCreatedEvent(this, session));
		logger.info("NEW SESSION IS CREATED =====>"+session.getId());
		return session;
	}

	@Override
	public void save(ExpiringSession session) {
		logger.info("SAVING THE SESSION WITH SESSION ID "+session.getId());
		//printSession(session);
		this.sessionRegion.put(session.getId(),session);
	
	}

	@Override
	public ExpiringSession getSession(String id) {
		ExpiringSession session=null;
		logger.info("GETTING THE SESSION WITH SESSION ID"+id);
		session=this.sessionRegion.get(id);
		if(session == null){
			return null;
		}
		if(session.isExpired()){
				//eventPublisher.publishEvent(new SessionExpiredEvent(this, session));
				delete(session.getId());
				return null;
		}
					
		/*testing purpose */
		//printSession(session);
		return session;
	}

	@Override
	public void delete(String id) {
		
		logger.info("DELETING THE SESSION WITH SESSION ID===>"+id);				
		//eventPublisher.publishEvent(new SessionDeletedEvent(this, this.sessionRegion.get(id)));
		this.sessionRegion.remove(id);
		
	}
	
	
	
	
	public void  printSession(Object sessionObj) {
		ExpiringSession session=null;
		
		if(sessionObj instanceof ExpiringSession){
			session=(ExpiringSession) sessionObj;
			logger.info("Session ID is "+session.getId());
			logger.info("Session Creation time  is "+new Date(session.getCreationTime()));
			logger.info("Session Last accessed time  is "+new Date(session.getLastAccessedTime()));
		}
	}
	
	public List<ExpiringSession> findAllSessions() throws FunctionDomainException, TypeMismatchException, NameResolutionException, QueryInvocationTargetException {
		
		
		QueryService queryService = this.clientCache.getQueryService();	
		Query query = queryService.newQuery(SELECT_QUERYSTRING);			
		@SuppressWarnings("unchecked")
		SelectResults<Object> results = (SelectResults<Object>)query.execute();
		logger.info("Results "+results);
		logger.info("SIZE "+results.size());	
		List<ExpiringSession> sessions=new ArrayList<ExpiringSession>(results.size());
		return sessions;
	}


}
