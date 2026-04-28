package com.matjzing.dto.notice;

import com.matjzing.dto.common.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * @author: 김아진
 * @date: 2026-04-23
 * @pname: 관리자
 * @desc: 관리자 삭제 요청 모델 작성
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "공지사항 삭제 요청 모델")
@Alias("adminNoticeDeleteRequest")
public class AdminNoticeDeleteRequest extends BaseRequest {

	@Schema(description = "_", example = "")
	private Long noticeSeq;
}
