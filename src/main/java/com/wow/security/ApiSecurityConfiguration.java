package com.wow.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wow.security.auth.provider.JwtAuthenticationProvider;
import com.wow.security.auth.provider.LoginAuthenticationProvider;
import com.wow.security.filter.CustomCorsFilter;
import com.wow.security.filter.JwtTokenAuthenticationProcessingFilter;
import com.wow.security.filter.LoginProcessingFilter;
import com.wow.security.jwt.SkipPathRequestMatcher;
import com.wow.security.jwt.extracter.TokenExtractor;

/**
 * 
 * @author Brahmadutt S
 *
 * Mar 6, 2020
 */

@Configuration
@EnableWebSecurity
public class ApiSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	public static final String AUTHENTICATION_HEADER_NAME = "Authorization";
	private static final String AUTHENTICATION_URL = "/secure/login";
	private static final String REFRESH_TOKEN_URL = "/secure/refreshToken";
	private static final String PUBLIC_API = "/public/**";
	private static final String API_ROOT_URL = "/secure/**";

	@Autowired
	private RestAuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private AuthenticationSuccessHandler successHandler;
	@Autowired
	private JwtAuthenticationFailureHandler failureHandler;
	@Autowired
	private TokenExtractor tokenExtractor;
	@Autowired
	private LoginAuthenticationProvider ajaxAuthenticationProvider;
	@Autowired
	private JwtAuthenticationProvider jwtAuthenticationProvider;

	@Autowired
	private ObjectMapper objectMapper;

	protected LoginProcessingFilter buildAjaxLoginProcessingFilter(String loginEntryPoint) throws Exception {
		LoginProcessingFilter filter = new LoginProcessingFilter(loginEntryPoint, successHandler, failureHandler,
				objectMapper);
		filter.setAuthenticationManager(this.authenticationManager);
		return filter;
	}

	protected JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter(
			List<String> pathsToSkip, String pattern) throws Exception {
		SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, pattern);
		JwtTokenAuthenticationProcessingFilter filter = new JwtTokenAuthenticationProcessingFilter(failureHandler,
				tokenExtractor, matcher);
		filter.setAuthenticationManager(this.authenticationManager);
		return filter;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(ajaxAuthenticationProvider);
		auth.authenticationProvider(jwtAuthenticationProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		List<String> permitAllEndpointList = Arrays.asList(AUTHENTICATION_URL, REFRESH_TOKEN_URL, PUBLIC_API);

		http.csrf().disable() // We don't need CSRF for JWT based authentication
				.exceptionHandling().authenticationEntryPoint(this.authenticationEntryPoint)

				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

				.and().authorizeRequests()
				.antMatchers(permitAllEndpointList.toArray(new String[permitAllEndpointList.size()])).permitAll().and()
				.authorizeRequests().antMatchers(API_ROOT_URL).authenticated() // Protected API End-points
				.and().addFilterBefore(new CustomCorsFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(buildAjaxLoginProcessingFilter(AUTHENTICATION_URL),
						UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(permitAllEndpointList, API_ROOT_URL),
						UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}

	@Bean
	protected AuthenticationEntryPoint authenticationEntryPoint() {
		return new RestAuthenticationEntryPoint();
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
