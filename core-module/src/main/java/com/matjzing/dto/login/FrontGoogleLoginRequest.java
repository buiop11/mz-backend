package com.matjzing.dto.login;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.apache.ibatis.type.Alias;

@Alias("frontGoogleLoginRequest")
@Data
@Schema(description = "Google OIDC id_token 로그인 요청")
public class FrontGoogleLoginRequest {

	@NotBlank(message = "idToken은 필수입니다.")
	@Schema(description = "Google에서 발급한 OIDC id_token", requiredMode = Schema.RequiredMode.REQUIRED)
	private String idToken;

}
