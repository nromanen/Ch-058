package com.shrralis.ssdemo1.exception;

import com.shrralis.tools.model.JsonError;

import static com.shrralis.tools.model.JsonError.Error.RECOVERY_TOKEN_EXPIRED;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 1/3/18 at 2:43 PM
 */
public class ExpiredRecoveryTokenException extends AbstractCitizenException {

	public ExpiredRecoveryTokenException() {
		super("The recovery token is expired");
	}

	@Override
	public JsonError.Error getError() {
		return RECOVERY_TOKEN_EXPIRED;
	}
}
