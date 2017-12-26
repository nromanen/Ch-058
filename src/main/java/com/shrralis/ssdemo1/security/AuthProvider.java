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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 12/21/17 at 3:11 PM
 */
public class AuthProvider implements AuthenticationProvider, InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(AuthProvider.class);
    private PasswordEncoder passwordEncoder;
    private UserDetailsService userDetailsService;
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    public AuthProvider() {
        setPasswordEncoder(new BCryptPasswordEncoder());
    }

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

    private UserDetails retrieveUserDetails(String login) throws AuthenticationException {
        UserDetails userDetails;

        try {
            userDetails = userDetailsService.loadUserByUsername(login);
        } catch (UsernameNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage(), e);
        }

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
            UsernamePasswordAuthenticationToken authentication
    ) throws AuthenticationException {
        if (authentication.getCredentials() != null) {
            String password = authentication.getCredentials().toString();

            if (password.length() < 8 || !passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("Bad credentials");
            }
        } else {
            throw new BadCredentialsException("Bad credentials");
        }
    }

    private Authentication createSuccessAuthentication(Authentication authentication, UserDetails user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                user,
                authentication.getCredentials(),
                authoritiesMapper.mapAuthorities(user.getAuthorities())
        );

        authenticationToken.setDetails(authentication.getDetails());
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    protected PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        if (passwordEncoder == null) {
            throw new IllegalArgumentException("passwordEncoder cannot be null");
        }

        this.passwordEncoder = passwordEncoder;
    }

    protected UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
