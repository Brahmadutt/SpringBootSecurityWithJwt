package com.wow.security.constants;

public enum ErrorResponseCode {
	
	UNEXPECTED_ERROR(1001, "Unexpected error"),
	ILLEGAL_ARGUMENTS(1002, "Illegal arguments"),
	BAD_CREDENTIALS(1003, "Bad credentials"),
	INVALID_TOKEN(1004, "Invalid token"),
	AUTHENTICATION_METHOD_NOT_SUPPORTED(1005, "Authentication method not supported"),
	VALIDATION_FAILURE(1011, "Validation failure"),
	ENCRYPTION_KEY_EXPIRED(512, "Encryption Key expired"),
	TOKEN_EXPIRED(511, "Token expired"),
	UNAUTHORIZED_REQUEST(401, "Unauthorized request");
	
	private final int errorStatus;
	private final String errorMsg;
	public int getErrorStatus() {
		return errorStatus;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	private ErrorResponseCode(int errorStatus, String errorMsg) {
		this.errorStatus = errorStatus;
		this.errorMsg = errorMsg;
	}
}
