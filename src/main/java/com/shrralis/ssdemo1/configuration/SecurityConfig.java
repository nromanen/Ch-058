/*
 * The following code have been created by Yaroslav Zhyravov (shrralis).
 * The code can be used in non-commercial way for everyone.
 * But for any commercial way it needs a author's agreement.
 * Please contact the author for that:
 *  - https://t.me/Shrralis
 *  - https://twitter.com/Shrralis
 *  - shrralis@gmail.com
 *
 * Copyright (c) 2017 by shrralis (Yaroslav Zhyravov).
 */

package com.shrralis.ssdemo1.configuration;

import com.shrralis.ssdemo1.security.AuthFailureHandler;
import com.shrralis.ssdemo1.security.AuthSuccessHandler;
import com.shrralis.ssdemo1.security.HttpLogoutSuccessHandler;
import com.shrralis.ssdemo1.security.RestUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private RestUserDetailsService userDetailsService;
	@Autowired
	private AuthenticationEntryPoint authEntryPoint;
	@Autowired
	private AuthSuccessHandler authSuccessHandler;
	@Autowired
	private AuthFailureHandler authFailureHandler;
	@Autowired
	private HttpLogoutSuccessHandler logoutSuccessHandler;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Bean
	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return super.userDetailsServiceBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authenticationProvider(authenticationProvider())
				.exceptionHandling()
				.authenticationEntryPoint(authEntryPoint)
				.and()
				.formLogin()
				.permitAll()
				.loginProcessingUrl("/auth/login")
				.usernameParameter("login")
				.passwordParameter("password")
				.successHandler(authSuccessHandler)
				.failureHandler(authFailureHandler)
				.and()
				.logout()
				.permitAll()
				.logoutRequestMatcher(new AntPathRequestMatcher("/auth/login", "DELETE"))
				.logoutSuccessHandler(logoutSuccessHandler)
				.and()
				.sessionManagement()
				.maximumSessions(1);
		http.authorizeRequests().anyRequest().authenticated();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
		return authenticationProvider;
	}
}
