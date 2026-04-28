package com.matjzing.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@Schema(description = "응답 기본 DTO")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ResponseModel<T> {

	@Schema(description = "응답 코드", required = true, example = "SUC001")
	private String code;

	@Schema(description = "응답 메세지", required = true, example = "처리가 완료되었습니다.")
	private String message;

	@Schema(description = "응답 객체", required = true)
	private T data;

}
