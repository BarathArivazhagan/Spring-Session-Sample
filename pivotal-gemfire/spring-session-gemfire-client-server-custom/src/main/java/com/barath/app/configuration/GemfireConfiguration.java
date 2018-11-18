package com.barath.app.configuration;

import java.lang.invoke.MethodHandles;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;


@Configuration
public class GemfireConfiguration {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private static final String DEFAULT_REGION_NAME="gemfire-session";	
	
	@Value("${gemfire.locator.host:localhost}")
	private String gemfireHost;
	
	@Value("${gemfire.locator.port:10334}")
	private int gemfirePort;
	
	@Value("${gemfire.session.region}")
	private String sessionRegionName;
	
	@Bean
	public ClientCache clientCache(){
		
		if(logger.isInfoEnabled()) {
			logger.info(" configuring client cache");
		}
		ClientCache c = new ClientCacheFactory().addPoolLocator(gemfireHost, gemfirePort).create();
		
		this.sessionRegionName = StringUtils.isEmpty(sessionRegionName) ? DEFAULT_REGION_NAME : sessionRegionName;
		c.createClientRegionFactory(ClientRegionShortcut.PROXY).create( sessionRegionName);
		return c;
	}

}
