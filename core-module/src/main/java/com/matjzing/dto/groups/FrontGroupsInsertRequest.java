package com.matjzing.dto.groups;

import com.matjzing.dto.common.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * 토픽 참여 회원(TOPIC_MEMBER) 등록 요청
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "토픽 참여 회원 등록 요청")
@Alias("frontGroupsInsertRequest")
public class FrontGroupsInsertRequest extends BaseRequest {

	@Schema(hidden = true)
	private Long topicMemberSeq;

	@Schema(description = "토픽 시퀀스", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
	private Long topicSeq;

	@Schema(description = "참여 역할 (OWNER|PARTICIPANT)", example = "PARTICIPANT")
	private String roleType;

}
