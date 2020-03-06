package com.wow.security.response;

import java.util.Base64;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.wow.security.constants.ResponseCode;
import com.wow.security.util.JacksonConversionUtil;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(Include.NON_NULL)
public class BaseResponse {

	@JsonIgnore
	private ResponseCode responseCode;
	
	@JsonProperty("status_cd")
	private int statusCode;
	
	@JsonProperty("status_msg")
	private String statusMsg;
	
	@JsonProperty("data")
	private String base64EncodedData;

	@JsonIgnore
	private Object responseBody;

	@JsonProperty("shek")
	private String sharedEncryptedKey;

	@JsonProperty("hmac")
	private String hmac;
	
	@JsonProperty("error")
	private ErrorResponse error;

	public ResponseCode getResponseCode() {
		return this.responseCode;
	}

	public void setResponseCode(ResponseCode responseCode) {
		this.responseCode = responseCode;
	}

	public Object getResponseBody() {
		return this.responseBody;
	}

	public void setResponseBody(Object responseBody) {
		this.responseBody = responseBody;
		if (this.responseBody != null) {
			this.base64EncodedData = Base64.getEncoder().encodeToString(JacksonConversionUtil.writeValueAsBytes(responseBody));
		}
	}

	public String getSharedEncryptedKey() {
		return this.sharedEncryptedKey;
	}

	public void setSharedEncryptedKey(String sharedEncryptedKey) {
		this.sharedEncryptedKey = sharedEncryptedKey;
	}

	public String getHmac() {
		return this.hmac;
	}

	public void setHmac(String hmac) {
		this.hmac = hmac;
	}

	public String getBase64EncodedData() {
		return this.base64EncodedData;
	}

	public int getStatusCode() {
		statusCode = responseCode.getStatus();
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMsg() {
		statusMsg=responseCode.getStatusMsg();
		return statusMsg;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	public ErrorResponse getError() {
		return error;
	}

	public void setError(ErrorResponse error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "Response [statusCode=" + getStatusCode() + ", statusMsg=" + getStatusMsg() + ", data="
				+ base64EncodedData + ", error=" + error + "]";
	}
}
