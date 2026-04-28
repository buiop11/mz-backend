package com.matjzing.service;

import com.matjzing.dto.common.*;
import com.matjzing.dto.common.enumeration.*;
import com.matjzing.dto.login.*;
import com.matjzing.exception.*;
import com.matjzing.mapper.AdminLoginMapper;
import com.matjzing.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
@Service
public class AdminLoginService {

    private enum LoginERRCd {
        ERR_LOGIN_001("일치하는 회원 정보가 없는 경우"),
        ERR_LOGIN_002("리프레시 토큰이 없는 경우"),
        ERR_LOGIN_003("기존 비밀번호화 일치하는 경우"),
        ;
        private final String desc;
        public String getDesc() {
            return desc;
        }
        LoginERRCd(String desc) {
            this.desc = desc;
        }
    }

    @Value("${jwt.token-validity-in-seconds.refresh}")
    private long JWT_REFRESH_EXPIRATION;

    @Value("${jwt.refresh-header}")
    private String HEADER_REFRESH;

    private final AdminLoginMapper mapper;
    
    private final Sha256Util sha256Util;
    private final Aes256Util aes256Util;
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
        private final LoginUtil loginUtil;

    @Transactional
    public AdminLoginResponse login(AdminLoginRequest req) {
        // 회원 조회
        req.setManagerId(aes256Util.encrypt(req.getManagerId()));
        AdminLoginSelectResponse manager = mapper.selectLogin(req);

        if (ObjectUtils.isEmpty(manager) || !sha256Util.encrypt(req.getPassword()).equals(manager.getPassword())) {
            // 아이디가 없거나 비밀번호 오류
            throw new CustomException(LoginERRCd.ERR_LOGIN_001.toString(), LoginERRCd.ERR_LOGIN_001.getDesc());
        } else {
            // 정상인 경우
            return responseLogin(req, manager, true);
        }
    }

    public AdminLoginResponse updateAccessToken(AdminLoginUpdateRefreshTokenRequest req) {
        req.setRefreshToken(cookieUtil.getCookie(HEADER_REFRESH));

        if (ObjectUtils.isEmpty(req.getRefreshToken())) {
            throw new CustomException(LoginERRCd.ERR_LOGIN_002.name(), LoginERRCd.ERR_LOGIN_002.getDesc());
        }

        JwtAuthenticationVo refreshToken = jwtUtil.getTokenVo(req.getRefreshToken());

        if (!ObjectUtils.isEmpty(refreshToken) && JwtType.refresh.equals(refreshToken.getJwtType())) {
            AdminLoginRequest adminLoginRequest = new AdminLoginRequest();
            adminLoginRequest.setManagerSeq(refreshToken.getSeq());
            boolean isGenerateRefresh = false;
            return responseLogin(adminLoginRequest, mapper.selectManager(adminLoginRequest), isGenerateRefresh);
        } else {
            throw new JwtException();
        }

    }

    public void logout() {
        // 리프레시 토큰 삭제
        cookieUtil.setRefreshToken("", 0L);
    }

    private AdminLoginResponse generateToken(AdminLoginSelectResponse req, boolean isGenerateRefresh) {
        JwtAuthenticationVo jwtAuthenticationVo = JwtAuthenticationVo.builder()
                .seq(req.getManagerSeq())
                .id(req.getManagerId())
                .jwtType(JwtType.access)
                .platformType(PlatformType.admin)
                .build();

        // 엑세스 토큰 생성
        String accessToken = jwtUtil.generateToken(jwtAuthenticationVo);
        String accessTokenExpiredDt = jwtUtil.dateToDateFormatString(jwtAuthenticationVo.getExpiration());

        String refreshToken = "";
        String refreshTokenExpiredDt = "";
        if (isGenerateRefresh) {
            // 리프레시 토큰 생성
            jwtAuthenticationVo.setJwtType(JwtType.refresh);
            refreshToken = jwtUtil.generateToken(jwtAuthenticationVo);
            refreshTokenExpiredDt = jwtUtil.dateToDateFormatString(jwtAuthenticationVo.getExpiration());
        }

        return AdminLoginResponse.builder()
                .accessToken(accessToken)
                .accessTokenExpiredDt(accessTokenExpiredDt)
                .refreshToken(refreshToken)
                .refreshTokenExpiredDt(refreshTokenExpiredDt)
                .build();
    }

    @Transactional
    public AdminLoginResponse responseLogin(AdminLoginRequest req, AdminLoginSelectResponse manager, boolean isGenerateRefresh) {
        // 토큰 생성
        AdminLoginResponse token = generateToken(manager, isGenerateRefresh);

        // 비밀번호 만료여부 체크
        LocalDateTime now = LocalDateTime.now();
        boolean isPasswordExpired = manager.isPasswordInitYn() || (null != manager.getPasswordChangeDt() && ChronoUnit.DAYS.between(manager.getPasswordChangeDt(), now) > 90);

//        MapperUtil.setBaseRequest(req); // BaseRequest 셋팅
//        req.setManagerSeq(manager.getManagerSeq());
//        req.setUpdaterSeq(req.getManagerSeq());
//        mapper.updateLoginInfo(req);

        // 엑세스토큰 갱신 API가 아닌 경우에만
        if (StringUtils.hasText(token.getRefreshToken())) {
            // 리프레시 토큰 쿠키로 변경
            cookieUtil.setRefreshToken(token.getRefreshToken(), JWT_REFRESH_EXPIRATION);
        }

        return AdminLoginResponse.builder()
                .passwordExpiredYn(isPasswordExpired)
                .accessToken(token.getAccessToken())
                .accessTokenExpiredDt(token.getAccessTokenExpiredDt())
                .refreshToken(token.getRefreshToken())
                .accessTokenExpiredDt(token.getRefreshTokenExpiredDt())
                .name(manager.getName())
                .connectionDt(LocalDateTime.now())
                .connectionIp(RestUtil.getClientIp())
                .build();
    }

//    public AdminFindPasswordResponse findPassword(AdminFindPasswordRequest req) {
//        req.setManagerId(aes256Util.encrypt(req.getManagerId()));
//        req.setMobilePhoneNo(aes256Util.encrypt(req.getMobilePhoneNo()));
//
//        AdminFindPasswordResponse response = mapper.findPassword(req);
//
//        if (null != response) {
//            response.setAuthKey(aes256Util.encrypt(String.valueOf(response.getManagerSeq())));
//            return response;
//        } else {
//            throw new CustomException(LoginERRCd.ERR_LOGIN_001.toString(), LoginERRCd.ERR_LOGIN_001.getDesc());
//        }
//    }
//
//    @Transactional
//    public void updatePassword(AdminUpdatePasswordRequest req) {
//        req.setPassword(sha256Util.encrypt(req.getPassword()));
//
//        req.setManagerSeq(Long.valueOf(aes256Util.decrypt(req.getAuthKey())));
//
//        if (mapper.updatePassword(req) == 0) {
//            throw new CustomException(LoginERRCd.ERR_LOGIN_001.toString(), LoginERRCd.ERR_LOGIN_001.getDesc());
//        }
//    }

}
