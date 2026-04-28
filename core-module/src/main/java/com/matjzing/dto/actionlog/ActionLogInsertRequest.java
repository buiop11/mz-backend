package com.matjzing.dto.actionlog;

import com.matjzing.dto.common.BaseRequest;
import lombok.*;
import org.apache.ibatis.type.Alias;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Alias("actionLogInsertRequest")
public class ActionLogInsertRequest extends BaseRequest {

	// OS_구분_코드
	private String osSectionCd;

	// 요청_메소드_코드
	private String requestMethodCd;

	// 요청_URL
	private String requestUrl;

	// 실행_시간
	private Long executionTime;

}
