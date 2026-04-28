package com.matjzing.dto.common.enumeration;

public enum ProfileType {
	local("local"),
	dev("dev"),
	prod("prod"),
	;
	private final String desc;

	public String getDesc() {
		return desc;
	}

	ProfileType(String desc) {
		this.desc = desc;
	}
}
