package com.matjzing.dto.common.enumeration;

public enum JwtType {
	access("access"),
	refresh("refresh"),
	;
	private final String desc;

	public String getDesc() {
		return desc;
	}

	JwtType(String desc) {
		this.desc = desc;
	}
}
