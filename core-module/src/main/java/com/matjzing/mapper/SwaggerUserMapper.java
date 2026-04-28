package com.matjzing.mapper;

import com.github.pagehelper.Page;
import com.matjzing.dto.swagger.*;
import org.springframework.stereotype.Repository;

@Repository
public interface SwaggerUserMapper {

    Page<SwaggerUserSelectPageResponse> selectSwaggerUserPage(SwaggerUserSelectPageRequest req);
    SwaggerUserSelectResponse selectSwaggerUser(SwaggerUserSelectRequest req);
    Long insertSwaggerUser(SwaggerUserInsertRequest req);
    Long updateSwaggerUser(SwaggerUserUpdateRequest req);
    Long deleteSwaggerUser(SwaggerUserDeleteRequest req);
    SwaggerLoginSelectResponse selectLogin(SwaggerLoginRequest req);

}