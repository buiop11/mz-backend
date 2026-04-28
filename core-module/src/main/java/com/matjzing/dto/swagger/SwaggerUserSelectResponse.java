package com.matjzing.dto.swagger;

import com.matjzing.dto.file.FileSelectResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "SWAGGER_회원 조회 응답 모델")
@Alias("swaggerUserSelectResponse")
public class SwaggerUserSelectResponse {

	@Schema(description = "파일 목록")
	private List<FileSelectResponse> fileList;

	@Schema(description = "회원_시퀀스", example = "")
	private Long memberSeq;

	@Schema(description = "ID", example = "")
	private String id;

	@Schema(description = "비밀번호", example = "")
	private String password;

	@Schema(description = "이름", example = "")
	private String name;

}
