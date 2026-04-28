package com.matjzing.dto.swagger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Alias("swaggerLoginSelectResponse")
public class SwaggerLoginSelectResponse {

    private Long memberSeq;

}
