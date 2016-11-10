package com.barath.demo.app;


import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;

import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class UserDetails {
	
	
	@Autowired 
	private UserService userService;
	
	
	
	public void addUsers(){
		System.out.println("calling the post contrsut adding the users ");
		
	User user1=new User(10000, "barath", 23, UserGender.MALE, "barath@test.com", "barath");
	User user2=new User(10001, "arivu", 58, UserGender.MALE, "arivu@test.com", "arivu");
	User user3=new User(10002, "steve", 28, UserGender.MALE, "steve@test.com", "steve");
	User user4=new User(10003, "kavi", 32, UserGender.FEMALE, "kavi@test.com", "kavi");
	User user5=new User(10004, "reena", 30, UserGender.FEMALE, "reena@test.com", "reena");
	User user6=new User(10005, "praveen", 28, UserGender.MALE, "praveen@test.com", "praveen");
	User user7=new User(10006, "pari", 26, UserGender.FEMALE, "pari@test.com", "pari");
	User user8=new User(10007, "prasad", 28, UserGender.MALE, "prasad@test.com", "prasad");
	User user9=new User(10008, "geethika", 25, UserGender.FEMALE, "geethika@test.com", "geethika");
	User user10=new User(10009, "nissan", 23, UserGender.MALE, "nissan@test.com", "nissan");
	User user11=new User(10010, "ganesan", 35, UserGender.MALE, "ganesan@test.com", "ganesan");
	List<User> userList=Arrays.asList(user1,user2,user3,user4,user5,user5,user6,user7,user8,user9,user10,user11);
	
	Assert.notEmpty(userList,"user list cannot be empty");
	Assert.notNull(userService,"User service cannot be empty");
	
	userService.addUsers(userList);
		
		
	}
	
	

	

}


interface UserRepository extends JpaRepository<User, Long> {

	
	public User findByUserEmailId(String emailId);
	
}
