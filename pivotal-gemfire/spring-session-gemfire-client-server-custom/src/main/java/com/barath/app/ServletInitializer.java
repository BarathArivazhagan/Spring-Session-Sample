package com.barath.app;

import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.InternalResourceViewResolver;



public class ServletInitializer extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
	
	/**	 enable below for lower versions
	 @Bean
	public EmbeddedServletContainerCustomizer customizer() {
	    return container -> {
	        if (container instanceof TomcatEmbeddedServletContainerFactory) {
	            TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) container;
	            tomcat.addContextCustomizers(context -> context.setCookieProcessor(new LegacyCookieProcessor()));
	        }
	    };
	} **/
	
	
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver(){
		return new InternalResourceViewResolver();
	}

}
