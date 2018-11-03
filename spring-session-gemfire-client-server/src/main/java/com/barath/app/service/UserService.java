package com.barath.app.service;


import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.barath.app.entity.User;
import com.barath.app.repository.UserRepository;


@Service
public class UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private final UserRepository userRepository;
	
	
	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}


	public User addUser(User user){
		return userRepository.save(user);
	}
	
	
	public List<User> addUsers(List<User> users){
		return this.userRepository.save(users);
	}
	
	public boolean authenticateLogin(String emailId,String password,HttpServletRequest request){

			 User user=userRepository.findByUserEmailId(emailId);
			 Assert.notNull(user,"user cannot be empty means not found, relogin");
			 if(emailId.equals(user.getUserEmailId()) && password.equals(user.getUserPassword())){
				 request.getSession().setAttribute("USER_NAME", user.getUserName());
				 return true;
			 }

		return false;
	}


	@PostConstruct
	public void init(){

		User user1=new User(10000, "barath", 23, User.UserGender.MALE, "barath@test.com", "barath");
		User user2=new User(10001, "arivu", 58, User.UserGender.MALE, "arivu@test.com", "arivu");
		User user3=new User(10002, "steve", 28, User.UserGender.MALE, "steve@test.com", "steve");
		User user4=new User(10003, "kavi", 32, User.UserGender.FEMALE, "kavi@test.com", "kavi");
		User user5=new User(10004, "reena", 30, User.UserGender.FEMALE, "reena@test.com", "reena");
		User user6=new User(10005, "praveen", 28, User.UserGender.MALE, "praveen@test.com", "praveen");
		User user7=new User(10006, "pari", 26, User.UserGender.FEMALE, "pari@test.com", "pari");
		User user8=new User(10007, "prasad", 28, User.UserGender.MALE, "prasad@test.com", "prasad");
		User user9=new User(10008, "geethika", 25, User.UserGender.FEMALE, "geethika@test.com", "geethika");
		User user10=new User(10009, "nissan", 23, User.UserGender.MALE, "nissan@test.com", "nissan");
		User user11=new User(10010, "ganesan", 35, User.UserGender.MALE, "ganesan@test.com", "ganesan");
		List<User> userList= Arrays.asList(user1,user2,user3,user4,user5,user5,user6,user7,user8,user9,user10,user11);
		this.addUsers(userList);
	}

	

}
