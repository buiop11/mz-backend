package com.matjzing.exception;

import lombok.*;
import com.matjzing.dto.common.MessageResponse;

@EqualsAndHashCode(callSuper = false)
@Data
public class MessageException extends RuntimeException {
	MessageResponse messageResponse;

	public MessageException(MessageResponse messageResponse) {

		this.messageResponse = messageResponse;
	}
}
