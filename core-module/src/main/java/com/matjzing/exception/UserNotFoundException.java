package com.matjzing.exception;

public class UserNotFoundException extends RuntimeException{

	public UserNotFoundException() {
		super("Can't find User");
	}

}
