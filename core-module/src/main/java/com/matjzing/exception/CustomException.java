package com.matjzing.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
public class CustomException extends RuntimeException {

	private String code;
	private String message;
	private ErrorDetail errorDetail;

	public CustomException(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

}

