package com.matjzing.dto.member;

import lombok.*;
import com.matjzing.dto.login.enumeration.MemberStatusCd;

@EqualsAndHashCode(callSuper = false)
@Data
public class FrontMemberSelectResponse {

	private String id;
	private String password;
	private String di;
	private MemberStatusCd memberStatusCd;

}

