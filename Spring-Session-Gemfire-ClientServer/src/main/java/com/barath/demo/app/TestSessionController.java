package com.barath.demo.app;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.ExpiringSession;
import org.springframework.session.SessionRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.barath.demo.app.session.GemfireSessionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gemstone.gemfire.cache.query.FunctionDomainException;
import com.gemstone.gemfire.cache.query.NameResolutionException;
import com.gemstone.gemfire.cache.query.QueryInvocationTargetException;
import com.gemstone.gemfire.cache.query.TypeMismatchException;

@RestController
public class TestSessionController {
	
	private static final Logger logger=LoggerFactory.getLogger(TestSessionController.class);
	private final ObjectMapper mapper=new ObjectMapper();
	private static final String WELCOME_VIEW="/WEB-INF/jsp/welcome.jsp";	
	private static final String HEADER_TOKEN="x-auth-token";
	private static final String SESSION_EXPIRY_PAGE="sessionexpiry.jsp";
	
	@Autowired
	private GemfireSessionRepository sessionRep;
	
	
	@RequestMapping("/test/{id}")
	public Object getSession(@PathVariable("id") String sessionId){
			
		return sessionRep.getSession(sessionId);		
		
	}
	
	@RequestMapping("/test/session")
	public Object testSession(){
		String sessionId=getSessionToken();
		System.out.println("Test Session "+sessionId);
		ExpiringSession session= sessionRep.getSession(sessionId);		
		if(session !=null && session.isExpired()){
			return " Session is Expired";
		}
		
		return session;
	}
	

	@RequestMapping("/findAll")
	public List<String> findAllSession(){
		System.out.println("Finding all the sessions");
		List<String> sessions=null;
		try {
			List<ExpiringSession> sessionsFound= sessionRep.findAllSessions();
			if(sessionsFound !=null && !sessionsFound.isEmpty()){
				sessions=new ArrayList<String>(sessionsFound.size());
				for(ExpiringSession session: sessionsFound){
					sessions.add(mapper.writeValueAsString(session));
				}
			}
		
		} catch (FunctionDomainException | TypeMismatchException | NameResolutionException
				| QueryInvocationTargetException | JsonProcessingException e) {
			
			e.printStackTrace();
		}	
		return sessions;
		
	}


	@RequestMapping("/service")
	public Object testService(){
		String sessionId=getSessionToken();
	
		System.out.println("Testing the service with session id "+sessionId);
		ExpiringSession session= sessionRep.getSession(sessionId);		
		if(session !=null && !session.isExpired()){
			return " Service is  processed and session is active ";
		}
		
		return "OOPS ! Session Expired . Login again <a href='/CITI'></a>";
		
		
	}
	
	
	
	
	
	private String getSessionToken(){
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request.getHeader(HEADER_TOKEN);
	}
	
}
