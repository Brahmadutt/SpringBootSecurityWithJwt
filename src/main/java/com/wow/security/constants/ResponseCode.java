package com.wow.security.constants;

public enum ResponseCode {
	
	FAILURE(0, "Failure"),
	SUCCESS(1,"Success");
	
	private final int status;
	private final String statusMsg;
	
	public int getStatus() {
		return status;
	}
	public String getStatusMsg() {
		return statusMsg;
	}
	private ResponseCode(int status, String statusMsg) {
		this.status = status;
		this.statusMsg = statusMsg;
	}
}
