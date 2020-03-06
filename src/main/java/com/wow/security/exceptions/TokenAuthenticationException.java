package com.wow.security.exceptions;

import org.springframework.security.core.AuthenticationException;

public class TokenAuthenticationException extends AuthenticationException {

	private static final long serialVersionUID = 3412391929888813798L;

	public TokenAuthenticationException(String msg) {
		super(msg);
	}

	public TokenAuthenticationException(String msg, Throwable t) {
		super(msg, t);
	}

}
