package com.barath.app;

import java.lang.invoke.MethodHandles;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.geode.cache.client.ClientRegionShortcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnablePool;
import org.springframework.data.gemfire.config.xml.GemfireConstants;
import org.springframework.session.data.gemfire.config.annotation.web.http.EnableGemFireHttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ClientCacheApplication(name="client-cache")
@EnableGemFireHttpSession(regionName="gemfire-http-sessions", maxInactiveIntervalInSeconds= 60, clientRegionShortcut = ClientRegionShortcut.PROXY)
@EnablePool(name=GemfireConstants.DEFAULT_GEMFIRE_POOL_NAME,
locatorsString= "localhost[10334]"
,subscriptionEnabled= true)
@RestController
public class Application {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private static final  DateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS Z"); 

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
		String lastAccessedTime =df.format(new  Date(session.getLastAccessedTime()));
		String creationTime= df.format(new Date(session.getCreationTime()));
		responseMap.put("LAST ACCESSED TIME ", lastAccessedTime);
		responseMap.put("CREATION TIME ",creationTime);
		if(logger.isInfoEnabled()) { 
			logger.info("session id {}",session.getId());
			logger.info("LAST ACCESSED TIME {}",lastAccessedTime); 
			logger.info("CREATION TIME {}",creationTime); 
			
		}
	
		
		return responseMap;
	}
}
