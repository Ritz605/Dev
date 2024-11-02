package com.Stack.Overflow.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.weaver.loadtime.Options;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCorsFilter implements Filter{

	private String clientAppURL = "http://localhost:4200/*";
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse response2 = (HttpServletResponse) response;
		HttpServletRequest request2 = (HttpServletRequest) request;
		Map<String, String> map = new HashMap<>();
		
		String originHeader = request2.getHeader("origin");
		response2.setHeader("Access-Control-Allow-Origin", originHeader);
		response2.setHeader("Access-Control-Allow-Methods", "POST, GET ,PUT, DELETE, OPTIONS");
		response2.setHeader("Access-Control-Max-Age", "3600");
		response2.setHeader("Access-Control-Allow-Headers", "*");
		
		if("OPTIONS".equalsIgnoreCase(request2.getMethod())) {
			response2.setStatus(HttpServletResponse.SC_OK);
		}else {
			chain.doFilter(request, response);
		}
	}

	

}
