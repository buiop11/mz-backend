package com.matjzing.dto.common;

import lombok.*;
import com.matjzing.dto.common.enumeration.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthenticationVo {

	private Long seq;
	private String id;
	private PlatformType platformType; // front, admin
	private JwtType jwtType; // access token, refresh token
	private ProfileType profile; // dev, prod
	private Date expiration;
	private boolean isSwagger;

}
