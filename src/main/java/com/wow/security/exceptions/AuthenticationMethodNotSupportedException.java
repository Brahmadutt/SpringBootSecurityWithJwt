package com.wow.security.exceptions;

import org.springframework.security.authentication.AuthenticationServiceException;

public class AuthenticationMethodNotSupportedException extends AuthenticationServiceException {
    private static final long serialVersionUID = 3705043083010304496L;

    public AuthenticationMethodNotSupportedException(String msg) {
        super(msg);
    }
}
