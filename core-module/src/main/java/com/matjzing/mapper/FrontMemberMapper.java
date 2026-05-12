package com.matjzing.mapper;

import com.matjzing.dto.member.*;
import org.springframework.stereotype.Repository;

@Repository
public interface FrontMemberMapper {

    FrontMemberSelectResponse selectDuplicateMember(FrontMemberInsertRequest req);

    int insertMember(FrontMemberInsertRequest req);

    int checkId(FrontMemberCheckIdRequest req);

    int insertSecessionMember(CommonMemberSelectRequest req);

    void deleteMember(CommonMemberSelectRequest req);

    FrontMemberSelectResponse selectMember(CommonMemberSelectRequest req);

    int updatePassword(FrontMemberPasswordUpdateRequest req);

//    int updatePassInfo(FrontMemberPassInfoUpdateRequest req);
//
//    FrontMemberSettingResponse selectSettingMemberDetail(CommonMemberSelectRequest req);

    /** Google 로그인 최초 가입(암묵적 가입) */
    int insertMemberGoogle(FrontMemberGoogleInsertRequest req);

}
