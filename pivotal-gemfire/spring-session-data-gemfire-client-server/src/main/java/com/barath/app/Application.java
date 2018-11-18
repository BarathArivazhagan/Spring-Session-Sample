package com.barath.app;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.geode.cache.client.ClientRegionShortcut;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnablePool;
import org.springframework.data.gemfire.config.xml.GemfireConstants;
import org.springframework.session.Session;
import org.springframework.session.data.gemfire.config.annotation.web.http.EnableGemFireHttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ClientCacheApplication(name="client-cache")
@EnableGemFireHttpSession(regionName="gemfire-http-sessions", maxInactiveIntervalInSeconds= 10, clientRegionShortcut = ClientRegionShortcut.PROXY)
@EnablePool(name=GemfireConstants.DEFAULT_GEMFIRE_POOL_NAME,
locatorsString= "localhost[10334]"
,subscriptionEnabled= true)
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
