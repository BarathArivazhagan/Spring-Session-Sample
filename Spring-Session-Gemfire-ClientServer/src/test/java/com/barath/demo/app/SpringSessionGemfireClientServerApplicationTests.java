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
	private String sessionToken=null;
		
	


	@Autowired
	private WebApplicationContext webContext;
	
	@Autowired(required=false)
	private MockMvc mockMvc;
	
	@Autowired
	private TestRestTemplate restTemplate;
	

	@Before
	public void contextLoads() {
		if(mockMvc==null){
			mockMvc=MockMvcBuilders.webAppContextSetup(webContext).build();
		}
	}
	
	@Test
	public void performLogin() throws Exception{		
	
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(LOGIN_ENDPOINT)
		        .queryParam("emailId", "barath@test.com")
		        .queryParam("password", "barath");
		ResponseEntity<String> response=restTemplate.exchange(builder.build().toString(),HttpMethod.POST, null,String.class);
		System.out.println("output "+response.getHeaders());
		System.out.println("Session "+response.getHeaders().getValuesAsList(SESSION_TOKEN).get(0));
		this.setSessionToken(response.getHeaders().getValuesAsList(SESSION_TOKEN).get(0));
	}
	
	
	@Test
	public void testService(){
		
		HttpHeaders headers=new HttpHeaders();		
		System.out.println("TEST SERVICE "+this.getSessionToken());
		headers.set(SESSION_TOKEN, this.getSessionToken());
		HttpEntity<String> httpEntity=new HttpEntity<String>(headers);
		ResponseEntity<String> response=restTemplate.exchange(TEST_SERVICE_ENDPOINT,HttpMethod.GET, httpEntity,String.class);
		System.out.println("output "+response.getHeaders());
		System.out.println("Session "+response.getHeaders().getValuesAsList(SESSION_TOKEN).get(0));		
		assertEquals(this.getSessionToken(), response.getHeaders().getValuesAsList(SESSION_TOKEN).get(0));
	}
	
	
	@Test
	public void testServiceFails(){		
		HttpHeaders headers=new HttpHeaders();		
		//headers.set(SESSION_TOKEN, this.getSessionToken());
		HttpEntity<String> httpEntity=new HttpEntity<>(headers);
		ResponseEntity<String> response=restTemplate.exchange(TEST_SERVICE_ENDPOINT,HttpMethod.GET, httpEntity,String.class);
		System.out.println("output "+response.getHeaders());
		System.out.println("Session "+response.getHeaders().getValuesAsList(SESSION_TOKEN).get(0));
		assertNotEquals(this.getSessionToken(),response.getHeaders().getValuesAsList(SESSION_TOKEN).get(0));
	}
	
	
	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}
	

}
