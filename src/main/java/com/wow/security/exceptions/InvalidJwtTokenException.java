package com.wow.security.exceptions;

import org.springframework.security.core.AuthenticationException;

import com.wow.security.jwt.token.JwtToken;

public class InvalidJwtTokenException extends AuthenticationException {
    private static final long serialVersionUID = -294671188037098603L;
    
    private JwtToken token;

    public InvalidJwtTokenException(String msg) {
        super(msg);
    }

    public InvalidJwtTokenException(JwtToken token, String msg, Throwable t) {
        super(msg, t);
        this.token = token;
    }

    public String token() {
        return this.token.getToken();
    }
}
