package com.shrralis.ssdemo1.security.exception;

import com.shrralis.ssdemo1.security.exception.interfaces.ICitizenAuthenticationException;
import com.shrralis.tools.model.JsonError;
import org.springframework.security.core.AuthenticationException;

import static com.shrralis.tools.model.JsonError.Error.NOT_SUBMITTED_REGISTRATION;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 1/22/18 at 2:45 PM
 */
public class NotSubmittedUserRegistrationException extends AuthenticationException implements ICitizenAuthenticationException {

	public NotSubmittedUserRegistrationException(String message) {
		super(message);
	}

	public NotSubmittedUserRegistrationException(String message, Throwable cause) {
		super(message, cause);
	}

	@Override
	public JsonError.Error getError() {
		return NOT_SUBMITTED_REGISTRATION;
	}
}
