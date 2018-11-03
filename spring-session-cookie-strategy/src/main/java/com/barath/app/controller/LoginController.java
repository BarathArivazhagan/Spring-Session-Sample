package com.barath.app.controller;

import java.lang.invoke.MethodHandles;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.session.ExpiringSession;
import org.springframework.session.SessionRepository;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.barath.app.service.LoginService;



@RestController
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private static final String WELCOME_VIEW="/WEB-INF/jsp/welcome.jsp";
	private static final String ERROR_VIEW="/WEB-INF/jsp/error.jsp";
	private static final String LOGOUT_VIEW="/html/logout.html";
	
	private static final String DEFAULT_SESSION_ALIAS_PARAM_NAME = "_s";
	
	private final LoginService loginService;
	
	private final SessionRepository<ExpiringSession> sessionRepository;

	public LoginController(LoginService loginService, SessionRepository<ExpiringSession> sessionRepository) {
		this.loginService = loginService;
		this.sessionRepository = sessionRepository;
	}

	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ModelAndView handleLogin(@RequestParam("emailId") String emailId,@RequestParam("password") String password,HttpServletRequest request){
		
		if(!StringUtils.isEmpty(emailId) && !StringUtils.isEmpty(password)){
			logger.info("EMAIL ID "+emailId+"   PASS WORD ID "+password);			
			boolean isAuthenticated=loginService.authenticateLogin(emailId, password,request);
			if(isAuthenticated){
			
				return new ModelAndView(WELCOME_VIEW);
			}
		
		}
		return new ModelAndView(ERROR_VIEW);
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public ModelAndView handleLogout(HttpServletRequest request,HttpServletResponse response){
		logger.info("LOGOUT METHOD IS INVOKED");
		logger.info("SESSION ID TO BE DELETED IS "+request.getSession().getId());
		sessionRepository.delete(request.getSession().getId());
		return new ModelAndView(LOGOUT_VIEW);
	}
}
