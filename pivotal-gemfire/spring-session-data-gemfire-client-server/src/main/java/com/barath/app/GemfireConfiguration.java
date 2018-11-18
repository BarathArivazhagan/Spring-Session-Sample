package com.barath.app;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.gemfire.client.PoolFactoryBean;
import org.springframework.data.gemfire.config.xml.GemfireConstants;
import org.springframework.data.gemfire.support.ConnectionEndpoint;


/**
 * 
 * This is custom implementation class if other than default
 * gemfire pool is required.
 * 
 * Also @see AddPoolsConfiguration
 * 		@see ClientConfigurerConfiguration
 * 
 * @author barath
 *
 */
@Profile("custom")
@Configuration
public class GemfireConfiguration {
	
	@Bean(name = GemfireConstants.DEFAULT_GEMFIRE_POOL_NAME)
    public PoolFactoryBean gemfirePool(@Value("${gemfire.locator.host:localhost}") String host,
        @Value("${gemfire.locator.port:10334}") int port) {
      PoolFactoryBean gemfirePool = new PoolFactoryBean();
      gemfirePool.setKeepAlive(false);
      gemfirePool.setMinConnections(1);
      gemfirePool.setPingInterval(TimeUnit.SECONDS.toMillis(15));
      gemfirePool.setReadTimeout(((Number)TimeUnit.SECONDS.toMillis(20)).intValue());
      gemfirePool.setRetryAttempts(1);
      gemfirePool.setSubscriptionEnabled(true);
      gemfirePool.setThreadLocalConnections(false);

      gemfirePool.addLocators(new ConnectionEndpoint(host, port));

      return gemfirePool;
    }
	

  

  

}
