package com.wow.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.wow.security.constants.ErrorResponseCode;
import com.wow.security.constants.ResponseCode;
import com.wow.security.exceptions.AuthenticationMethodNotSupportedException;
import com.wow.security.exceptions.InvalidJwtTokenException;
import com.wow.security.exceptions.JwtExpiredTokenException;
import com.wow.security.response.BaseResponse;
import com.wow.security.response.ErrorResponse;
import com.wow.security.util.JacksonConversionUtil;

/**
 * 
 * @author Brahmadutt S
 *
 * Mar 6, 2020
 */

@Component
public class JwtAuthenticationFailureHandler
		implements org.springframework.security.web.authentication.AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		BaseResponse responseData = new BaseResponse();
		responseData.setResponseCode(ResponseCode.FAILURE);

		ErrorResponse error = new ErrorResponse();
		if (exception instanceof BadCredentialsException) {
			error.setResponseCode(ErrorResponseCode.BAD_CREDENTIALS);
		} else if (exception instanceof JwtExpiredTokenException) {
			error.setResponseCode(ErrorResponseCode.TOKEN_EXPIRED);
		} else if (exception instanceof AuthenticationMethodNotSupportedException) {
			error.setResponseCode(ErrorResponseCode.AUTHENTICATION_METHOD_NOT_SUPPORTED);
		} else if (exception instanceof AuthenticationServiceException || exception instanceof InvalidJwtTokenException) {
			error.setResponseCode(ErrorResponseCode.INVALID_TOKEN);
		} else {
			error.setResponseCode(ErrorResponseCode.UNEXPECTED_ERROR);
		}
		error.setAdditionalMsg(exception.getMessage());
		responseData.setError(error);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		response.addHeader("Accept", MediaType.APPLICATION_JSON_VALUE);
		response.getWriter().print(JacksonConversionUtil.writeValueAsString(responseData));
		response.getWriter().flush();
	}

}
