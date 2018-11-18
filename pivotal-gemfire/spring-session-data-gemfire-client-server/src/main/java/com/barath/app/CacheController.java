package com.barath.app;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.gemfire.GemfireOperations;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheController {
	
	
	private static final String SELECT_QUERY_SESSIONS="SELECT * FROM /gemfire-http-sessions.keySet";
	private final GemfireOperations gemfireOperations;	
	
	
	public CacheController(GemfireOperations gemfireOperations) {
		super();
		this.gemfireOperations = gemfireOperations;
	}





	@GetMapping("/sessions")
	public List<String> getSessionsFromGemfire(){
		
		return gemfireOperations.query(SELECT_QUERY_SESSIONS)
			.stream()
			.map(Objects::toString)
			.collect(Collectors.toList());
	}
	
	@DeleteMapping("/sessions/remove")
	public void removeSessions() {
		
		this.gemfireOperations
			.query(SELECT_QUERY_SESSIONS)
			.stream()
			.map(Objects::toString)
			.collect(Collectors.toList())
			.stream()
			.forEach( key ->{
				
				System.out.println("key "+key);
				this.gemfireOperations.remove(key);
			});
			
	}
}
