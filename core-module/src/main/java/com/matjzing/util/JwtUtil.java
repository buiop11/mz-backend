package com.matjzing.util;

import com.matjzing.dto.common.JwtAuthenticationVo;
import com.matjzing.dto.common.enumeration.JwtType;
import com.matjzing.dto.common.enumeration.ProfileType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String SECRET_KEY;

	@Value("${jwt.token-validity-in-seconds.access}")
	private long JWT_ACCESS_EXPIRATION;

	@Value("${jwt.token-validity-in-seconds.refresh}")
	private long JWT_REFRESH_EXPIRATION;

	@Value("${spring.profiles.active}")
	private String profile;

	private final Map<String, Object> claims = new HashMap<>();
	private final Aes256Util aes256Util;
	private final CookieUtil cookieUtil;

	public String generateToken(JwtAuthenticationVo jwtAuthenticationVo) {
		jwtAuthenticationVo.setProfile(ProfileType.valueOf(profile));

		if (jwtAuthenticationVo.isSwagger()) {
			jwtAuthenticationVo.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24));
		} else {
			if (JwtType.access.getDesc().equals(jwtAuthenticationVo.getJwtType().getDesc())) {
				jwtAuthenticationVo.setExpiration(new Date(System.currentTimeMillis() + JWT_ACCESS_EXPIRATION * 1000 * 60));
			} else if (JwtType.refresh.getDesc().equals(jwtAuthenticationVo.getJwtType().getDesc())) {
				jwtAuthenticationVo.setExpiration(new Date(System.currentTimeMillis() + JWT_REFRESH_EXPIRATION * 1000 * 60));
			}
		}

		claims.put("info", aes256Util.encrypt(RestUtil.convertDtoToJsonString(jwtAuthenticationVo)));
		return Jwts
				.builder()
				.setClaims(claims)
				.setSubject(aes256Util.encrypt(jwtAuthenticationVo.getPlatformType().getDesc()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(jwtAuthenticationVo.getExpiration())
				.signWith(getSignInKey(), SignatureAlgorithm.HS512)
				.compact();
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public JwtAuthenticationVo getTokenVo(String token) {
		try {
			return RestUtil.convertJsonToDto(aes256Util.decrypt((String) extractAllClaims(token).get("info")), JwtAuthenticationVo.class);
		} catch (ExpiredJwtException ex) {
			// 토큰 만료
			throw ex;
		} catch (JwtException | IllegalArgumentException  ex) {
			// 토큰 위변조
			throw new com.matjzing.exception.JwtException();
		} catch (Exception ex) {
			// 토큰 에러
			throw ex;
		}
	}

	public Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	public String dateToDateFormatString(Date dt) {
		String result = "";
		if (!ObjectUtils.isEmpty(dt)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			result =  sdf.format(dt);
		}
		return result;
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public boolean isCheckToken(String requestUri, String[] whiteList) throws UnsupportedEncodingException {
		String target = URLDecoder.decode(requestUri, StandardCharsets.UTF_8);
		return requestUri.startsWith("/api") && replacePattern(whiteList).stream()
				.anyMatch(pattern -> Pattern.matches(pattern, target));
	}


	private List<String> replacePattern(String[] urlArr) {
		return Arrays.stream(urlArr).map(url -> {
			if ((url.contains("/{") && url.contains("}")) || url.contains("/**")) {
				String[] split = url.split("/");
				List<String> urlList = new ArrayList<>();
				for (int i = 0; i < split.length; i++) {
					if (split[i].contains("{") || split[i].contains("}") || split[i].contains("**")) {
						urlList.add("[0-9a-zA-Z=]+");
					} else {
						urlList.add(split[i]);
					}
				}
				return String.join("/", urlList);
			} else {
				return url;
			}
		}).collect(Collectors.toList());
	}

	public boolean isCheckSwaggerToken(String requestUri, HttpServletResponse response) throws IOException {
		String ip = "127.0.0.1|0:0:0:0:0:0:0:1|125.131.114.51"; // 로컬, 회사 IP
		if ("/swagger-ui.html".equals(requestUri) && !(ip.contains(RestUtil.getClientIp()))){
			String swaggerToken = cookieUtil.getCookie("swagger");
			if (StringUtils.hasText(swaggerToken)) {
				try {
					getTokenVo(swaggerToken);
				} catch (Exception ex) {
					return redirectLogin(response);
				}
			} else {
				return redirectLogin(response);
			}
		}
		return true;
	}

	private boolean redirectLogin(HttpServletResponse response) throws IOException {
		cookieUtil.setNormalCookie("swagger", "", 0L);
		response.sendRedirect("/view/login");
		return false;
	}

}
