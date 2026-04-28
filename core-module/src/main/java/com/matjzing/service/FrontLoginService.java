package com.matjzing.service;

import com.matjzing.dto.common.*;
import com.matjzing.dto.common.enumeration.*;
import com.matjzing.dto.login.*;
import com.matjzing.dto.login.enumeration.*;
import com.matjzing.dto.member.*;
import com.matjzing.exception.*;
import com.matjzing.mapper.FrontLoginMapper;
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
public class FrontLoginService {

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

    private final FrontLoginMapper mapper;
    
    private final Sha256Util sha256Util;
    private final Aes256Util aes256Util;
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
        private final PassUtil passUtil;
        private final LoginUtil loginUtil;

    @Transactional
    public FrontLoginResponse login(FrontLoginRequest req) {
        // 회원 조회
        req.setId(aes256Util.encrypt(req.getId()));
        FrontLoginSelectResponse member = mapper.selectLogin(req);

        if (ObjectUtils.isEmpty(member) || !sha256Util.encrypt(req.getPassword()).equals(member.getPassword())) {
            // 아이디가 없거나 비밀번호 오류
            throw new CustomException(LoginERRCd.ERR_LOGIN_001.toString(), LoginERRCd.ERR_LOGIN_001.getDesc());
        } else {
            // 정상인 경우
            return responseLogin(req, member, true);
        }
    }

    public FrontLoginResponse updateAccessToken(FrontLoginUpdateRefreshTokenRequest req) {
        req.setRefreshToken(cookieUtil.getCookie(HEADER_REFRESH));

        if (ObjectUtils.isEmpty(req.getRefreshToken())) {
            throw new CustomException(LoginERRCd.ERR_LOGIN_002.name(), LoginERRCd.ERR_LOGIN_002.getDesc());
        }

        JwtAuthenticationVo refreshToken = jwtUtil.getTokenVo(req.getRefreshToken());

        if (!ObjectUtils.isEmpty(refreshToken) && JwtType.refresh.equals(refreshToken.getJwtType())) {
            FrontLoginRequest frontLoginRequest = new FrontLoginRequest();
            frontLoginRequest.setMemberSeq(refreshToken.getSeq());
            boolean isGenerateRefresh = false;
            return responseLogin(frontLoginRequest, mapper.selectMember(frontLoginRequest), isGenerateRefresh);
        } else {
            throw new JwtException();
        }

    }

    public void logout() {
        // 리프레시 토큰 삭제
        cookieUtil.setRefreshToken("", 0L);
    }

    private FrontLoginResponse generateToken(FrontLoginSelectResponse req, boolean isGenerateRefresh) {
        JwtAuthenticationVo jwtAuthenticationVo = JwtAuthenticationVo.builder()
                .seq(req.getMemberSeq())
                .id(req.getId())
                .jwtType(JwtType.access)
                .platformType(PlatformType.front)
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

        return FrontLoginResponse.builder()
                .accessToken(accessToken)
                .accessTokenExpiredDt(accessTokenExpiredDt)
                .refreshToken(refreshToken)
                .refreshTokenExpiredDt(refreshTokenExpiredDt)
                .build();
    }

