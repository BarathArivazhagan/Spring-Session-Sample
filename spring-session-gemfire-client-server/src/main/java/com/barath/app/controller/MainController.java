package com.barath.app.controller;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.barath.app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.ExpiringSession;
import org.springframework.session.SessionRepository;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class MainController {
	
	
	private static final Logger logger=LoggerFactory.getLogger(MainController.class);
	private static final String HOME_VIEW="/html/login.html";
	private static final String ERROR_VIEW="/html/error.html";
	private static final String NEWSESSION_VIEW="/WEB-INF/jsp/NewSession.jsp";	
	private static final String HEADER_TOKEN="x-auth-token";
	private static final String WELCOME_VIEW="/WEB-INF/jsp/welcome.jsp";	
	private static final String LOGOUT_VIEW="/html/logout.html";

	private final UserService userService;

	private final SessionRepository<ExpiringSession> sessionRepository;	
	
	public MainController(UserService userService, SessionRepository<ExpiringSession> sessionRepository) {
		super();
		this.userService = userService;
		this.sessionRepository = sessionRepository;
	}


	@RequestMapping(value="/")
	public ModelAndView homePage(){		
		
		return new ModelAndView(HOME_VIEW);
	}
	
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ModelAndView handleLogin(@RequestParam("emailId") String emailId,@RequestParam("password") String password,HttpServletRequest request,HttpServletResponse response){
		
		if(!StringUtils.isEmpty(emailId) && !StringUtils.isEmpty(password)){
			logger.info("EMAIL ID "+emailId+"   PASS WORD ID "+password);
			Assert.notNull(userService, "LOGIN SERVICE Cannot be null");
			boolean isAuthenticated=userService.authenticateLogin(emailId, password,request);
			if(isAuthenticated){
				
				return new ModelAndView(WELCOME_VIEW);
			}
		
		}
		return new ModelAndView(ERROR_VIEW);
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public ModelAndView handleLogout(HttpServletRequest request,HttpServletResponse response){
		logger.info("LOGOUT METHOD IS INVOKED");
		if(sessionRepository !=null){
			logger.info("SESSION ID TO BE DELETED IS "+request.getSession().getId());
			sessionRepository.delete(request.getSession().getId());
		}
		
		return new ModelAndView(LOGOUT_VIEW);
	}
	
	
	private String getSessionToken(){
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request.getHeader(HEADER_TOKEN);
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
	
	private Map<String, String> getResponseHeadersInfo(HttpServletResponse response) {

		Map<String, String> map = new HashMap<String, String>();
	
		Collection<String> headerNames = response.getHeaderNames();
		
		for( String entry:headerNames){
			logger.info("HEADER KEY ==> "+entry+ "   HEADER VALUE IS "+entry);
		}
		return map;
	  }
	
	
	
	@ExceptionHandler(Exception.class)
	public String handleException(Exception exception) {
		logger.info("Error "+exception.getMessage());
		return exception.getMessage();
	}
}
