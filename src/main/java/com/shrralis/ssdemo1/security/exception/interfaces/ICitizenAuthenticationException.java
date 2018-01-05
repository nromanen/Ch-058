package com.shrralis.ssdemo1.security.exception.interfaces;

import com.shrralis.tools.model.JsonError;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 1/5/18 at 12:57 PM
 */
public interface ICitizenAuthenticationException {

	JsonError.Error getError();
}
