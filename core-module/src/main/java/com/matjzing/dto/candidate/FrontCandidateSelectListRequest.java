package com.matjzing.dto.candidate;

import com.matjzing.dto.common.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * @author: 김아진
 * @date: 2026-05-11
 * @pname: 관리자
 * @desc: 관리자 목록 조회 요청 모델 작성
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "후보 List 조회 요청 모델")
@Alias("frontCandidateSelectListRequest")
public class FrontCandidateSelectListRequest extends PageRequest {
	/*
		@NotNull(message = "필수값입니다.")
	 */
}
