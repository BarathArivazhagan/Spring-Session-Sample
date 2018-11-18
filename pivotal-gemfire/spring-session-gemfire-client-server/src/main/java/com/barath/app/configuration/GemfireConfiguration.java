package com.barath.app.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.client.ClientRegionShortcut;

@Configuration
public class GemfireConfiguration {
	

	private static final String REGION_NAME="session";	
	
	@Value("${gemfire.locator.host:localhost}")
	private String gemfireHost;
	
	@Value("${gemfire.locator.port:10334}")
	private int gemfirePort;
	
	@Bean
	public ClientCache clientCache(){
		
		ClientCache c = new ClientCacheFactory().addPoolLocator(gemfireHost, gemfirePort).create();
		
		 Region<Object, Object> region = c.createClientRegionFactory(ClientRegionShortcut.PROXY).create(REGION_NAME);
		
		return c;
	}

}
