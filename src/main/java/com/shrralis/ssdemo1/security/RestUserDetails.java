package com.shrralis.ssdemo1.security;

import com.shrralis.ssdemo1.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0 Created 12/20/17 at 3:14 PM
 */
public class RestUserDetails implements UserDetails {
	private User user;
	private List<GrantedAuthority> authorities;

	public RestUserDetails(User user, List<GrantedAuthority> authorities) {
		this.user = user;
		this.authorities = authorities;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getLogin();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
