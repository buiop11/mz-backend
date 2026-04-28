package com.matjzing.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Data
public class CacheCom02 {

	@Schema(description = "점검_시작_일시", example = SwaggerSample.LOCAL_DATE_SAMPLE_ST)
	private LocalDateTime checkBeginDt;

	@Schema(description = "점검_종료_일시", example = SwaggerSample.LOCAL_DATE_SAMPLE_ST)
	private LocalDateTime checkEndDt;

}
