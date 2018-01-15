package com.shrralis.ssdemo1.service;

import com.shrralis.ssdemo1.entity.User;
import com.shrralis.ssdemo1.security.model.AuthorizedUser;
import com.shrralis.ssdemo1.security.service.UserDetailsServiceImpl;
import com.shrralis.ssdemo1.service.interfaces.IUserService;
import com.shrralis.tools.model.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Arrays;
import java.util.Collections;

@Service
public class SpringSecuritySignInAdapter implements SignInAdapter {

    @Autowired
    private IUserService userDetailsService;

    @Override
    public String signIn(String localUserId, Connection<?> connection, NativeWebRequest request) {
        User user = this.userDetailsService.getUser(Integer.parseInt(localUserId));
        AuthorizedUser authorizedUser = new AuthorizedUser(user, UserDetailsServiceImpl.getAuthorities(user));
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        authorizedUser, null, Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getType().toString()))));
        return null;
    }
}
