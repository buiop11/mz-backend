package com.matjzing.mapper;

import com.matjzing.dto.login.*;
import com.matjzing.dto.member.*;
import com.matjzing.dto.security.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FrontLoginMapper {

    User findByUserId(String username);

    /** Google OIDC sub로 MEMBER 또는 휴면 회원 조회 */
    FrontLoginSelectResponse selectMemberByGoogleSub(@Param("googleSub") String googleSub);

    FrontLoginSelectResponse selectLogin(FrontLoginRequest req);
    FrontLoginSelectResponse selectLoginByDi(FrontLoginRequest req);
    FrontLoginSelectResponse selectMember(FrontLoginRequest req);
    int updateRestoreMember(FrontLoginRequest req);
    int deleteDormancyMember(FrontLoginRequest req);
//    FrontMemberFindIdResponse findId(FrontMemberFindIdRequest req);
//    int updatePasswordByDi(FrontMemberPasswordUpdateByDiRequest req);
//    int updateDormancyPasswordByDi(FrontMemberPasswordUpdateByDiRequest req);
    int insertDormancyMember(CommonMemberSelectRequest req);
    int updateDormancyMember(CommonMemberSelectRequest req);

}
