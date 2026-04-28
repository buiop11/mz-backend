package com.matjzing.dto.swagger;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter @Setter
@Alias("swaggerLoginRequest")
public class SwaggerLoginRequest {

    private String id;
    private String password;

}

