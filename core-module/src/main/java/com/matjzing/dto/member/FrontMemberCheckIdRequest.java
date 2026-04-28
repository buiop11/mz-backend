package com.matjzing.dto.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import jakarta.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = false)
@Data
@Schema(title = "front id 중복체크 요청 모델")
public class FrontMemberCheckIdRequest {

	@NotNull(message = "id는 필수값입니다.")
	@Schema(description = "id", example = "emotion", required = true)
	private String id;


}
