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
import com.shrralis.ssdemo1.repository.UsersRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 12/21/17 at 3:23 PM
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = usersRepository.findByLogin(username);

            return new AuthorizedUser(user.getLogin(), user.getPassword(), getAuthorities(user));
        } catch (NoResultException ex) {
            throw new UsernameNotFoundException("User not found");
        }
    }

    // TODO: delete this
    /*private AuthorizedUser getAuthorizedUserInstance(User user) {
        AuthorizedUser authorizedUser = new AuthorizedUser(user.getLogin(), user.getPassword(), getAuthorities(user));

        authorizedUser.setId(user.getId());
        authorizedUser.setEmail(user.getEmail());
        authorizedUser.setType(user.getType());
        return authorizedUser;
    }*/

    private Set<GrantedAuthority> getAuthorities(User user) {
        Set<GrantedAuthority> authorities = new HashSet<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getType().toString()));
        return authorities;
    }
}
