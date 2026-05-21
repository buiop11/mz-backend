package com.matjzing.dto.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import com.matjzing.dto.common.BaseRequest;

import jakarta.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "front 회원가입 요청 모델")
public class FrontMemberInsertRequest extends BaseRequest {

	@NotNull(message = "ID 필수값입니다.")
	@Schema(description = "ID", example = "userId", required = true)
	private String id;

	@NotNull(message = "비밀번호 필수값입니다.")
	@Schema(description = "비밀번호", example = "qwer1234Q!@", required = true)
	private String password;

	@Schema(description = "패스정보", example = "PASS 암호화 데이터")
	private String passInfo;

	@NotNull(message = "이메일 필수값입니다.")
	@Schema(description = "이메일", example = "emotion@emotion.co.kr", required = true)
	private String email;

	@Schema(description = "회원_시퀀스", hidden = true)
	private Long memberSeq;

	@Schema(description = "CI", hidden = true)
	private String ci;

	@Schema(description = "DI", hidden = true)
	private String di;

	@Schema(description = "이름", hidden = true)
	private String name;

	@Schema(description = "닉네임", hidden = true)
	private String nickname;

	@Schema(description = "전화번호", hidden = true)
	private String mobilePhoneNo;

	@Schema(description = "통신사 구분 코드", hidden = true)
	private String telecomSectionCd;

	@Schema(description = "내국인 구분 코드", hidden = true)
	private String nativeSectionCd;

	@Schema(description = "성별 구분 코드", hidden = true)
	private String genderCd;

	@Schema(description = "생년월일", hidden = true)
	private String birthday;

}
