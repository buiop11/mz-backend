package com.matjzing.dto.topic.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * TOPIC_MEMBER.ROLE_TYPE — 등록자는 OWNER, 그 외 참여는 PARTICIPANT.
 */
@Getter
@RequiredArgsConstructor
public enum TopicMemberRoleType {

	OWNER("OWNER"),
	PARTICIPANT("PARTICIPANT"),
	;

	private final String code;

	public static TopicMemberRoleType fromCode(String code) {
		if (code == null) {
			return null;
		}
		for (TopicMemberRoleType v : values()) {
			if (v.code.equalsIgnoreCase(code)) {
				return v;
			}
		}
		return null;
	}
}
