package com.matjzing.dto.login;

import io.swagger.v3.oas.annotations.media.Schema;
import com.matjzing.dto.common.BaseRequest;
import lombok.*;
import org.apache.ibatis.type.Alias;


import jakarta.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "admin 로그인 요청 모델")
@Alias("adminLoginRequest")
public class AdminLoginRequest extends BaseRequest {

	@NotNull(message = "ID는 필수값입니다.")
	@Schema(description = "ID", example = "super@super.co.kr", required = true)
	private String managerId;

	@NotNull(message = "비밀번호는 필수값입니다.")
	@Schema(description = "비밀번호", example = "super", required = true)
	private String password;

	@Schema(description = "관리자일련번호", hidden = true)
	private Long managerSeq;

}
