package com.sunil.employeeManger.exception;

public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String string) {
		super(string);
	}

}
