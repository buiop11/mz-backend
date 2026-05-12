package com.matjzing.dto.member;

import com.matjzing.dto.common.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

/**
 * Google 최초 로그인 시 MEMBER 등록용 (PASS/DI 본인인증 없음).
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Alias("frontMemberGoogleInsertRequest")
public class FrontMemberGoogleInsertRequest extends BaseRequest {

	private Long memberSeq;

	private String googleSub;
	private String memberTypeCd;
	private String id;
	private String ci;
	private String di;
	private String password;
	private String email;
	private String name;
	private String nickname;
	private String genderCd;
	private String telecomSectionCd;
	private String nativeSectionCd;
	private String mobilePhoneNo;
	private String birthday;
}
