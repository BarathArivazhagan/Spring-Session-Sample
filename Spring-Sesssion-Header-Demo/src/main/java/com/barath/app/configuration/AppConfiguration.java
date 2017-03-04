package com.barath.app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.barath.app.service.UserDetails;

@Configuration
public class AppConfiguration {

	@Bean(initMethod="addUsers")
	public UserDetails userDetails(){
		return new UserDetails();
	}
	
	
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver(){
		return new InternalResourceViewResolver();
	}
}
