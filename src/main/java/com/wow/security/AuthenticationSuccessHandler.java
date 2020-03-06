package com.wow.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.wow.security.constants.ErrorResponseCode;
import com.wow.security.constants.ResponseCode;
import com.wow.security.jwt.token.JwtToken;
import com.wow.security.jwt.token.JwtTokenFactory;
import com.wow.security.jwt.token.UserContext;
import com.wow.security.response.BaseResponse;
import com.wow.security.response.ErrorResponse;
import com.wow.security.response.LoginResponse;
import com.wow.security.util.JacksonConversionUtil;

/**
 * 
 * @author Brahmadutt S
 *
 * Mar 6, 2020
 */

@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	private final JwtTokenFactory tokenFactory;

	@Autowired
	public AuthenticationSuccessHandler(final JwtTokenFactory tokenFactory) {
		this.tokenFactory = tokenFactory;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		BaseResponse responseData = new BaseResponse();
		try {
			UserContext userContext = (UserContext) authentication.getPrincipal();

			JwtToken accessToken = tokenFactory.createAccessJwtToken(userContext);
			JwtToken refreshToken = tokenFactory.createRefreshToken(userContext);

			Map<String, String> tokenMap = new HashMap<String, String>();
			tokenMap.put("token", accessToken.getToken());
			tokenMap.put("refreshToken", refreshToken.getToken());

			LoginResponse loginResponse = new LoginResponse();
			loginResponse.setAuthToken(accessToken.getToken());
			loginResponse.setRefreshToken(refreshToken.getToken());

			responseData.setResponseCode(ResponseCode.SUCCESS);
			responseData.setResponseBody(loginResponse);

		} catch (IllegalArgumentException e) {
			ErrorResponse error = new ErrorResponse();
			error.setResponseCode(ErrorResponseCode.ILLEGAL_ARGUMENTS);
			error.setAdditionalMsg(e.getMessage());
			responseData.setError(error);
			responseData.setResponseCode(ResponseCode.FAILURE);
		} catch (Exception e) {
			ErrorResponse error = new ErrorResponse();
			error.setResponseCode(ErrorResponseCode.UNEXPECTED_ERROR);
			error.setAdditionalMsg(e.getMessage());
			responseData.setError(error);
			responseData.setResponseCode(ResponseCode.FAILURE);
		}
		response.addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		response.addHeader("Accept", MediaType.APPLICATION_JSON_VALUE);
		response.getWriter().print(JacksonConversionUtil.writeValueAsString(responseData));
		response.getWriter().flush();

		HttpSession session = request.getSession(false);

		if (session == null) {
			return;
		}

		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
}