package com.barath.app.controller;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.SessionRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MainController {
	
	private static final String HOME_VIEW="/html/login.html";
	private static final String ERROR_VIEW="/html/error.html";
	private static final String NEWSESSION_VIEW="/WEB-INF/jsp/NewSession.jsp";
	
	private static final String HEADER_TOKEN="x-auth-token";
	
	@Autowired
	private SessionRepository sessionRep;
	
	@RequestMapping(value="/")
	public ModelAndView homePage(){		
		
		return new ModelAndView(HOME_VIEW);
	}
	
	
	
	@RequestMapping(value="/newSession",method=RequestMethod.GET)
	public ModelAndView handleNewSession(HttpServletRequest request,HttpServletResponse response){
		
		
		String token=request.getHeader(HEADER_TOKEN);
		getHeadersInfo(request);
		System.out.println("HEADER TOKEN IS "+token);
		String sessionId=request.getSession().getId();
		System.out.println("SESSION ID IS "+sessionId);
		//sessionRep.delete(sessionId);
		Collection<String> names=response.getHeaderNames();
		for(String name:names){
			System.out.println("RESPONSE HEADER NAMES ARE "+name);
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
			System.out.println("HEADER KEY ==> "+entry.getKey()+ "   HEADER VALUE IS "+entry.getValue());
		}
		return map;
	  }
}
