package com.shrralis.ssdemo1.service;

import com.shrralis.ssdemo1.entity.User;
import com.shrralis.ssdemo1.exception.AbstractCitizenException;
import com.shrralis.ssdemo1.security.exception.CitizenBadCredentialsException;
import com.shrralis.ssdemo1.security.model.AuthorizedUser;
import com.shrralis.ssdemo1.security.service.UserDetailsServiceImpl;
import com.shrralis.ssdemo1.service.interfaces.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Collections;

@Service
public class SpringSecuritySignInAdapter implements SignInAdapter {

	@Autowired
	private IUserService userDetailsService;

	@Override
	public String signIn(String localUserId, Connection<?> connection, NativeWebRequest request) throws CitizenBadCredentialsException {
		if(AuthorizedUser.getCurrent()!=null && !StringUtils.contains(connection.fetchUserProfile().getEmail(), AuthorizedUser.getCurrent().getEmail())){
			throw new CitizenBadCredentialsException("Email doesn't match with yours");
		}

		User user = this.userDetailsService.getUser(Integer.parseInt(localUserId));
		AuthorizedUser authorizedUser = new AuthorizedUser(user, UserDetailsServiceImpl.getAuthorities(user));

		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken(authorizedUser, null,
						Collections.singleton(new SimpleGrantedAuthority(user.getType().name()))));
		return null;
	}
}