    @Transactional
    public FrontLoginResponse responseLogin(FrontLoginRequest req, FrontLoginSelectResponse member, boolean isGenerateRefresh) {
        // 토큰 생성
        FrontLoginResponse token = generateToken(member, isGenerateRefresh);

        // 비밀번호 만료여부 체크
        LocalDateTime now = LocalDateTime.now();
        boolean isPasswordExpired = ChronoUnit.DAYS.between(member.getPasswordChangeDt(), now) > 90;

        // 로그인 이력 저장
//        FrontLoginHistoryInsertRequest loginHistoryInsertRequest = new FrontLoginHistoryInsertRequest();
//        if (ObjectUtils.isEmpty(req.getDeviceInfo())) {
//            loginHistoryInsertRequest = FrontLoginHistoryInsertRequest.builder()
//                    .memberSeq(member.getMemberSeq())
//                    .build();
//        } else {
//            loginHistoryInsertRequest = FrontLoginHistoryInsertRequest.builder()
//                    .memberSeq(member.getMemberSeq())
//                    .appVersion(req.getDeviceInfo().getAppVersion())
//                    .osVersion(req.getDeviceInfo().getOsVersion())
//                    .build();
//        }
//
//        MapperUtil.setBaseRequest(loginHistoryInsertRequest);
//        loginHistoryInsertRequest.setRegisterSeq(member.getMemberSeq());
//        mapper.insertLoginHistory(loginHistoryInsertRequest);

        // 엑세스토큰 갱신 API가 아닌 경우에만
        if (StringUtils.hasText(token.getRefreshToken())) {
            // 리프레시 토큰 쿠키로 변경
            cookieUtil.setRefreshToken(token.getRefreshToken(), JWT_REFRESH_EXPIRATION);
        }

        return FrontLoginResponse.builder()
                .passwordExpiredYn(isPasswordExpired)
                .accessToken(token.getAccessToken())
                .accessTokenExpiredDt(token.getAccessTokenExpiredDt())
                .refreshToken(token.getRefreshToken())
                .accessTokenExpiredDt(token.getRefreshTokenExpiredDt())
                .build();
    }

//    public FrontMemberFindIdResponse findId(FrontMemberFindIdRequest req) {
//        req.setDi(passUtil.getDi(req.getPassInfo()));
//        FrontMemberFindIdResponse response = mapper.findId(req);
//        if (null == response) {
//            throw new CustomException(LoginERRCd.ERR_LOGIN_001.name(), LoginERRCd.ERR_LOGIN_001.getDesc());
//        } else {
//            response.setId(aes256Util.decrypt(response.getId()));
//        }
//        return response;
//    }

//    @Transactional
//    public void updatePasswordByDi(FrontMemberPasswordUpdateByDiRequest req) {
//        // BaseRequest 셋팅
//        MapperUtil.setBaseRequest(req);
//        req.setNewPassword(sha256Util.encrypt(req.getNewPassword()));
//        req.setDi(passUtil.getDi(req.getPassInfo()));
//
//        // 회원정보 조회
//        FrontLoginRequest selectMemberRequest = RestUtil.convert(req, FrontLoginRequest.class);
//        selectMemberRequest.setMemberTypeCd(MemberTypeCd.email);
//        FrontLoginSelectResponse selectMemberResponse = mapper.selectLoginByDi(selectMemberRequest);
//
//        if (!ObjectUtils.isEmpty(selectMemberResponse)) {
//            if (selectMemberResponse.getPassword().equals(req.getNewPassword())) {
//                // 기존 비밀번호와 일치하는 경우
//                throw new CustomException(LoginERRCd.ERR_LOGIN_003.toString(), LoginERRCd.ERR_LOGIN_003.getDesc());
//            }
//
//            if (MemberStatusCd.NORMAL.equals(selectMemberResponse.getMemberStatusCd())) {
//                // 정상 회원 DI 비밀번호 변경
//                mapper.updatePasswordByDi(req);
//            } else {
//                // 휴면 회원 DI 비밀번호 변경
//                mapper.updateDormancyPasswordByDi(req);
//            }
//        } else {
//            // 일치하는 회원 정보가 없는 경우
//            throw new CustomException(LoginERRCd.ERR_LOGIN_001.toString(), LoginERRCd.ERR_LOGIN_001.getDesc());
//        }
//    }

//    @Transactional
//    public void updateRestoreMember(FrontRestoreDormancyRequest req) {
//        // 회원 조회
//        PassInfo passInfo = passUtil.decPassInfo(req.getPassInfo());
//
//        FrontLoginRequest frontLoginRequest = new FrontLoginRequest();
//        frontLoginRequest.setMemberSeq(loginUtil.getLoginUserSeq());
//        FrontLoginSelectResponse front = mapper.selectDormancyMember(frontLoginRequest);
//
//        if (passInfo.getDi().equals(front.getDi())) {
//            // 휴면 해지
//            mapper.updateRestoreMember(frontLoginRequest);
//            // 휴면 테이블 회원 정보 삭제
//            mapper.deleteDormancyMember(frontLoginRequest);
//        } else {
//            // 아이디가 없거나 비밀번호 오류
//            throw new CustomException(LoginERRCd.ERR_LOGIN_001.toString(), LoginERRCd.ERR_LOGIN_001.getDesc());
//        }
//    }

//    @Transactional
//    public void updateDormancyMember(FrontUpdateDormancyMemberRequest req) {
//        FrontLoginRequest frontLoginRequest = RestUtil.convert(req, FrontLoginRequest.class);
//        frontLoginRequest.setId(aes256Util.encrypt(req.getId()));
//        FrontLoginSelectResponse front = mapper.selectLogin(frontLoginRequest);
//
//        if (isMember(frontLoginRequest, member)) {
//            CommonMemberSelectRequest dormancyReq = new CommonMemberSelectRequest();
//            MapperUtil.setBaseRequest(dormancyReq);
//            dormancyReq.setMemberSeq(dormancyReq.getUpdaterSeq());
//            dormancyReq.setMemberStatusChangeDt(LocalDateTime.now());
//            mapper.insertDormancyMember(dormancyReq);
//
//            // 비밀번호 삭제
//            req.setPassword(sha256Util.encrypt("-"));
//            mapper.updateDormancyMember(dormancyReq);
//        } else {
//            // 아이디가 없거나 비밀번호 오류
//            throw new CustomException(LoginERRCd.ERR_LOGIN_001.toString(), LoginERRCd.ERR_LOGIN_001.getDesc());
//        }
//    }

//    public void deleteSecessionMember(String mobilePhoneNo) {
//        String encMobilePhoneNo = aes256Util.encrypt(mobilePhoneNo);
//        mapper.deleteSecessionMember(encMobilePhoneNo);
//    }

//    public void deleteTokenSecessionMember(Long memberSeq) {
//        FrontLoginSelectResponse frontLoginSelectResponse = mapper.deleteTokenSecessionMember(memberSeq);
//
//        if (null != frontLoginSelectResponse) {
//            throw new JwtException();
//        }
//    }

}
