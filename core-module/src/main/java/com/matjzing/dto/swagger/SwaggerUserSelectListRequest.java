package com.matjzing.dto.swagger;

import com.matjzing.dto.common.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.ibatis.type.Alias;

@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "SWAGGER_회원 List 조회 요청 모델")
@Alias("swaggerUserSelectListRequest")
public class SwaggerUserSelectListRequest extends PageRequest {
}
