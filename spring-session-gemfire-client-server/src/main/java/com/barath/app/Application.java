package com.barath.app;

import com.barath.app.session.EnableGemfireHttpSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableGemfireHttpSession(maxInactiveIntervalInSeconds=60)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
