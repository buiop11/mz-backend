package com.matjzing.dto.groups;

import com.matjzing.dto.common.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * 토픽 참여 회원(TOPIC_MEMBER) 수정 요청
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "토픽 참여 회원 수정 요청")
@Alias("frontGroupsUpdateRequest")
public class FrontGroupsUpdateRequest extends BaseRequest {

	@Schema(description = "토픽 참여 시퀀스", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
	private Long topicMemberSeq;

	@Schema(description = "참여 역할 (OWNER|PARTICIPANT)", example = "PARTICIPANT")
	private String roleType;

}
