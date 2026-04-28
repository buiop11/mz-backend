package com.matjzing.dto.swagger;

import com.matjzing.dto.common.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.ibatis.type.Alias;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "SWAGGER_회원 수정 요청 모델")
@Alias("swaggerUserUpdateRequest")
public class SwaggerUserUpdateRequest extends BaseRequest {

	@NotNull(message = "필수값입니다.")
	@Schema(description = "회원_시퀀스", example = "1")
	private Long memberSeq;

	@NotEmpty(message = "필수값입니다.")
	@Schema(description = "비밀번호", example = "pWd1!")
	private String password;

}

