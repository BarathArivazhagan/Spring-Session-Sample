package com.barath.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.SessionRepository;
import org.springframework.session.web.http.HttpSessionManager;
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
	
	
	private static final String WELCOME_VIEW="/WEB-INF/jsp/welcome.jsp";
	private static final String ERROR_VIEW="/WEB-INF/jsp/error.jsp";
	private static final String LOGOUT_VIEW="/html/logout.html";
	
	private static final String DEFAULT_SESSION_ALIAS_PARAM_NAME = "_s";
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private SessionRepository sessionRep;
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ModelAndView handleLogin(@RequestParam("emailId") String emailId,@RequestParam("password") String password,HttpServletRequest request){
		
		if(!StringUtils.isEmpty(emailId) && !StringUtils.isEmpty(password)){
			System.out.println("EMAIL ID "+emailId+"   PASS WORD ID "+password);
			Assert.notNull(loginService, "LOGIN SERVICE Cannot be null");
			boolean isAuthenticated=loginService.authenticateLogin(emailId, password,request);
			if(isAuthenticated){
			
				return new ModelAndView(WELCOME_VIEW);
			}
		
		}
		return new ModelAndView(ERROR_VIEW);
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public ModelAndView handleLogout(HttpServletRequest request,HttpServletResponse response){
		System.out.println("LOGOUT METHOD IS INVOKED");
		if(sessionRep !=null){
			System.out.println("SESSION ID TO BE DELETED IS "+request.getSession().getId());
			sessionRep.delete(request.getSession().getId());
		}
		
		return new ModelAndView(LOGOUT_VIEW);
	}
}
