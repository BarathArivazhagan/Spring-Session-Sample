package com.barath.app.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.barath.app.entities.User;
import com.barath.app.entities.User.UserGender;

public class UserDetails {
	
	
	@Autowired 
	private UserService userService;
	
	public void addUsers(){
		
		
	User user1=new User(10000, "barath", 23, UserGender.MALE, "barath@citi.com", "barath");
	User user2=new User(10001, "ananthan", 32, UserGender.MALE, "ananthan@citi.com", "ananthan");
	User user3=new User(10002, "pugazh", 28, UserGender.MALE, "pugazh@citi.com", "pugazh");
	User user4=new User(10003, "ravie", 32, UserGender.MALE, "ravie@citi.com", "ravie");
	User user5=new User(10004, "reena", 30, UserGender.FEMALE, "reena@citi.com", "reena");
	User user6=new User(10005, "praveen", 28, UserGender.MALE, "praveen@citi.com", "praveen");
	User user7=new User(10006, "preethi", 26, UserGender.FEMALE, "preethi@citi.com", "preethi");
	User user8=new User(10007, "prasad", 28, UserGender.MALE, "prasad@citi.com", "prasad");
	User user9=new User(10008, "geethika", 25, UserGender.FEMALE, "geethika@citi.com", "geethika");
	User user10=new User(10009, "nissan", 23, UserGender.MALE, "nissan@citi.com", "nissan");
	User user11=new User(10010, "ganesan", 35, UserGender.MALE, "ganesan@citi.com", "ganesan");
	List<User> userList=Arrays.asList(user1,user2,user3,user4,user5,user5,user6,user7,user8,user9,user10,user11);
	
	Assert.notEmpty(userList,"user list cannot be empty");
	Assert.notNull(userService,"User service cannot be empty");
	
	userService.addUsers(userList);
		
		
	}

}
