package com.shrralis.ssdemo1.security.exception;

import org.springframework.security.authentication.BadCredentialsException;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 1/4/18 at 5:01 PM
 */
public class CitizenBadCredentialsException extends BadCredentialsException {

	private final int failedAttempts;

	/**
	 * Constructs a <code>BadCredentialsException</code> with the specified message.
	 *
	 * @param msg
	 * 		the detail message
	 */
	public CitizenBadCredentialsException(String msg, int failedAttempts) {
		super(msg);

		this.failedAttempts = failedAttempts;
	}

	/**
	 * Constructs a <code>BadCredentialsException</code> with the specified message and
	 * root cause.
	 *
	 * @param msg
	 * 		the detail message
	 * @param t
	 * 		root cause
	 */
	public CitizenBadCredentialsException(String msg, int failedAttempts, Throwable t) {
		super(msg, t);

		this.failedAttempts = failedAttempts;
	}

	public int getFailedAttempts() {
		return failedAttempts;
	}
}
