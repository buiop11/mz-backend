package com.matjzing.dto.swagger;

import com.matjzing.dto.common.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.ibatis.type.Alias;

import jakarta.validation.constraints.NotEmpty;

@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "SWAGGER_회원 등록 요청 모델")
@Alias("swaggerUserInsertRequest")
public class SwaggerUserInsertRequest extends BaseRequest {

	@Schema(description = "회원_시퀀스", hidden = true)
	private Long memberSeq;

	@NotEmpty(message = "필수값입니다.")
	@Schema(description = "ID", example = "mailId123@naver.co.kr")
	private String id;

	@NotEmpty(message = "필수값입니다.")
	@Schema(description = "비밀번호", example = "pWd1!")
	private String password;

	@NotEmpty(message = "필수값입니다.")
	@Schema(description = "이름", example = "스웨거")
	private String name;

}
