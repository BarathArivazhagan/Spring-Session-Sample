package com.barath.app.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.ExpiringSession;
import org.springframework.session.SessionRepository;
import org.springframework.session.web.http.HttpSessionManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MainController {
	
	private static final String HOME_VIEW="/html/login.html";
	private static final String ERROR_VIEW="/html/error.html";
	private static final String NEWSESSION_VIEW="/WEB-INF/jsp/NewSession.jsp";	
	
	static final String DEFAULT_SESSION_ALIAS_PARAM_NAME = "_s";
	
	@Autowired
	private SessionRepository<ExpiringSession> sessionRep;
	
	@RequestMapping(value="/")
	public ModelAndView homePage(){	
		
		return new ModelAndView(HOME_VIEW);
	}
	
	
	
	@RequestMapping(value="/newSession",method=RequestMethod.GET)
	public ModelAndView handleNewSession(HttpServletRequest request,HttpServletResponse response){
		HttpSessionManager sessionManager=(HttpSessionManager) request.getAttribute(HttpSessionManager.class.getName());
		String currentAlias=sessionManager.getCurrentSessionAlias(request);
		System.out.println("CURRENT ALIAS IS "+currentAlias);
		Map<String,String> sessionIds=sessionManager.getSessionIds(request);
		
		HttpSession session=request.getSession();
		
		System.out.println("REQUESTED SESSION ID "+session.getId());
		String aliasId=sessionIds.get(currentAlias);
		System.out.println("REQUESTED CURRENT ALIAS SESSION ID "+aliasId);
		String userName=(String) session.getAttribute("USER_NAME");
		System.out.println("");
		
		for(Entry<String, String> entry:sessionManager.getSessionIds(request).entrySet()){
			System.out.println("SESSION ALIAS "+entry.getKey()+" SESSION VALUE "+entry.getValue());
		}
		
		return new ModelAndView(NEWSESSION_VIEW);
	}

	
	
	
	
}
