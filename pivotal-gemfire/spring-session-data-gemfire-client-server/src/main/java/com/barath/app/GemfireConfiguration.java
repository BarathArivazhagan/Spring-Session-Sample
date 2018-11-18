package com.barath.app;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.client.ClientCacheFactoryBean;
import org.springframework.data.gemfire.client.PoolFactoryBean;
import org.springframework.data.gemfire.config.xml.GemfireConstants;
import org.springframework.data.gemfire.support.ConnectionEndpoint;

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
	

    public  Properties gemfireProperties() {
      Properties gemfireProperties = new Properties();
      gemfireProperties.setProperty("log-level", logLevel());
      return gemfireProperties;
    }

    public String logLevel() {
      return System.getProperty("gemfire.log-level", "config");
    }

    @Bean
    public ClientCacheFactoryBean gemfireCache() {
    	
      ClientCacheFactoryBean gemfireCache = new ClientCacheFactoryBean();
      gemfireCache.setClose(true);
      gemfireCache.setProperties(gemfireProperties());
      return gemfireCache;
    }

}
