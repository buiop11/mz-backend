package com.matjzing.dto.common.enumeration;

public enum PlatformType {

	front("front"),
	admin("admin"),
	;
	private final String desc;

	public String getDesc() {
		return desc;
	}

	PlatformType(String desc) {
		this.desc = desc;
	}
}
