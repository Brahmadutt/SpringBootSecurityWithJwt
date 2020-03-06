package com.wow.security.exceptions;

import org.springframework.security.core.AuthenticationException;

public class EncryprionKeyExpiredException extends AuthenticationException {

	private static final long serialVersionUID = 291398115961352767L;

	public EncryprionKeyExpiredException() {
		super("EK Expired");
	}

}
