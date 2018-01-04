package com.shrralis.ssdemo1.exception;

import com.shrralis.tools.model.JsonError;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 1/4/18 at 4:36 PM
 */
public class UserFailedAuthenticationCountOverflow extends AbstractCitizenException {

	@Override
	public JsonError.Error getError() {
		return null;
	}
}
