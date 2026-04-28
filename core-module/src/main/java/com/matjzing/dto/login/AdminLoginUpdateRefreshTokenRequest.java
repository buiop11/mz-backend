package com.matjzing.dto.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@Schema(description = "admin 엑세스토큰 갱신 요청 모델")
@Data
public class AdminLoginUpdateRefreshTokenRequest {

	@Schema(description = "refresh 토큰", example = "abc...", hidden = true)
	private String refreshToken;

}
