package com.matjzing.dto.groups;

import com.matjzing.dto.common.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * 토픽 참여 회원(TOPIC_MEMBER) 목록 조회 요청
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "토픽 참여 회원 List 조회 요청")
@Alias("frontGroupsSelectListRequest")
public class FrontGroupsSelectListRequest extends PageRequest {

	@Schema(description = "토픽 시퀀스", example = "1")
	private Long topicSeq;

	@Schema(description = "회원 시퀀스", example = "1")
	private Long memberSeq;

	@Schema(description = "참여 역할", example = "PARTICIPANT")
	private String roleType;

}
