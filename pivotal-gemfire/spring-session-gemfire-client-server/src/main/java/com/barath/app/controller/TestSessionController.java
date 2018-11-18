package com.barath.app.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.barath.app.session.GemfireSessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.session.ExpiringSession;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
	private static final String HEADER_TOKEN="x-auth-token";
	
	

	private final GemfireSessionRepository gemfireSessionRepository;

	public TestSessionController(GemfireSessionRepository gemfireSessionRepository) {
		this.gemfireSessionRepository = gemfireSessionRepository;
	}

	@RequestMapping("/test/{id}")
	public Object getSession(@PathVariable("id") String sessionId){
			
		return gemfireSessionRepository.getSession(sessionId);
		
	}
	
	@RequestMapping("/test/session")
	public Object testSession(){
		String sessionId=getSessionToken();
		System.out.println("Test Session "+sessionId);
		ExpiringSession session= gemfireSessionRepository.getSession(sessionId);
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
			List<ExpiringSession> sessionsFound= gemfireSessionRepository.findAllSessions();
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
	public Object testService(HttpServletRequest request,@RequestHeader Map<String, String> headers,HttpServletResponse response){
		Map<String,Object> responseMap=new HashMap<String,Object>(2);
		headers.entrySet().stream().forEach(System.out::println);
		String sessionId=getSessionToken();
		if(sessionId == null ){
			sessionId=request.getSession().getId();
		}
		System.out.println("Testing the service with session id "+sessionId);
		ExpiringSession session= gemfireSessionRepository.getSession(sessionId);
		response.setHeader(HEADER_TOKEN, sessionId);
		if(session !=null && !session.isExpired()){
			responseMap.put("STATUS", "SUCCESS");
			responseMap.put("MESSAGE", "Service is  processed and session is active");
			
			//session.setAttribute("STATUS", "SERVICE PROCESSED ");
		}else{
			responseMap.put("STATUS", "FAIL");
			responseMap.put("MESSAGE", "OOPS ! Session Expired . Login again <a href='/CITI'>here</a>");
		}
		
		//return "OOPS ! Session Expired . Login again <a href='/CITI'></a>";
		//responseMap.put("STATUS", "FAIL");
		//responseMap.put("MESSAGE", "OOPS ! Session Expired . Login again <a href='/CITI'></a>");
		//return session;
		return responseMap;
		
		
	}
	
	
	
	
	
	private String getSessionToken(){
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request.getHeader(HEADER_TOKEN);
	}
	
}
