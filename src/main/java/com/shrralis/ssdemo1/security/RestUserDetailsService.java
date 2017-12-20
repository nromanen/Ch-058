package com.shrralis.ssdemo1.security;

import com.shrralis.ssdemo1.entity.User;
import com.shrralis.ssdemo1.repository.UsersRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0 Created 12/20/17 at 1:31 AM
 */
@Service
public class RestUserDetailsService implements UserDetailsService {
	@Resource
	private UsersRepository usersRepository;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		User user = usersRepository.findByLogin(login);

		if (user == null) {
			throw new UsernameNotFoundException(String.format("The login %s doesn't exist!", login));
		}
		return new RestUserDetails(user, getGrantedAuthorities(user));
	}

	private List<GrantedAuthority> getGrantedAuthorities(User user) {
		List<GrantedAuthority> authorities = new ArrayList<>();

		authorities.add(new SimpleGrantedAuthority(user.getType().name()));
		return authorities;
	}
}
