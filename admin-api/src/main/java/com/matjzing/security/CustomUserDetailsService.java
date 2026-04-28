package com.matjzing.security;

import com.matjzing.dto.security.Role;
import com.matjzing.dto.security.User;
import com.matjzing.exception.UserNotFoundException;
import com.matjzing.mapper.AdminLoginMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final AdminLoginMapper adminLoginMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// mapper 조회
		User user = adminLoginMapper.findByUserId(username);

		if (user != null) {
			user.setRole(Role.USER);
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			String role = user.getRole().name();
			authorities.add(new SimpleGrantedAuthority(role));
			return new PrincipalDetails(user);
		} else {
			throw new UserNotFoundException();
		}

	}

}
