package com.barath.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.barath.app.entities.User;
import com.barath.app.repository.UserRepository;

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

}
