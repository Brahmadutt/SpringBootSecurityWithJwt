package com.wow.security.exceptions;

import org.springframework.security.core.AuthenticationException;

public class TokenExpiredException extends AuthenticationException {

	private static final long serialVersionUID = 4965117660438897862L;

	public TokenExpiredException() {
		super("Token Expired");
	}

}
