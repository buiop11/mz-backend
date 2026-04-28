package com.matjzing.exception;

import lombok.*;
import com.matjzing.dto.common.enumeration.ErrorCode;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
public class AppVerException extends RuntimeException {

	private ErrorCode errorCode;
	private Object data;
	private String clientOs;
	private String clientVersion;
	private String serverVersion;

}
