package com.barath.app.service;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.barath.app.entities.User;
import com.barath.app.repository.UserRepository;

import java.lang.invoke.MethodHandles;

@Service
public class LoginService {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private final UserRepository userRepository;

	public LoginService(UserRepository userRepository) {
		this.userRepository = userRepository;
		Assert.notNull(userRepository, "user repository cannot be null");
	}

	public boolean authenticateLogin(String emailId, String password, HttpServletRequest request){
		

		if(!StringUtils.isEmpty(emailId) && !StringUtils.isEmpty(password)){
			 User user=userRepository.findByUserEmailId(emailId);
			 if(emailId.equals(user.getUserEmailId()) && password.equals(user.getUserPassword())){
				 request.getSession().setAttribute("USER_NAME", user.getUserName());
				 return true;
			 }
			
		}
		
		
		return false;
	}

}
