package com.matjzing.service;

import com.github.pagehelper.PageHelper;
import com.matjzing.dto.common.EPageInfo;
import com.matjzing.dto.common.JwtAuthenticationVo;
import com.matjzing.dto.common.enumeration.JwtType;
import com.matjzing.dto.common.enumeration.PlatformType;
import com.matjzing.dto.swagger.*;
import com.matjzing.exception.NotfoundException;
import com.matjzing.mapper.SwaggerUserMapper;
import com.matjzing.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class SwaggerUserService {

    private final SwaggerUserMapper mapper;
    private final Aes256Util aes256Util;
    private final Sha256Util sha256Util;
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;

    public EPageInfo<SwaggerUserSelectPageResponse> page(SwaggerUserSelectPageRequest req) {
        PageHelper.startPage(req.getCurrentPage(), 10);
        return new EPageInfo<>(mapper.selectSwaggerUserPage(req));
    }

    public SwaggerUserSelectResponse detail(SwaggerUserSelectRequest req) {
        SwaggerUserSelectResponse response = mapper.selectSwaggerUser(req);
        if (null == response) {
            throw new NotfoundException();
        }
        return response;
    }

    @Transactional
    public void insert(SwaggerUserInsertRequest req) {
        MapperUtil.setBaseRequest(req); // BaseRequest 셋팅
        req.setPassword(sha256Util.encrypt(req.getPassword()));
        mapper.insertSwaggerUser(req); // 등록처리
    }

    @Transactional
    public void update(SwaggerUserUpdateRequest req) {
        MapperUtil.setBaseRequest(req); // BaseRequest 셋팅
        req.setPassword(sha256Util.encrypt(req.getPassword()));
        mapper.updateSwaggerUser(req);
    }

    @Transactional
    public void delete(SwaggerUserDeleteRequest req) {
        MapperUtil.setBaseRequest(req);
        mapper.deleteSwaggerUser(req);
    }

    public String login(SwaggerLoginRequest req) {
        req.setPassword(sha256Util.encrypt(req.getPassword()));
        SwaggerLoginSelectResponse swaggerLoginSelectResponse = mapper.selectLogin(req);

        if (!ObjectUtils.isEmpty(swaggerLoginSelectResponse)) {
            // 토큰생성 1일
            String accessToken = jwtUtil.generateToken(JwtAuthenticationVo.builder()
                    .seq(swaggerLoginSelectResponse.getMemberSeq())
                    .id(req.getId())
                    .jwtType(JwtType.access)
                    .platformType(PlatformType.admin)
                    .isSwagger(true)
                    .build());
            cookieUtil.setSecureCookie("swagger", accessToken, 1500L);
            return null;
        } else {
            return "error";
        }
    }

}