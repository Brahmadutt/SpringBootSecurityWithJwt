package com.wow.security.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequest implements Serializable {

	private static final long serialVersionUID = -6005863393471341061L;

	@JsonProperty("username")
	private String username;

	@JsonProperty("password")
	private String password;

	@JsonProperty("api_key")
	private String apiKey;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

}
