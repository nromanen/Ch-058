package com.shrralis.ssdemo1.security.exception;

import com.shrralis.ssdemo1.exception.AbstractCitizenException;
import com.shrralis.ssdemo1.security.exception.interfaces.ICitizenAuthenticationException;
import com.shrralis.tools.model.JsonError;
import org.springframework.security.core.AuthenticationException;

import static com.shrralis.tools.model.JsonError.Error.USER_BLOCKED_BY_MAX_FAILED_AUTH;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 1/4/18 at 4:36 PM
 */
public class UserFailedAuthenticationCountOverflow extends AuthenticationException
		implements ICitizenAuthenticationException {

	public UserFailedAuthenticationCountOverflow(String msg) {
		super(msg);
	}

	public UserFailedAuthenticationCountOverflow(String msg, Throwable t) {
		super(msg, t);
	}

	@Override
	public JsonError.Error getError() {
		return USER_BLOCKED_BY_MAX_FAILED_AUTH;
	}
}
