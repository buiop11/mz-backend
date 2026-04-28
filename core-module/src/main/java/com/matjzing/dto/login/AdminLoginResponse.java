package com.matjzing.dto.login;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import com.matjzing.dto.common.SwaggerSample;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Schema(description = "admin 로그인 응답 모델")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminLoginResponse {

	@Schema(description = "비밀번호 만료 여부 true이면 갱신 팝업 노출", example = "false")
	private boolean passwordExpiredYn;

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

	@Schema(description = "관리자 이름", example = "김관리")
	private String name;

	@Schema(description = "접속 일시", example = SwaggerSample.LOCAL_DATE_SAMPLE_ST)
	private LocalDateTime connectionDt;

	@Schema(description = "접속 IP", example = "127.0.0.1")
	private String connectionIp;

}
