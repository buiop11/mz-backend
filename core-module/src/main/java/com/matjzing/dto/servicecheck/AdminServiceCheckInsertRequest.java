package com.matjzing.dto.servicecheck;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "admin 시스템 점검 등록 Request")
public class AdminServiceCheckInsertRequest {

	@Schema(description = "점검이유", example = "서비스 개선을 위한 점검")
	private String info;

	@Schema(description = "기간", example = "12월 16일 (금) 00:00 ~ 18:00")
	private String dt;

}
