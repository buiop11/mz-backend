package com.matjzing.dto.common.enumeration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.*;

import java.util.*;

@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ErrorField {

	@Schema(description = "에러목록")
	private List<Error> errors;

	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	public static class Error {
		@Schema(description = "에러필드", example = "title")
		private String field;
		@Schema(description = "에러메시지", example = "title은 필수값입니다.")
		private String message;
	}

	public static ErrorField of(BindingResult bindingResult) {
		List<Error> errors = new ArrayList<>();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			errors.add(Error.builder()
				.field(fieldError.getField())
				.message(fieldError.getDefaultMessage())
				.build());
		}
		return ErrorField.builder()
				.errors(errors)
				.build();
	}

	public static ErrorField of(List<Error> errors) {
		return ErrorField.builder().errors(errors).build();
	}

}
