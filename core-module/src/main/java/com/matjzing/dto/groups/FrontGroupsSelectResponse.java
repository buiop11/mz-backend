package com.matjzing.dto.groups;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

/**
 * 토픽 참여 회원(TOPIC_MEMBER) 상세 조회 응답
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "토픽 참여 회원 상세 조회 응답")
@Alias("frontGroupsSelectResponse")
public class FrontGroupsSelectResponse {

	@Schema(description = "토픽 참여 시퀀스")
	private Long topicMemberSeq;

	@Schema(description = "토픽 시퀀스")
	private Long topicSeq;

	@Schema(description = "회원 시퀀스")
	private Long memberSeq;

	@Schema(description = "참여 역할")
	private String roleType;

	@Schema(description = "참여 일시")
	private LocalDateTime joinDt;

	@Schema(description = "사용 여부")
	private Boolean useYn;

}
