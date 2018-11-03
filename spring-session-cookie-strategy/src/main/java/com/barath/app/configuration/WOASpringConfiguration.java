package com.barath.app.configuration;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.ExpiringSession;
import org.springframework.session.SessionRepository;
import org.springframework.session.web.http.CookieHttpSessionStrategy;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;
import org.springframework.session.web.http.MultiHttpSessionStrategy;
import org.springframework.session.web.http.SessionEventHttpSessionListenerAdapter;
import org.springframework.session.web.http.SessionRepositoryFilter;
import org.springframework.util.Assert;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.barath.app.session.WOASessionFilter;
import com.barath.app.session.WOASpringSessionRepository;
import com.barath.app.session.events.WOASessionEventListener;

@Configuration
public class WOASpringConfiguration {
	
	private CookieHttpSessionStrategy cookietHttpSessionStrategy=new CookieHttpSessionStrategy();
	
	private MultiHttpSessionStrategy httpSessionStrategy = this.cookietHttpSessionStrategy;
	private List<HttpSessionListener> httpSessionListeners=getHttpSessionListeners();
	
	
	private ServletContext servletContext;
	
	
	public  List<HttpSessionListener> getHttpSessionListeners(){
		
		List<HttpSessionListener> httpSessionListeners=new ArrayList<HttpSessionListener>();
		httpSessionListeners.add( new HttpSessionListener() {
			
			@Override
			public void sessionDestroyed(HttpSessionEvent se) {
				System.out.println("HELLOE");
				
			}
			
			@Override
			public void sessionCreated(HttpSessionEvent se) {
				System.out.println("DELETED");
				
			}
		});
		return httpSessionListeners;
		
	}
	
	@Bean
	public WOASessionEventListener WOASessionEventListener(){
		return new WOASessionEventListener();
	}
	
	
	
	@Bean
	public SessionEventHttpSessionListenerAdapter sessionEventHttpSessionListenerAdapter(){
		//SessionEventHttpSessionListenerAdapter sessionEvent=new SessionEventHttpSessionListenerAdapter();
		return new SessionEventHttpSessionListenerAdapter(this.httpSessionListeners);
	}
	
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
			System.out.println("MULTIHTTPSESSION STRATEGY IS USED");
			sessionRepositoryFilter.setHttpSessionStrategy(
					(MultiHttpSessionStrategy) this.httpSessionStrategy);
		}
		else {
			sessionRepositoryFilter.setHttpSessionStrategy(this.httpSessionStrategy);
		}
		return sessionRepositoryFilter;
	}
	
	@Bean
	public SessionRepository<ExpiringSession> sessionRepository(){
		return new WOASpringSessionRepository(300);
	}
	
	/*@Bean
	public FilterRegistrationBean sessionFilter(){
		FilterRegistrationBean sessionFilter=new FilterRegistrationBean();
		WOASessionFilter woaSessionFilter=new WOASessionFilter();
		sessionFilter.setFilter(woaSessionFilter);
		sessionFilter.addUrlPatterns("/newSession");
		sessionFilter.setOrder(2);
		return sessionFilter;
		
	}
	*/
	@Autowired(required = false)
	public void setHttpSessionStrategy(MultiHttpSessionStrategy httpSessionStrategy) {
		this.httpSessionStrategy = httpSessionStrategy;
		//this.cookietHttpSessionStrategy.setSessionAliasParamName("WOASESSION");
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
		registration.addMappingForUrlPatterns(dispatcherTypes, false,new String[]{"/login","/logout","/newSession"});
	}
	
	
	protected EnumSet<DispatcherType> getSessionDispactherTypes(){
		return EnumSet.of(DispatcherType.REQUEST,DispatcherType.ERROR,DispatcherType.ASYNC);
	}
	
	protected boolean isAsyncSesssionSupported(){
		return true;
	}
	
	
}
