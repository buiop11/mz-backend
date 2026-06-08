package com.matjzing.dto.topic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.matjzing.dto.common.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

/**
 * PICK 완료 로그 목록 항목 — 안건(TOPIC) + 선정 후보(CANDIDATE) 단일 객체
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "PICK 완료 로그 목록 항목 (안건+선정후보 통합)")
@Alias("frontTopicPickListResponse")
@JsonPropertyOrder({
	"topicSeq", "memberSeq", "categorySeq", "categoryName", "candidateSeq",
	"emoji", "title", "status", "picked", "googleEventId", "updateDt",
	"name", "info", "price", "pickDate", "imageUrl", "linkUrl", "fixed", "proposerMemberSeq"
})
public class FrontTopicPickListResponse extends FrontTopicSelectListResponse {

	@Schema(description = "안건 수정 일시 (PICK 반영 시각)")
	private LocalDateTime updateDt;

	@Schema(description = "선정 후보명")
	private String name;

	@Schema(description = "후보 정보")
	private String info;

	@Schema(description = "가격")
	private Integer price;

	@Schema(description = "일자")
	private LocalDateTime pickDate;

	@Schema(description = "이미지 URL")
	private String imageUrl;

	@Schema(description = "링크 URL")
	private String linkUrl;

	@Schema(description = "고정 여부")
	@JsonProperty("fixed")
	private Boolean fixed;

	@Schema(description = "후보 제안 회원 시퀀스")
	private Long proposerMemberSeq;

}
