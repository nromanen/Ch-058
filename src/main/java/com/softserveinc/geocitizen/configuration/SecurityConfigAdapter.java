package com.softserveinc.geocitizen.configuration;

import com.softserveinc.geocitizen.entity.User;
import com.softserveinc.geocitizen.security.handler.CitizenAccessDeniedHandler;
import com.softserveinc.geocitizen.security.handler.CitizenAuthenticationFailureHandler;
import com.softserveinc.geocitizen.security.handler.LogoutSuccessHandler;
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
 * @author softserveinc (https://t.me/Shrralis)
 * @version 1.0
 * Created 1/2/18 at 2:10 PM
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfigAdapter extends WebSecurityConfigurerAdapter {

	private final AuthenticationSuccessHandler authSuccessHandler;
	private final CitizenAuthenticationFailureHandler authFailureHandler;
	private final CitizenAccessDeniedHandler accessDeniedHandler;
	private final LogoutSuccessHandler logoutSuccessHandler;

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
				.antMatchers("/api/auth/getCurrentSession", "/api/users/currentLang", "/api/issues**", "/api/issues/*//*votes", "/api/images/*", "/api/map**", "/api/users/image/*").permitAll()
				.antMatchers("/api/auth/logout", "/api/auth/update").authenticated()
				.antMatchers("/api/issues/*/*", "/api/map/marker", "/api/map/issue", "/api/users/*")
				.hasAnyRole(User.Type.ROLE_USER.getRole(), User.Type.ROLE_ADMIN.getRole(), User.Type.ROLE_MASTER.getRole())
				.antMatchers("/api/users", "/api/admin/**")
				.hasAnyRole(User.Type.ROLE_ADMIN.getRole(), User.Type.ROLE_MASTER.getRole())
				.antMatchers("/api/auth/*").anonymous()
				.and()
				.formLogin()
				.loginPage("/api/auth/login")
				.loginProcessingUrl("/api/auth/login")
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
