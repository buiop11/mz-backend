package com.matjzing.dto.login;

import com.matjzing.dto.common.*;
import com.matjzing.dto.login.enumeration.*;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Alias("frontLoginSelectResponse")
public class FrontLoginSelectResponse extends BaseRequest {

	private Long memberSeq;
	private String id;
	private String password;
	private String name;

	private String nickname;
	private String mobilePhoneNo;
	private LocalDateTime passwordChangeDt;
	private MemberStatusCd memberStatusCd;
	private int loginFailureCount;
	private boolean autoLoginYn;

	private String di;

}
