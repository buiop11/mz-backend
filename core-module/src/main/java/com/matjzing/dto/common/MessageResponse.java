package com.matjzing.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "Message Response")
@Alias("messageResponse")
public class MessageResponse {

	@Schema(description = "점검_시작_일시", example = SwaggerSample.LOCAL_DATE_SAMPLE_ST)
	private LocalDateTime checkBeginDt;

	@Schema(description = "점검_종료_일시", example = SwaggerSample.LOCAL_DATE_SAMPLE_ST)
	private LocalDateTime checkEndDt;

}
