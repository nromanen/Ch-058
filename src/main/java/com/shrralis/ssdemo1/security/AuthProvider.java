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

package com.shrralis.ssdemo1.security;

import com.shrralis.ssdemo1.entity.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 12/21/17 at 3:11 PM
 */
public class AuthProvider implements AuthenticationProvider, InitializingBean {

    private PasswordEncoder passwordEncoder;
    private UserDetailsService userDetailsService;
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

	@Override
    public void afterPropertiesSet() {
        if (userDetailsService == null) {
            throw new IllegalArgumentException("A UserDetailsService must be set");
        }
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        String login = (authentication.getPrincipal() == null) ? null : authentication.getName();
        UserDetails userDetails = retrieveUserDetails(login);

        additionalAuthenticationChecks(userDetails, (UsernamePasswordAuthenticationToken) authentication);
        return createSuccessAuthentication(authentication, userDetails);
    }

    private UserDetails retrieveUserDetails(String login) {
	    UserDetails userDetails = userDetailsService.loadUserByUsername(login);

        if (userDetails == null) {
            throw new InternalAuthenticationServiceException(
                    "UserDetailsService returned null, which is an interface contract violation");
        }
        return userDetails;
    }

    /**
     * Main method that respond login page password validation
     *
     * @param userDetails
     * @param authentication
     * @throws BadCredentialsException if invalid password was entered
     */
    private void additionalAuthenticationChecks(
		    UserDetails userDetails,
		    UsernamePasswordAuthenticationToken authentication) {
        if (authentication.getCredentials() != null) {
            String password = authentication.getCredentials().toString();

	        if (password.length() < User.MIN_PASSWORD_LENGTH
			        || !passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("Bad credentials");
            }
        } else {
            throw new BadCredentialsException("Bad credentials");
        }
    }

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

	private Authentication createSuccessAuthentication(Authentication authentication, UserDetails user) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				user,
				authentication.getCredentials(),
				authoritiesMapper.mapAuthorities(user.getAuthorities()));

		authenticationToken.setDetails(authentication.getDetails());
		return authenticationToken;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
}
