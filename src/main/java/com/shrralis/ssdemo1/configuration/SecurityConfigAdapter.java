package com.shrralis.ssdemo1.configuration;

import com.shrralis.ssdemo1.security.handler.CitizenAccessDeniedHandler;
import com.shrralis.ssdemo1.security.handler.CitizenAuthenticationFailureHandler;
import com.shrralis.ssdemo1.security.handler.LogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 1/2/18 at 2:10 PM
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfigAdapter extends WebSecurityConfigurerAdapter {

	private AuthenticationSuccessHandler authSuccessHandler;
	private CitizenAuthenticationFailureHandler authFailureHandler;
	private CitizenAccessDeniedHandler accessDeniedHandler;
	private LogoutSuccessHandler logoutSuccessHandler;

	@Autowired
	public SecurityConfigAdapter(AuthenticationSuccessHandler authSuccessHandler,
	                             CitizenAuthenticationFailureHandler authFailureHandler,
	                             CitizenAccessDeniedHandler accessDeniedHandler,
	                             LogoutSuccessHandler logoutSuccessHandler) {
		this.authSuccessHandler = authSuccessHandler;
		this.authFailureHandler = authFailureHandler;
		this.accessDeniedHandler = accessDeniedHandler;
		this.logoutSuccessHandler = logoutSuccessHandler;
	}

	@Autowired
	protected void configureGlobalSecurity(AuthenticationManagerBuilder auth, AuthenticationProvider authProvider) {
		auth.authenticationProvider(authProvider);
		auth.eraseCredentials(true);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.cors()
				.and()
				.authorizeRequests()
				.antMatchers("/auth/logout").authenticated()
				.antMatchers("/admin**/**").access("hasRole('ADMIN')")
				.antMatchers("/user**/**").access("hasRole('ADMIN') or hasRole('USER')")
				.and()
				.formLogin()
				.loginPage("/auth/login")
				.loginProcessingUrl("/auth/login")
				.usernameParameter("login")
				.passwordParameter("password")
				.successHandler(authSuccessHandler)
				.failureHandler(authFailureHandler)
				.and()
				.logout()
				.logoutUrl("/auth/logout")
				.invalidateHttpSession(true)
				.logoutSuccessHandler(logoutSuccessHandler)
				.deleteCookies("JSESSIONID", "XSRF-TOKEN", "locale-cookie")
				.and()
				.exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler)
				.and()
				.csrf()
				.disable();
	}
}
