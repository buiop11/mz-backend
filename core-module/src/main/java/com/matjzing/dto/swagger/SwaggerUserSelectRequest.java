package com.matjzing.dto.swagger;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.ibatis.type.Alias;

@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "SWAGGER_회원 조회 요청 모델")
@Alias("swaggerUserSelectRequest")
public class SwaggerUserSelectRequest {

	@Schema(description = "회원_시퀀스")
	private Long memberSeq;

}
