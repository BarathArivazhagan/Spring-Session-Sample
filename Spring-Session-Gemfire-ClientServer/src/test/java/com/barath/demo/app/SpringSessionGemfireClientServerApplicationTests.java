package com.barath.demo.app;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponentsBuilder;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=SpringSessionGemfireClientServerApplication.class,webEnvironment=WebEnvironment.DEFINED_PORT)
public class SpringSessionGemfireClientServerApplicationTests {
	
	private static final String LOGIN_ENDPOINT="http://localhost:8083/APP/login";
	private static final String TEST_SERVICE_ENDPOINT="http://localhost:8083/APP/service";
	private static final String SESSION_TOKEN="x-auth-token";
	private String sessionToken;
		
		
	


	@Autowired
	private WebApplicationContext webContext;
	
	@Autowired(required=false)
	private MockMvc mockMvc;
	
	@Autowired(required=false)
	private RestTemplate restTemplate;
	

	@Before
	public void contextLoads() {
		if(mockMvc==null){
			mockMvc=MockMvcBuilders.webAppContextSetup(webContext).build();
		}
		this.restTemplate= (restTemplate != null )?  this.restTemplate : new RestTemplate();
	}
	
	
	public ResponseEntity<String> performLogin() throws Exception{		
	
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(LOGIN_ENDPOINT)
		        .queryParam("emailId", "barath@test.com")
		        .queryParam("password", "barath");
		ResponseEntity<String> response=restTemplate.exchange(builder.build().toString(),HttpMethod.POST, null,String.class);
		System.out.println("output "+response.getHeaders());	
		
		return  response;
	
	}
	
	
	@Test	
	public void testService() throws Exception{
		
		ResponseEntity<String> loginResponse=performLogin();
		String sessionToken=loginResponse.getHeaders().getFirst(SESSION_TOKEN);
		HttpHeaders headers=new HttpHeaders();		
		System.out.println("TEST SERVICE "+sessionToken);
		headers.set(SESSION_TOKEN, sessionToken);
		HttpEntity<String> httpEntity=new HttpEntity<String>(headers);
		ResponseEntity<String> response=restTemplate.exchange(TEST_SERVICE_ENDPOINT,HttpMethod.GET, httpEntity,String.class);
		System.out.println("output "+response.getHeaders());
		HttpHeaders responseHeaders=response.getHeaders();		;
		System.out.println("Session "+responseHeaders.getFirst(SESSION_TOKEN));	
		System.out.println("Response output "+response.getBody());
		assertEquals(sessionToken, responseHeaders.getFirst(SESSION_TOKEN));
	}
	
	
	//@Test
	public void testServiceFails() throws Exception{		
		HttpHeaders headers=new HttpHeaders();		
		//headers.set(SESSION_TOKEN, this.getSessionToken());
		ResponseEntity<String> loginResponse=performLogin();
		String sessionToken=loginResponse.getHeaders().getFirst(SESSION_TOKEN);
		HttpEntity<String> httpEntity=new HttpEntity<>(headers);
		ResponseEntity<String> response=restTemplate.exchange(TEST_SERVICE_ENDPOINT,HttpMethod.GET, httpEntity,String.class);
		System.out.println("output "+response.getHeaders());
		HttpHeaders responseHeaders=response.getHeaders();
		
		System.out.println("Session "+responseHeaders.getFirst(SESSION_TOKEN));	
	
		assertNotEquals(sessionToken,responseHeaders.getFirst(SESSION_TOKEN));
	}
	
	
	

}
