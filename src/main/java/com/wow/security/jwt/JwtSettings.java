package com.wow.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "classpath:jwt.properties")
public class JwtSettings {
	
	@Value("${jwt.tokenExpiringTime}")
    private Integer tokenExpirationTime;

	@Value("${jwt.tokenIssuer}")
    private String tokenIssuer;
    
	@Value("${jwt.tokenSigningKey}")
    private String tokenSigningKey;
    
	@Value("${jwt.refreshTokenExpiringTime}")
    private Integer refreshTokenExpTime;
	
	@Value("${jwt.refreshTokenSigningKey}")
	private String refreshTokenSigningKey;

	public Integer getTokenExpirationTime() {
		return tokenExpirationTime;
	}

	public String getTokenIssuer() {
		return tokenIssuer;
	}

	public String getTokenSigningKey() {
		return tokenSigningKey;
	}

	public Integer getRefreshTokenExpTime() {
		return refreshTokenExpTime;
	}

	public String getRefreshTokenSigningKey() {
		return refreshTokenSigningKey;
	}
}
