package com.sunil.employeeManger.dto;

public class AuthResponse {

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "AuthResponse [message=" + message + "]";
	}

}
