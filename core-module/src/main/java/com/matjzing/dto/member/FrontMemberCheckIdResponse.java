package com.matjzing.dto.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "front id 중복체크 응답 모델")
public class FrontMemberCheckIdResponse {

	@Schema(description = "사용 가능 여부(true:사용가능, false:사용불가)", example = "true")
	private boolean useYn;

}
