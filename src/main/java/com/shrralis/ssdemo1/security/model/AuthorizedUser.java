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

package com.shrralis.ssdemo1.security.model;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Objects;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 12/21/17 at 3:26 PM
 */
public class AuthorizedUser extends User {

	private Integer id;
	private String email;
	private com.shrralis.ssdemo1.entity.User.Type type;
	private transient int failedAuthCount;

	public AuthorizedUser(
			com.shrralis.ssdemo1.entity.User user,
			Collection<? extends GrantedAuthority> authorities) {
		super(user.getLogin(), user.getPassword(), authorities);

		id = user.getId();
		email = user.getEmail();
		type = user.getType();
	}

	public static AuthorizedUser getCurrent() {
		if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken
				|| SecurityContextHolder.getContext().getAuthentication() == null) {
			return null;
		}
		return (AuthorizedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public com.shrralis.ssdemo1.entity.User.Type getType() {
		return type;
	}

	public AuthorizedUser setType(com.shrralis.ssdemo1.entity.User.Type type) {
		this.type = type;

		return this;
	}

	public int getFailedAuthCount() {
		return failedAuthCount;
	}

	public void setFailedAuthCount(int failedAuthCount) {
		this.failedAuthCount = failedAuthCount;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		if (!super.equals(o)) {
			return false;
		}

		AuthorizedUser that = (AuthorizedUser) o;

		return Objects.equals(id, that.id) &&
				Objects.equals(email, that.email) &&
				type == that.type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), id, email, type);
	}

	@Override
	public String toString() {
		return "AuthorizedUser{" +
				"id=" + id +
				", email='" + email + '\'' +
				", type=" + type +
				", failedAuthCount=" + failedAuthCount +
				'}';
	}
}
