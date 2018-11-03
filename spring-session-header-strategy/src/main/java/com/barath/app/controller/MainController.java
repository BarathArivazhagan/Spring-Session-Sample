package com.barath.app.controller;

import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.ExpiringSession;
import org.springframework.session.SessionRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MainController {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private static final String HOME_VIEW="/html/login.html";
	private static final String ERROR_VIEW="/html/error.html";
	private static final String NEWSESSION_VIEW="/WEB-INF/jsp/NewSession.jsp";
	
	private static final String HEADER_TOKEN="x-auth-token";
	
	private final SessionRepository<ExpiringSession> sessionRepository;	
	
	
	public MainController(SessionRepository<ExpiringSession> sessionRepository) {
		super();
		this.sessionRepository = sessionRepository;
	}



	@RequestMapping(value="/")
	public ModelAndView homePage(){		
		
		return new ModelAndView(HOME_VIEW);
	}
	
	
	
	@RequestMapping(value="/newSession",method=RequestMethod.GET)
	public ModelAndView handleNewSession(HttpServletRequest request,HttpServletResponse response){
		
		
		String token=request.getHeader(HEADER_TOKEN);
		getHeadersInfo(request);
		logger.info("HEADER TOKEN IS "+token);
		String sessionId=request.getSession().getId();
		logger.info("SESSION ID IS "+sessionId);
		//sessionRep.delete(sessionId);
		Collection<String> names=response.getHeaderNames();
		for(String name:names){
			logger.info("RESPONSE HEADER NAMES ARE "+name);
		}
		return new ModelAndView(NEWSESSION_VIEW);
	}

	
	
	
	private Map<String, String> getHeadersInfo(HttpServletRequest request) {

		Map<String, String> map = new HashMap<String, String>();

		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			
			map.put(key, value);
		}
		for(Map.Entry<String, String> entry:map.entrySet()){
			logger.info("HEADER KEY ==> "+entry.getKey()+ "   HEADER VALUE IS "+entry.getValue());
		}
		return map;
	  }
}
