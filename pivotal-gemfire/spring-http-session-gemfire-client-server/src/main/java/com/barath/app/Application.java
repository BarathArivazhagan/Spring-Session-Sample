package com.barath.app;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.session.Session;
import org.springframework.session.data.gemfire.config.annotation.web.http.EnableGemFireHttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableGemFireHttpSession(maxInactiveIntervalInSeconds=10, regionName="gemfire-sessions")
@RestController
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@GetMapping
	public String home() {
		return "hello home";
	}
	
	@PostMapping("/session")
	public Map<String,Object> session(HttpSession session) {
		Map<String,Object> responseMap = new HashMap<>();
		responseMap.put("SESSION ID ", session.getId());
		responseMap.put("LAST ACCESSED TIME ", session.getLastAccessedTime());
		responseMap.put("CREATION TIME ", session.getCreationTime());
		if(session instanceof Session) {
			Session ses = (Session) session;
			responseMap.put("CREATION TIME", ses.getCreationTime());
		}
		System.out.println("session id "+session.getId());
		return responseMap;
	}
}
