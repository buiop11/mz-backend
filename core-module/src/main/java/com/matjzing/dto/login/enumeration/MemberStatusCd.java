package com.matjzing.dto.login.enumeration;

public enum MemberStatusCd {

	NORMAL("NORMAL"),
	DORMANCY("DORMANCY"),
	SECESSION("SECESSION"),
	;
	private final String desc;

	public String getDesc() {
		return desc;
	}

	MemberStatusCd(String desc) {
		this.desc = desc;
	}

}
