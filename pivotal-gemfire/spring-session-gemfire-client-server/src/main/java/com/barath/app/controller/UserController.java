package com.barath.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barath.app.entity.User;
import com.barath.app.service.UserService;

@RestController
@RequestMapping(value="/users",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController {
	
	private static final Logger logger=LoggerFactory.getLogger(TestSessionController.class);
	private final UserService userService;
	
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@GetMapping
	public List<User> users(){
		
		if(logger.isInfoEnabled()) { logger.info("finding all the users"); }
		return this.userService.getUsers();
	}
	
	

}
