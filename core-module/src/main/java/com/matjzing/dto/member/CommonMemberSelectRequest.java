package com.matjzing.dto.member;

import lombok.*;
import org.apache.ibatis.type.Alias;
import com.matjzing.dto.common.BaseRequest;

@EqualsAndHashCode(callSuper = false)
@Data
@Alias("commonMemberSelectRequest")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommonMemberSelectRequest extends BaseRequest {

	private Long memberSeq;
	private String password;

}
