package com.matjzing.dto.swagger;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.ibatis.type.Alias;

@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "SWAGGER_회원 Page 조회 응답 모델")
@Alias("swaggerUserSelectPageResponse")
public class SwaggerUserSelectPageResponse {

	@Schema(description = "회원_시퀀스", example = "")
	private Long memberSeq;

	@Schema(description = "ID", example = "")
	private String id;

	@Schema(description = "비밀번호", example = "")
	private String password;

	@Schema(description = "이름", example = "")
	private String name;

}
