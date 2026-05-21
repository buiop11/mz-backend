package com.matjzing.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Data
public class BaseRequest {

	@Schema(description = "로그인 회원 MEMBER_SEQ. access JWT에 회원 정보가 있으면 생략 가능하며, 그렇지 않을 때만 JSON에 넣으면 REGISTER_SEQ/UPDATER_SEQ에 반영됩니다.", example = "1")
	private Long memberSeq;

	@Schema(description = "등록자_시퀀스", hidden = true)
	private Long registerSeq;

	@Schema(description = "등록_일시", hidden = true)
	private LocalDateTime registrationDt;

	@Schema(description = "등록자_IP", hidden = true)
	private String registerIp;

	@Schema(description = "수정자_시퀀스", hidden = true)
	private Long updaterSeq;

	@Schema(description = "수정_일시", hidden = true)
	private LocalDateTime updateDt;

	@Schema(description = "수정자_IP", hidden = true)
	private String updaterIp;

}
