package com.barath.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.barath.app.session.EnableGemfireHttpSession;

@SpringBootApplication
@EnableGemfireHttpSession(maxInactiveIntervalInSeconds=60)
public class Application extends ServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
