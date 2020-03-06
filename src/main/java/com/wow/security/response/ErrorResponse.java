package com.wow.security.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wow.security.constants.ErrorResponseCode;


public class ErrorResponse {
	
	@JsonProperty("error_cd")
	private Integer errorCode;

	@JsonProperty("error_msg")
	private String message;
	
	@JsonProperty("additional_msg")
	private String additionalMsg;
	
	@JsonIgnore
	private ErrorResponseCode responseCode;

	public Integer getErrorCode() {
		errorCode=responseCode.getErrorStatus();
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		message=responseCode.getErrorMsg();
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ErrorResponseCode getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(ErrorResponseCode responseCode) {
		this.responseCode = responseCode;
	}

	@Override
	public String toString() {
		return "ErrorResponse [errorCode=" + getErrorCode() + ", message=" + getMessage() + ", additonal_msg="+ getAdditionalMsg()+ "]";
	}

	public String getAdditionalMsg() {
		return additionalMsg;
	}

	public void setAdditionalMsg(String additionalMsg) {
		this.additionalMsg = additionalMsg;
	}
}
