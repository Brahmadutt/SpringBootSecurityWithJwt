package com.wow.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.wow.security.request.LoginRequest;
import com.wow.security.util.HttpServletUtil;

/**
 * 
 * @author Brahmadutt S
 *
 * Mar 6, 2020
 */

public class ApiUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		if (!"POST".equals(request.getMethod())) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		try {
			if (StringUtils.hasText(request.getHeader("Content-Type"))
					&& request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE)) {
				LoginRequest loginRequest = HttpServletUtil.readRequestData(request, LoginRequest.class);
				String password = loginRequest.getPassword();
				UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
						loginRequest.getUsername(), password);
				setDetails(request, authRequest);
				// API key to passed to success handler
				request.setAttribute("apiKey", loginRequest.getApiKey());
				return this.getAuthenticationManager().authenticate(authRequest);
			} else {
				throw new AuthenticationServiceException("Invalid Content-Type. Login accepts only 'application/json'");
			}
		} catch (JsonParseException e) {
			throw new AuthenticationServiceException("Invalid data content in request body", e);
		} catch (JsonMappingException e) {
			throw new AuthenticationServiceException("Invalid data content in request body", e);
		} catch (IOException e) {
			throw new AuthenticationServiceException("Invalid data content in request body", e);
		} catch (Exception e) {
			throw new AuthenticationServiceException("Invalid password", e);
		}
	}

	@Autowired
	@Override
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);
	}

}
