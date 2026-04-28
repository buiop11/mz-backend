package com.matjzing.exception;

import com.matjzing.dto.common.enumeration.ErrorField;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@Data
public class CustomFieldException extends RuntimeException {

	private ErrorField errorField;
	private String message;

	public CustomFieldException(ErrorField errorField, String message) {
		super(message);
		this.errorField = errorField;
		this.message = message;
	}

}

