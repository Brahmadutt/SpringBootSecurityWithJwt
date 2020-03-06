package com.wow.security.response;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class LoginResponse {

	@JsonProperty("auth_token")
	private String authToken;

	@JsonProperty("sek")
	private String sek;

	@JsonProperty("refresh_token")
	private String refreshToken;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getSek() {
		return sek;
	}

	public void setSek(String sek) {
		this.sek = sek;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

}