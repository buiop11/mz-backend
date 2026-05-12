package com.matjzing.dto.notice;

import com.matjzing.dto.common.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * @author: 김아진
 * @date: 2026-04-23
 * @pname: 관리자
 * @desc: 관리자 목록 조회 요청 모델 작성
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "공지사항 List 조회 요청 모델")
@Alias("adminNoticeSelectListRequest")
public class AdminNoticeSelectListRequest extends PageRequest {
	/*
		@NotNull(message = "필수값입니다.")
	 */
}
