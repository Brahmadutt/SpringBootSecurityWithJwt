package com.wow.security.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wow.security.exceptions.AuthenticationMethodNotSupportedException;
import com.wow.security.request.LoginRequest;
import com.wow.security.util.HttpServletUtil;

/**
 * 
 * @author Brahmadutt S
 *
 * Mar 6, 2020
 */

public class LoginProcessingFilter extends AbstractAuthenticationProcessingFilter {
	private static Logger logger = LoggerFactory.getLogger(LoginProcessingFilter.class);

	private final AuthenticationSuccessHandler successHandler;
	private final AuthenticationFailureHandler failureHandler;

	private final ObjectMapper objectMapper;

	public LoginProcessingFilter(String defaultProcessUrl, AuthenticationSuccessHandler successHandler,
			AuthenticationFailureHandler failureHandler, ObjectMapper mapper) {
		super(defaultProcessUrl);
		this.successHandler = successHandler;
		this.failureHandler = failureHandler;
		this.objectMapper = mapper;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		if (!HttpMethod.POST.name().equals(request.getMethod()) || !HttpServletUtil.isAjax(request)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Authentication method not supported. Request method: {}", request.getMethod());
			}
			throw new AuthenticationMethodNotSupportedException("Authentication method not supported");
		}
		LoginRequest loginRequest = null;
		Map<String, String[]> mp = request.getParameterMap();
		JSONObject json = new JSONObject();
		try {
			for (Map.Entry<String, String[]> entry : mp.entrySet()) {
				json.put(entry.getKey(), entry.getValue());
			}

			//System.out.printf("JSON: %s", json.toString(2));
			loginRequest = objectMapper.readValue(json.toString(2).replace("[", "").replace("]", ""),
					LoginRequest.class);
		} catch (Exception e) {
			System.out.println(e);
		}

		if (StringUtils.isBlank(loginRequest.getUsername()) || StringUtils.isBlank(loginRequest.getPassword())) {
			throw new AuthenticationServiceException("Username or Password not provided");
		}

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
				loginRequest.getPassword());

		return this.getAuthenticationManager().authenticate(token);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		successHandler.onAuthenticationSuccess(request, response, authResult);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		SecurityContextHolder.clearContext();
		failureHandler.onAuthenticationFailure(request, response, failed);
	}
}
