package com.wow.security.auth.provider;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.wow.security.jwt.token.UserContext;

/**
 * 
 * @author Brahmadutt S
 *
 * Mar 6, 2020
 */
@Component
public class LoginAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserDetailsService userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		PasswordEncoder encoder = passwordEncoder();

		String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();

		UserDetails user = userService.loadUserByUsername(username);

		if (!encoder.matches(password, user.getPassword())) {
			throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
		}

		List<GrantedAuthority> authorities = user.getAuthorities().stream().collect(Collectors.toList());

		UserContext userContext = UserContext.create(user.getUsername(), authorities);

		return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
