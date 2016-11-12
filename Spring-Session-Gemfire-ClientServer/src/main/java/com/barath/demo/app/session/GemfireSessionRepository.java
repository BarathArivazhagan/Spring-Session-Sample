package com.barath.demo.app.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.session.ExpiringSession;
import org.springframework.session.MapSession;
import org.springframework.session.SessionRepository;
import org.springframework.session.events.SessionCreatedEvent;
import org.springframework.session.events.SessionDeletedEvent;
import org.springframework.session.events.SessionExpiredEvent;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.query.FunctionDomainException;
import com.gemstone.gemfire.cache.query.NameResolutionException;
import com.gemstone.gemfire.cache.query.Query;
import com.gemstone.gemfire.cache.query.QueryInvocationTargetException;
import com.gemstone.gemfire.cache.query.QueryService;
import com.gemstone.gemfire.cache.query.SelectResults;
import com.gemstone.gemfire.cache.query.TypeMismatchException;


public class GemfireSessionRepository implements SessionRepository<ExpiringSession>{
	
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
		System.out.println("NEW SESSION IS CREATED =====>"+session.getId());
		//printSession(session);
		return session;
	}

	@Override
	public void save(ExpiringSession session) {
		System.out.println("SAVING THE SESSION WITH SESSION ID "+session.getId());
		//printSession(session);
		this.sessionRegion.put(session.getId(),session);
	
	}

	@Override
	public ExpiringSession getSession(String id) {
		ExpiringSession session=null;
		System.out.println("GETTING THE SESSION WITH SESSION ID"+id);
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
		
		System.out.println("DELETING THE SESSION WITH SESSION ID===>"+id);				
		//eventPublisher.publishEvent(new SessionDeletedEvent(this, this.sessionRegion.get(id)));
		this.sessionRegion.remove(id);
		
	}
	
	
	
	
	public void  printSession(Object sessionObj) {
		ExpiringSession session=null;
		
		if(sessionObj instanceof ExpiringSession){
			session=(ExpiringSession) sessionObj;
			System.out.println("Session ID is "+session.getId());
			System.out.println("Session Creation time  is "+new Date(session.getCreationTime()));
			System.out.println("Session Last accessed time  is "+new Date(session.getLastAccessedTime()));
		}
	}
	
	public List<ExpiringSession> findAllSessions() throws FunctionDomainException, TypeMismatchException, NameResolutionException, QueryInvocationTargetException {
		
		
		QueryService queryService = this.clientCache.getQueryService();	
		Query query = queryService.newQuery(SELECT_QUERYSTRING);			
		SelectResults<Object> results = (SelectResults<Object>)query.execute();
		System.out.println("Results "+results);
		System.out.println("SIZE "+results.size());	
		List<ExpiringSession> sessions=new ArrayList<ExpiringSession>(results.size());
		//results.stream().forEach(System.out::println);
		 return sessions;
	}


}
