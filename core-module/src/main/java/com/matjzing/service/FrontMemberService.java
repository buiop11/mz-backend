package com.matjzing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.*;
import com.matjzing.dto.login.enumeration.MemberStatusCd;
import com.matjzing.dto.member.*;
import com.matjzing.exception.CustomException;
import com.matjzing.mapper.FrontMemberMapper;
import com.matjzing.util.*;

@Service
@RequiredArgsConstructor
public class FrontMemberService {

    private enum MemberERRCd {
        ERR_MEMBER_001("기존 등록된 회원 존재합니다."),
        ERR_MEMBER_002("이미 사용중인 아이디 입니다."),
        ERR_MEMBER_003("기존 비밀번호를 확인하세요."),
        ERR_MEMBER_004("기존 비밀번호 변경 불가"),
        ERR_MEMBER_005("탈퇴 회원 탈퇴 후 30일간 서비스 재가입 불가."),
        ERR_MEMBER_006("일치하는 회원 정보가 없는 경우"),
        ;
        private final String desc;
        public String getDesc() {
            return desc;
        }
        MemberERRCd(String desc) {
            this.desc = desc;
        }
    }

    private final Aes256Util aes256Util;
    private final Sha256Util sha256Util;
    private final PassUtil passUtil;

    private final FrontMemberMapper mapper;

    public FrontMemberInsertResponse insert(FrontMemberInsertRequest req) {
        // BaseRequest 셋팅
        MapperUtil.setBaseRequest(req);

        // pass 복호화 및 req에 삽입
        passUtil.decPassInfo(req);

        // id, ci, di 중복체크
        FrontMemberSelectResponse selectMemberResponse = mapper.selectDuplicateMember(req);

        if (!ObjectUtils.isEmpty(selectMemberResponse)) {
            if (MemberStatusCd.SECESSION.getDesc().equals(selectMemberResponse.getMemberStatusCd().getDesc())) {
                // 탈퇴 회원의 경우 탈퇴 후 30일동안 가입불가
                throw new CustomException(MemberERRCd.ERR_MEMBER_005.toString(), MemberERRCd.ERR_MEMBER_005.getDesc());
            } else if (req.getDi().equals(selectMemberResponse.getDi())) {
                // 기 가입자 존재
                throw new CustomException(MemberERRCd.ERR_MEMBER_001.toString(), MemberERRCd.ERR_MEMBER_001.getDesc());
            } else if (req.getId().equals(selectMemberResponse.getId())) {
                // ID 중복
                throw new CustomException(MemberERRCd.ERR_MEMBER_002.toString(), MemberERRCd.ERR_MEMBER_002.getDesc());
            }
        }

        if (StringUtils.hasText(req.getMobilePhoneNo())) {
            req.setMobilePhoneNo(aes256Util.encrypt(req.getMobilePhoneNo()));
        }

        if (StringUtils.hasText(req.getEmail())) {
            req.setEmail(aes256Util.encrypt(req.getEmail()));
        }

        if (StringUtils.hasText(req.getPassword())) {
            req.setPassword(sha256Util.encrypt(req.getPassword()));
        }

        req.setRegisterSeq(0L);
        req.setUpdaterSeq(0L);
        mapper.insertMember(req); // 등록처리

        return FrontMemberInsertResponse.builder().name(req.getName()).build();
    }

    public FrontMemberCheckIdResponse checkId(FrontMemberCheckIdRequest req) {
        FrontMemberCheckIdResponse frontMemberCheckIdResponse = new FrontMemberCheckIdResponse();
        frontMemberCheckIdResponse.setUseYn(mapper.checkId(req) > 0);
        return frontMemberCheckIdResponse;
    }

    public void delete(CommonMemberSelectRequest req) {
        // BaseRequest 셋팅
        MapperUtil.setBaseRequest(req);
        // 탈퇴테이블 등록(select - insert)
        mapper.insertSecessionMember(req);
        // 비밀번호 삭제
        req.setPassword(sha256Util.encrypt("-"));
        // 회원탈퇴
        mapper.deleteMember(req);
    }

    public void updatePassword(FrontMemberPasswordUpdateRequest req) {
        // BaseRequest 셋팅
        MapperUtil.setBaseRequest(req);

        req.setNowPassword(sha256Util.encrypt(req.getNowPassword()));
        req.setNewPassword(sha256Util.encrypt(req.getNewPassword()));

        // 회원정보 조회
        FrontMemberSelectResponse selectMemberResponse = mapper.selectMember(RestUtil.convert(req, CommonMemberSelectRequest.class));

        if (!ObjectUtils.isEmpty(selectMemberResponse)) {
            if (req.getNowPassword().equals(selectMemberResponse.getPassword())) {
                // 현재 비밀번호가 다를 경우
                throw new CustomException(MemberERRCd.ERR_MEMBER_003.toString(), MemberERRCd.ERR_MEMBER_003.getDesc());
            } else if (req.getNewPassword().equals(selectMemberResponse.getPassword())) {
                // 신규 비밀번호와 이전 비밀번호가 같을 경우
                throw new CustomException(MemberERRCd.ERR_MEMBER_004.toString(), MemberERRCd.ERR_MEMBER_004.getDesc());
            }
        } else {
            // 일치하는 회원 정보가 없는 경우
            throw new CustomException(MemberERRCd.ERR_MEMBER_006.toString(), MemberERRCd.ERR_MEMBER_006.getDesc());
        }

        // 비밀번호 변경
        mapper.updatePassword(req);
    }

}
