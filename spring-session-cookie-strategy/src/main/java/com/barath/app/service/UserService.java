package com.barath.app.service;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.barath.app.entities.User;
import com.barath.app.repository.UserRepository;

import javax.annotation.PostConstruct;

@Service
public class UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void addUser(User user){
		userRepository.save(user);
	}
	
	
	public void addUsers(List<User> userList){
		this.userRepository.save(userList);
	}

	@PostConstruct
	public void init(){
		User user1=new User(10000, "barath", 23, User.UserGender.MALE, "barath@demo.com", "barath");
		User user2=new User(10001, "ananthan", 32, User.UserGender.MALE, "ananthan@demo.com", "ananthan");
		User user3=new User(10002, "pugazh", 28, User.UserGender.MALE, "pugazh@demo.com", "pugazh");
		User user4=new User(10003, "ravie", 32, User.UserGender.MALE, "ravie@demo.com", "ravie");
		User user5=new User(10004, "reena", 30, User.UserGender.FEMALE, "reena@demo.com", "reena");
		User user6=new User(10005, "praveen", 28, User.UserGender.MALE, "praveen@demo.com", "praveen");
		User user7=new User(10006, "preethi", 26, User.UserGender.FEMALE, "preethi@demo.com", "preethi");
		User user8=new User(10007, "prasad", 28, User.UserGender.MALE, "prasad@demo.com", "prasad");
		User user9=new User(10008, "geethika", 25, User.UserGender.FEMALE, "geethika@demo.com", "geethika");
		User user10=new User(10009, "nissan", 23, User.UserGender.MALE, "nissan@demo.com", "nissan");
		User user11=new User(10010, "ganesan", 35, User.UserGender.MALE, "ganesan@demo.com", "ganesan");
		List<User> userList= Arrays.asList(user1,user2,user3,user4,user5,user5,user6,user7,user8,user9,user10,user11);
		this.addUsers(userList);

	}

}
