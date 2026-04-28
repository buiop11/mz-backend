package com.matjzing.dto.file;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "파일 업로드 FileUploadRequest DTO")
public class FileUploadRequest{

	@Schema(description = "멀티파트 File Array")
	private List<MultipartFile> files;

}
