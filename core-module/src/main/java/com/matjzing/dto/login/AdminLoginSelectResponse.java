package com.matjzing.dto.login;

import com.matjzing.dto.common.*;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Alias("adminLoginSelectResponse")
public class AdminLoginSelectResponse extends BaseRequest {

	private Long managerSeq;
	private String managerId;
	private String password;
	private String name;
	private String email;
	private LocalDateTime passwordChangeDt;
	private boolean passwordInitYn;

}
