package com.shrralis.ssdemo1.exception;

import com.shrralis.ssdemo1.exception.interfaces.AbstractShrralisException;
import com.shrralis.tools.model.JsonError;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0 Created 12/24/17 at 2:15 AM
 */
public class BadParameterFormatException extends AbstractShrralisException {
	private String parameterName;

	public BadParameterFormatException(String parameterName) {
		super("The passed " + parameterName + " parameter has bad format");

		this.parameterName = parameterName;
	}

	public BadParameterFormatException(String parameterName, boolean anyForDefaultMsg) {
		super("The passed " + parameterName + " has bad format");
	}

	public String getParameterName() {
		return parameterName;
	}

	@Override
	public JsonError.Error getError() {
		return JsonError.Error.BAD_PARAMETER_FORMAT.setParam(parameterName);
	}
}
