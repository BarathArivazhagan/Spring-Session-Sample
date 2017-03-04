package com.barath.app.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.barath.app.entities.User;
import com.barath.app.repository.UserRepository;

@Service
public class LoginService {
	
	@Autowired
	private UserRepository userRep;
	
	public boolean authenticateLogin(String emailId,String password,HttpServletRequest request){
		
		Assert.notNull(userRep, "USER REPOSITORY cannot be null");
		if(!StringUtils.isEmpty(emailId) && !StringUtils.isEmpty(password)){
			 User user=userRep.findByUserEmailId(emailId);
			 if(emailId.equals(user.getUserEmailId()) && password.equals(user.getUserPassword())){
				 request.getSession().setAttribute("USER_NAME", user.getUserName());
				 return true;
			 }
			
			
		}
		
		
		return false;
	}

}
