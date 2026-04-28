package com.matjzing.dto.member;

import io.swagger.v3.oas.annotations.media.Schema;
import com.matjzing.dto.common.BaseRequest;
import lombok.*;
import org.apache.ibatis.type.Alias;

@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "front 비밀번호 변경 요청 모델")
@Alias("frontMemberPasswordUpdateRequest")
public class FrontMemberPasswordUpdateRequest extends BaseRequest {

	@Schema(description = "memberSeq", hidden = true)
	private Long memberSeq;

	@Schema(description = "현재 비밀번호", example = "abc1")
	private String nowPassword;

	@Schema(description = "변경 비밀번호", example = "abcd1")
	private String newPassword;

}
