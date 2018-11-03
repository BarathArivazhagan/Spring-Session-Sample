package com.barath.app.session;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.session.ExpiringSession;
import org.springframework.session.SessionRepository;

import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;
import org.springframework.session.web.http.MultiHttpSessionStrategy;

import org.springframework.session.web.http.SessionRepositoryFilter;
import org.springframework.util.Assert;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.gemstone.gemfire.cache.client.ClientCache;

import javax.servlet.Filter;

@Configuration
@EnableGemfireRepositories
public class GemfireSessionConfiguration {
	
	
	private static final Logger logger=LoggerFactory.getLogger(GemfireSessionConfiguration.class);
	private static final String URL_MAPPING_SEPARATOR=",";
	private HeaderHttpSessionStrategy defaultHttpSessionStrategy=new HeaderHttpSessionStrategy();
	private HttpSessionStrategy httpSessionStrategy = this.defaultHttpSessionStrategy;
	private List<HttpSessionListener> httpSessionListeners=new ArrayList<>();
	
	private ServletContext servletContext;
	
	@Value("${spring.session.urlMappings:/login}")
	private String urlMappings;
	
	@Value("${spring.session.maxInactiveIntervalInSeconds:1800}")
	private int maxInactiveIntervalInSeconds;
	
	
	
	@Autowired
	public void setServletContext(ServletContext servletContext){
		this.servletContext=servletContext;	
		insertSessionRepositoryFilter(servletContext);
	}

	
	@Bean
	public <S extends ExpiringSession> SessionRepositoryFilter<? extends ExpiringSession> springSessionRepositoryFilter(
			SessionRepository<S> sessionRepository) {
		SessionRepositoryFilter<S> sessionRepositoryFilter = new SessionRepositoryFilter<S>(
				sessionRepository);
		
		if (this.httpSessionStrategy instanceof MultiHttpSessionStrategy) {
			sessionRepositoryFilter.setHttpSessionStrategy(
					(MultiHttpSessionStrategy) this.httpSessionStrategy);
		}
		else {
			sessionRepositoryFilter.setHttpSessionStrategy(this.httpSessionStrategy);
		}
		return sessionRepositoryFilter;
	}
	
	@Bean
	public SessionRepository<ExpiringSession> sessionRepository(ClientCache clientCache,ApplicationEventPublisher eventPublisher){
		
		logger.info("maxInactiveInterval "+maxInactiveIntervalInSeconds);
		return new GemfireSessionRepository(maxInactiveIntervalInSeconds,clientCache,eventPublisher);
	}
	
	@Autowired(required = false)
	public void setHttpSessionStrategy(HttpSessionStrategy httpSessionStrategy) {
		this.httpSessionStrategy = httpSessionStrategy;
	}
	
	private void insertSessionRepositoryFilter(ServletContext serlvetContext){
		String filterName="springSessionRepositoryFilter";
		DelegatingFilterProxy springSessionRepositoryFilter=new DelegatingFilterProxy(filterName);
		
		registerFilter(servletContext,true,filterName,springSessionRepositoryFilter);
	}
	
	
	private void registerFilter(ServletContext servletContext,boolean insertBeforeOtherFilters,String filterName,Filter filter){
		FilterRegistration.Dynamic registration=servletContext.addFilter(filterName, filter);
		Assert.notNull(registration, "Filter registration cannot be null");
		registration.setAsyncSupported(isAsyncSesssionSupported());
		EnumSet<DispatcherType> dispatcherTypes=getSessionDispactherTypes();
		registration.addMappingForUrlPatterns(dispatcherTypes, false,urlMappings.split(URL_MAPPING_SEPARATOR));
	}
	
	
	protected EnumSet<DispatcherType> getSessionDispactherTypes(){
		return EnumSet.of(DispatcherType.REQUEST,DispatcherType.ERROR,DispatcherType.ASYNC);
	}
	
	protected boolean isAsyncSesssionSupported(){
		return true;
	}
	
	
	

}
