package com.barath.demo.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.barath.demo.app.session.EnableGemfireHttpSession;

@SpringBootApplication
@EnableGemfireHttpSession(maxInactiveIntervalInSeconds=60)
public class SpringSessionGemfireClientServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSessionGemfireClientServerApplication.class, args);
	}
}
