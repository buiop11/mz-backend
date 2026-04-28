package com.matjzing.dto.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "front 회원가입 응답 모델")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FrontMemberInsertResponse {

	@Schema(description = "이름", example = "홍길동")
	private String name;

}
