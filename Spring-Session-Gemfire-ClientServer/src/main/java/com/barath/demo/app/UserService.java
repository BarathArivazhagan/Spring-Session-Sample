package com.barath.demo.app;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.barath.demo.app.User;
import com.barath.demo.app.UserRepository;



@Service
public class UserService {
	
	
	@Autowired
	private UserRepository userRep;
	
	
	public void addUser(User user){
		Assert.notNull(userRep, "User repository cannot be null");
		userRep.save(user);
	}
	
	
	public void addUsers(List<User> userList){
		Assert.notNull(userRep, "User repository cannot be null");
		for(User user:userList){
			userRep.save(user);			
		}
	}
	
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
