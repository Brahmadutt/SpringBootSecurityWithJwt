package com.wow.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.wow.security.constants.ErrorResponseCode;
import com.wow.security.constants.ResponseCode;
import com.wow.security.exceptions.EncryprionKeyExpiredException;
import com.wow.security.exceptions.TokenAuthenticationException;
import com.wow.security.exceptions.TokenExpiredException;
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
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		BaseResponse responseData = new BaseResponse();
		responseData.setResponseCode(ResponseCode.FAILURE);
		ErrorResponse errorResponse = new ErrorResponse();
		if (authException instanceof EncryprionKeyExpiredException) {
			errorResponse.setResponseCode(ErrorResponseCode.ENCRYPTION_KEY_EXPIRED);
		} else if (authException instanceof TokenExpiredException) {
			errorResponse.setResponseCode(ErrorResponseCode.TOKEN_EXPIRED);
		} else if (authException instanceof TokenAuthenticationException) {
			errorResponse.setResponseCode(ErrorResponseCode.INVALID_TOKEN);
		} else if (authException instanceof InsufficientAuthenticationException) {
			errorResponse.setResponseCode(ErrorResponseCode.UNAUTHORIZED_REQUEST);
		}
		responseData.setError(errorResponse);
		byte[] body = JacksonConversionUtil.writeValueAsBytes(responseData);
		response.getOutputStream().write(body);
	}

}