package com.matjzing.dto.login;

import io.swagger.v3.oas.annotations.media.Schema;
import com.matjzing.dto.common.BaseRequest;
import lombok.*;
import org.apache.ibatis.type.Alias;


import jakarta.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "front 로그인 요청 모델")
@Alias("frontLoginRequest")
public class FrontLoginRequest extends BaseRequest {

	@NotNull(message = "ID는 필수값입니다.")
	@Schema(description = "ID", example = "USER", required = true)
	private String id;

	@NotNull(message = "비밀번호는 필수값입니다.")
	@Schema(description = "비밀번호", example = "PWD", required = true)
	private String password;
	@Schema(description = "회원일련번호", hidden = true)
	private Long memberSeq;

	@Schema(description = "DI", hidden = true)
	private String di;

	@Schema(description = "PASS 암호화 데이터", hidden = true)
	private String passInfo;

}
