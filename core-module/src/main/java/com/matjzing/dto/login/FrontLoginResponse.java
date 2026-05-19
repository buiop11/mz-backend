package com.matjzing.dto.login;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@EqualsAndHashCode(callSuper = false)
@Schema(description = "front 로그인 응답 모델")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FrontLoginResponse {

	@Schema(description = "이메일", example = "emotion@emotion.co.kr")
	private String email;

	@Schema(description = "비밀번호 만료 여부 true이면 갱신 팝업 노출", example = "false")
	private boolean passwordExpiredYn;

	@Schema(description = "회원 일련번호 (MEMBER.MEMBER_SEQ). TOPIC/CANDIDATE/COMMENT의 REGISTER_SEQ에 저장됨", example = "1")
	private Long memberSeq;

	@Schema(description = "access 토큰", example = "abc...")
	private String accessToken;

	@Schema(description = "accessToken 토큰만료일자", example = "2022.09.06 09:00.000")
	private String accessTokenExpiredDt;

	@JsonIgnore
	@Schema(description = "refresh 토큰", example = "abc...", hidden = true)
	private String refreshToken;

	@JsonIgnore
	@Schema(description = "refreshToken 토큰만료일자", example = "2022.09.06 09:00.000", hidden = true)
	private String refreshTokenExpiredDt;


}
