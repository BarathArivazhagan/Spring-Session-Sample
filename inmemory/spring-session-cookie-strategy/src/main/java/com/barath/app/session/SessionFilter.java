package com.barath.app.session;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class SessionFilter implements Filter {
	static final String DEFAULT_SESSION_ALIAS_PARAM_NAME = "_s";
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("WOASESSIONFILTER IS INITIALIZED");
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("WOASESSIONFILTER IS FILTERING");
		HttpServletRequest request=(HttpServletRequest) req;
		
				
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
